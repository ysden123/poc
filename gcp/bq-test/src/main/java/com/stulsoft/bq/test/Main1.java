/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.bq.test;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yuriy Stul
 */
public class Main1 {
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
            Schema schema = Schema.of(Field.of(nameFieldName, LegacySQLTypeName.STRING),Field.of(ageFieldName, LegacySQLTypeName.INTEGER));
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
            if (response.hasErrors()){
                System.out.println("Errors in insert");
                System.out.println(response.getInsertErrors().toString());
            }else{
                System.out.println("Inserted " + n + " records");
            }

            System.out.println("Tables:");
            dataset.list().iterateAll().forEach(t -> System.out.format("getFriendlyName()=%s%n", t));

            RemoteBigQueryHelper.forceDelete(bigQuery, dataSetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==main");
    }
}
