/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.gcp.datastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.testing.RemoteDatastoreHelper;

/**
 * Playing with RemoteDatastoreHelper
 *
 * @author Yuriy Stul
 */
public class Main2 {
    private static void addManyValues1(Datastore datastore, int n) {
        System.out.println("==>addManyValues1");
        try {
            Duration duration = new Duration();
            // The kind for the new entity
            String kind = "Task";
            KeyFactory keyFactory = datastore.newKeyFactory();

            for (int i = 1; i <= n; ++i) {
                // The name/ID for the new entity
                String name = String.format("sampletask %d", i);
                // The Cloud Datastore key for the new entity
                Key taskKey = keyFactory.setKind(kind).newKey(name);

                // Prepares the new entity
                Entity task = Entity.newBuilder(taskKey)
                        .set("description", String.format("Buy milk %d", i))
                        .build();

                // Saves the entity
                datastore.put(task);
            }
            System.out.printf("Added %d entities during %d ms.%n", n, duration.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==addManyValues1");
    }

    private static void addManyValues2(Datastore datastore, int n) {
        System.out.println("==>addManyValues2");
        try {
            Duration totalDuration = new Duration();
            // The kind for the new entity
            String kind = "Task";
            Duration factoryDuration = new Duration();
            KeyFactory keyFactory = datastore.newKeyFactory();
            keyFactory.setKind(kind);
            System.out.printf("Factory creation duration is %d ms.%n", factoryDuration.duration());

            Duration duration = new Duration();
            long durationSum = 0L;
            for (int i = 1; i <= n; ++i) {
                duration.start();
                // The name/ID for the new entity
                String name = String.format("sampletask %d", i);
                // The Cloud Datastore key for the new entity
                Key taskKey = keyFactory.newKey(name);

                // Prepares the new entity
                Entity task = Entity.newBuilder(taskKey)
                        .set("description", String.format("Buy milk %d", i))
                        .build();

                // Saves the entity
                datastore.put(task);
                durationSum += duration.duration();
            }
            System.out.printf("Added %d entities during %d ms.%n", n, totalDuration.duration());
            System.out.printf("durationSum = %d ms. Average duration for one entity is %d ms.%n", durationSum,
                    durationSum / n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==addManyValues2");
    }

    public static void main(String[] args) {
        try {
            RemoteDatastoreHelper helper = RemoteDatastoreHelper.create();
            Datastore datastore = RemoteDatastoreHelper.create()
                    .getOptions()
                    .getService();

//            addManyValues1(datastore, 2);
            addManyValues2(datastore, 50);
            helper.deleteNamespace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
