/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.stulsoft.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;

public class MetadataApp {
    public static void main(String[] args) {
        System.out.println("==>main");
        File file = new File("d:\\Photo\\YSPhoto\\Images\\2022\\2022-11-03\\2022 11 03 006.NEF");
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.printf("[%s] - %s = %s%n",
                            directory.getName(), tag.getTagName(), tag.getDescription());
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.printf("ERROR: %s%n", error);
                    }
                }
            }

            System.out.println("GPS tags:");
            Directory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            gpsDirectory.getTags().forEach(tag -> System.out.printf("tag: %s%n", tag));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}