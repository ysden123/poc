/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yuriy Stul
 */
public class Main1 {
    /**
     * Doesn't check creation of job
     *
     * @see <a href="https://cloud.google.com/bigquery/docs/reference/standard-sql/enabling-standard-sql">Enabling Standard SQL</a>
     */
    private static void readValuesWithoutCheckCreateJob(final BigQuery bigQuery, final String queryString) {
        final Stopwatch stopwatch = new Stopwatch();
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(queryString)
                .setUseLegacySql(false)
                .build();

        // Create a job ID so that we can
        JobId jobId = JobId.of(UUID.randomUUID().toString());

        System.out.println("Running query...");
        stopwatch.start();
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig)
                .setJobId(jobId)
                .build());

        // Wait for query to complete
        try {
            System.out.println("Waiting result...");
            // Get the results.
            TableResult result = queryJob.getQueryResults(BigQuery.QueryResultsOption.pageSize(10));
            final Stopwatch stopwatch2 = new Stopwatch();
            System.out.printf("getQueryResults stopwatch = %d ms.%n", stopwatch2.duration());
            System.out.printf("Stopwatch = %d ms.%n", stopwatch.duration());
            // Check for errors
            if (queryJob.getStatus().getError() != null) {
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }

            FieldList fields = result.getSchema().getFields();
            int numberOfFields = fields.size();

            // Print all pages of the results.
            int page = 0;
            while (result != null) {
                System.out.printf("page=%d%n", ++page);
                printResults(result, numberOfFields, fields);
                result = result.getNextPage();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks creation of job
     *
     * @see <a href="https://cloud.google.com/bigquery/docs/reference/standard-sql/enabling-standard-sql">Enabling Standard SQL</a>
     */
    private static void readValuesWithCheckCreateJob(final BigQuery bigQuery, final String queryString) {
        System.out.println("==>readValuesWithCheckCreateJob");
        final Stopwatch stopwatch = new Stopwatch();
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(queryString)
                .setUseLegacySql(false)
                .build();

        // Create a job ID so that we can
        JobId jobId = JobId.of(UUID.randomUUID().toString());

        System.out.println("Creating job");
        stopwatch.start();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig)
                .setJobId(jobId)
                .build());
        try {
            // Wait fot the query to complete
            queryJob = queryJob.waitFor();
        } catch (InterruptedException e) {
            System.out.printf("Interrupted creating job. Error: %s%n", e.getMessage());
            return;
        } catch (BigQueryException e) {
            System.out.printf("Failed creating job for queryString: [%s]. Error: %s%n", queryString, e.getMessage());
            return;
        }
        System.out.printf("Created job in %d ms.%n", stopwatch.duration());

        // Check for errors
        if (queryJob == null) {
            System.out.println("Job no longer exists");
            return;
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            System.out.printf("Failed creating job for queryString: [%s] with error: %s %n", queryConfig, queryJob.getStatus().getError().toString());
            return;
        }

        // Get the result
        try{
            stopwatch.start();
            TableResult result = queryJob.getQueryResults(BigQuery.QueryResultsOption.pageSize(10));
            System.out.printf("Read 1st page in %d ms.%n", stopwatch.duration());
            FieldList fields = result.getSchema().getFields();
            int numberOfFields = fields.size();
            // Print all pages of the results.
            int page = 0;
            while (result != null) {
                System.out.printf("page=%d%n", ++page);
                printResults(result, numberOfFields, fields);
                stopwatch.start();
                result = result.getNextPage();
                System.out.printf("Read %d page in %d ms.%n", page, stopwatch.duration());
            }
        }
        catch(InterruptedException e){
            System.out.printf("Interrupted getting results with error %s%n", e.getMessage());
            return;
        }
        catch(BigQueryException e){
            System.out.printf("Failed getting results with error (BigQueryException) %s%n", e.getMessage());
            return;
        }
        System.out.println("<==readValuesWithCheckCreateJob");
    }

    private static void printResults(final TableResult result, final int numberOfFields, final FieldList fields){
        for (List<FieldValue> row : result.iterateAll()) {
            for (int fieldIndex = 0; fieldIndex < numberOfFields; ++fieldIndex) {
                FieldValue val = row.get(fieldIndex);
                System.out.printf("%s=%s, ", fields.get(fieldIndex).getName(), val.getValue().toString());
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        System.out.println("==>main");
        try {
            RemoteBigQueryHelper bigqueryHelper = RemoteBigQueryHelper.create();
            BigQuery bigQuery = bigqueryHelper.getOptions().getService();
            String dataSetName = RemoteBigQueryHelper.generateDatasetName();
            System.out.format("dataSetName: %s%n", dataSetName);
            Dataset dataset = bigQuery.create(DatasetInfo.newBuilder(dataSetName).build());
            // Create table
            String tableName = "test1";
            String nameFieldName = "name";
            String ageFieldName = "age";
            Schema schema = Schema.of(Field.of(nameFieldName, LegacySQLTypeName.STRING), Field.of(ageFieldName, LegacySQLTypeName.INTEGER));
            StandardTableDefinition tableDefinition = StandardTableDefinition.newBuilder()
                    .setSchema(schema)
                    .setTimePartitioning(TimePartitioning.of(TimePartitioning.Type.DAY))
                    .build();

            stopwatch.start();
            Table table = dataset.create(tableName, tableDefinition);
            stopwatch.stop();
            System.out.println("Created table " + tableName + " during " + stopwatch.duration() + " ms");

            // Add rows
            InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(table);
            int n = 500;
            for (int i = 1; i <= n; ++i) {
                Map<String, Object> recordContent = new HashMap<>();
                recordContent.put(nameFieldName, "yuriys " + i);
                recordContent.put(ageFieldName, i);
                builder.addRow(recordContent);
            }
            stopwatch.start();
            InsertAllResponse response = bigQuery.insertAll(builder.build());
            stopwatch.stop();
            System.out.println("Inserted during " + stopwatch.duration() + " ms");
            if (response.hasErrors()) {
                System.out.println("Errors in insert");
                System.out.println(response.getInsertErrors().toString());
            } else {
                System.out.println("Inserted " + n + " records");
            }

            System.out.println("Tables:");
            dataset.list().iterateAll().forEach(t -> System.out.format("getFriendlyName()=%s%n", t));

//            readValuesWithoutCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 10");
            readValuesWithoutCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 30");
//            readValuesWithoutCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName);

            readValuesWithCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 30");
            readValuesWithCheckCreateJob(bigQuery, "SELECT * FROMERROR " + dataSetName + "." + tableName + " LIMIT 30");

            RemoteBigQueryHelper.forceDelete(bigQuery, dataSetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==main");
    }
}
