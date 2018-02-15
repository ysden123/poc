/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper;
import com.google.cloud.bigquery.TableResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Yuriy Stul
 */
public class Main1 {
    /**
     * @see <a href="https://cloud.google.com/bigquery/docs/reference/standard-sql/enabling-standard-sql">Enabling Standard SQL</a>
     */
    private static void readValue(final BigQuery bigQuery, final String queryString) {
        final Duration duration = new Duration();
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(queryString)
                .setUseLegacySql(false)
                .build();

        // Create a job ID so that we can
        JobId jobId = JobId.of(UUID.randomUUID().toString());

        System.out.println("Running query...");
        duration.start();
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig)
                .setJobId(jobId)
                .build());

        // Wait for query to complete
        try {
            System.out.println("Waiting result...");
            queryJob = queryJob.waitFor();

            System.out.printf("Duration = %d ms.%n",duration.duration());

            // Check for errors
            if (queryJob == null){
                throw new RuntimeException("Job no longer exists");
            }else if (queryJob.getStatus().getError() != null){
                // You can also look at queryJob.getStatus().getExecutionErrors() for all
                // errors, not just the latest one.
                throw new RuntimeException(queryJob.getStatus().getError().toString());
            }
            // Get the results.
            TableResult result = queryJob.getQueryResults();

            FieldList fields = result.getSchema().getFields();
            int numberOfFields = fields.size();

            // Print all pages of the results.
            while (result != null) {
                for (List<FieldValue> row : result.iterateAll()) {
                    for(int fieldIndex = 0; fieldIndex < numberOfFields; ++ fieldIndex){
                        FieldValue val = row.get(fieldIndex);
                        System.out.printf("%s=%s, ", fields.get(fieldIndex).getName(), val.getValue().toString());
                    }
/*
                    for (FieldValue val : row) {
                        System.out.printf("%s->%s,", val.getAttribute().name(), val.toString());
                    }
*/
                    System.out.println("");
                }
                result = result.getNextPage();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Duration duration = new Duration();
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

            duration.start();
            Table table = dataset.create(tableName, tableDefinition);
            duration.stop();
            System.out.println("Created table " + tableName + " during " + duration.duration() + " ms");

            // Add rows
            InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(table);
            int n = 500;
            for (int i = 1; i <= n; ++i) {
                Map<String, Object> recordContent = new HashMap<>();
                recordContent.put(nameFieldName, "yuriys " + i);
                recordContent.put(ageFieldName, i);
                builder.addRow(recordContent);
            }
            duration.start();
            InsertAllResponse response = bigQuery.insertAll(builder.build());
            duration.stop();
            System.out.println("Inserted during " + duration.duration() + " ms");
            if (response.hasErrors()) {
                System.out.println("Errors in insert");
                System.out.println(response.getInsertErrors().toString());
            } else {
                System.out.println("Inserted " + n + " records");
            }

            System.out.println("Tables:");
            dataset.list().iterateAll().forEach(t -> System.out.format("getFriendlyName()=%s%n", t));

            readValue(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 10");

            RemoteBigQueryHelper.forceDelete(bigQuery, dataSetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==main");
    }
}
