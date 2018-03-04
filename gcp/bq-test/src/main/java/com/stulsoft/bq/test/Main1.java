/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper;

import static com.stulsoft.bq.test.BQUtils.*;

/**
 * @author Yuriy Stul
 */
public class Main1 {
    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        System.out.println("==>main");
        try {
            RemoteBigQueryHelper bigQueryHelper = RemoteBigQueryHelper.create();
            BigQuery bigQuery = bigQueryHelper.getOptions().getService();

/*
            FileInputStream is = new FileInputStream(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
            BigQuery bigQuery = RemoteBigQueryHelper.create("api-project-829216641821", is).getOptions().getService();
*/


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
            int n = 500;
            addRows(n, table, nameFieldName, ageFieldName);
            InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(table);
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
