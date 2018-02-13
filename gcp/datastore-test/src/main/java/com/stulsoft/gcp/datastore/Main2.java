/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.gcp.datastore;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.testing.RemoteDatastoreHelper;

import static com.stulsoft.gcp.datastore.Utils.addManyValues;

/**
 * Playing with RemoteDatastoreHelper
 *
 * @author Yuriy Stul
 */
public class Main2 {
//    private static void addManyValues1(Datastore datastore, int n) {
//        System.out.println("==>addManyValues1");
//        try {
//            Duration duration = new Duration();
//            // The kind for the new entity
//            String kind = "Task";
//            KeyFactory keyFactory = datastore.newKeyFactory();
//
//            for (int i = 1; i <= n; ++i) {
//                // The name/ID for the new entity
//                String name = String.format("sampletask %d", i);
//                // The Cloud Datastore key for the new entity
//                Key taskKey = keyFactory.setKind(kind).newKey(name);
//
//                // Prepares the new entity
//                Entity task = Entity.newBuilder(taskKey)
//                        .set("description", String.format("Buy milk %d", i))
//                        .build();
//
//                // Saves the entity
//                datastore.put(task);
//            }
//            System.out.printf("Added %d entities during %d ms.%n", n, duration.duration());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("<==addManyValues1");
//    }

    public static void main(String[] args) {
        try {
            RemoteDatastoreHelper helper = RemoteDatastoreHelper.create();
            Datastore datastore = RemoteDatastoreHelper.create()
                    .getOptions()
                    .getService();

            addManyValues(datastore, 50);
            helper.deleteNamespace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
