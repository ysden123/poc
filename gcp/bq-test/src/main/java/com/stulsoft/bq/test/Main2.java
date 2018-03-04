package com.stulsoft.bq.test;

import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.testing.RemoteBigQueryHelper;

import java.util.logging.Logger;

import static com.stulsoft.bq.test.BQUtils.*;

/**
 * @author Yuriy Stul
 * @since 3/4/2018
 */
public class Main2 {
    private static final Logger logger = LoggerUtils.getLogger(Main2.class.getName());

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        logger.info("==>main");
        BigQuery bigQuery = BigQueryOptions.getDefaultInstance().getService();
        try {
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
            addRows(n, builder, nameFieldName, ageFieldName);
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

            readValuesWithoutCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 30");
            readValuesWithCheckCreateJob(bigQuery, "SELECT * FROM " + dataSetName + "." + tableName + " LIMIT 30");
            readValuesWithCheckCreateJob(bigQuery, "SELECT * FROMERROR " + dataSetName + "." + tableName + " LIMIT 30");

            RemoteBigQueryHelper.forceDelete(bigQuery, dataSetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("<==main");
    }
}
