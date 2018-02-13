/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.gcp.datastore;

import com.google.cloud.datastore.*;

/**
 * @author Yuriy Stul
 * @see <a href="https://cloud.google.com/datastore/docs/reference/libraries#client-libraries-usage-java">Cloud Datastore Client Libraries</a>
 */
public class Main1 {

    private static void addManyValues(Datastore datastore, int n) {
        System.out.println("==>addManyValues");
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
        System.out.println("<==addManyValues");
    }

    public static void main(String... args) {
        try {
            // Instantiates a client
            Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

            addManyValues(datastore, 50);
//
//            // The kind for the new entity
//            String kind = "Task";
//            // The name/ID for the new entity
//            String name = "sampletask1";
//            // The Cloud Datastore key for the new entity
//            Key taskKey = datastore.newKeyFactory().setKind(kind).newKey(name);
//
//            // Clear data store
//            datastore.delete(taskKey);
//
//            // Prepares the new entity
//            Entity task = Entity.newBuilder(taskKey)
//                    .set("description", "Buy milk")
//                    .build();
//            System.out.printf("Created entity: %s%n", task.toString());
//
//            // Saves the entity
//            task = datastore.put(task);
//            System.out.printf("Entity after put: %s%n", task.toString());
//
//            System.out.printf("Saved %s: %s%n", task.getKey().getName(), task.getString("description"));
//
//            //Retrieve entity
//            Entity retrieved = datastore.get(taskKey);
//            System.out.printf("Retrieved entity: %s%n", retrieved.toString());
//
//            System.out.printf("Retrieved %s: %s%n", taskKey.getName(), retrieved.getString("description"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
