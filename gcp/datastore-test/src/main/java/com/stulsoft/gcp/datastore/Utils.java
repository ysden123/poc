/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.gcp.datastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuriy Stul
 */
public class Utils {
    private Utils() {
    }

    public static void addManyValues(Datastore datastore, int n) {
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
            List<Entity> entities = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                // The name/ID for the new entity
                String name = String.format("sampletask %d", i);
                // The Cloud Datastore key for the new entity
                Key taskKey = keyFactory.newKey(name);

                // Prepares the new entity
                Entity task = Entity.newBuilder(taskKey)
                        .set("description", String.format("Buy milk %d", i))
                        .build();
                entities.add(task);
            }
            // Saves the entity
            datastore.put(entities.toArray(new Entity[entities.size()]));
            duration.stop();
            totalDuration.stop();
            System.out.printf("Added %d entities during %d ms. Total duration is %d ms. %n", n, duration.duration(), totalDuration.duration());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<==addManyValues2");
    }
}
