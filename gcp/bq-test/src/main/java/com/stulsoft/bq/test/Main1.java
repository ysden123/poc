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

            Table table = dataset.create(tableName, tableDefinition);
            System.out.println("Created table " + tableName);

            // Add row
            InsertAllRequest.Builder builder = InsertAllRequest.newBuilder(table);
            for(int i = 1; i <= 3; ++i){
                Map<String, Object> recordContent = new HashMap<>();
                recordContent.put(nameFieldName, "yuriys " + i);
                recordContent.put(ageFieldName, i);
                builder.addRow(recordContent);
            }
            InsertAllResponse response = bigQuery.insertAll(builder.build());
            if (response.hasErrors()){
                System.out.println("Errors in insert");
                System.out.println(response.getInsertErrors().toString());
            }else{
                System.out.println("Inserted 3 records");
            }

            System.out.println("Tables:");
            dataset.list().iterateAll().forEach(t -> System.out.format("getFriendlyName()=%s%n", t));

//            RemoteBigQueryHelper.forceDelete(bigquery, dataSetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==main");
    }
}
