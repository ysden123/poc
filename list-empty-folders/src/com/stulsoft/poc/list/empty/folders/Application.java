/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.list.empty.folders;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Yuriy Stul
 */
public class Application {
    public static void main(String[] args) {
        System.out.println("==>main");
        var start = System.currentTimeMillis();
        lookInBaseFolder(Paths.get("e:\\Photo\\YSPhoto\\Images\\"));
        System.out.printf("Done in %d ms %n", System.currentTimeMillis() - start);
    }

    private static void lookInBaseFolder(Path path) {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                var iterator = directory.iterator();
                var emptyFolders = new LinkedList<String>();
                while (iterator.hasNext()) {
                    checkIfFolderIsEmpty(iterator.next()).ifPresent(emptyFolders::add);
                }

                System.out.println("List of empty folders:");
                emptyFolders.forEach(System.out::println);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static Optional<String> checkIfFolderIsEmpty(Path path) {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return directory.iterator().hasNext() ? Optional.empty() : Optional.of(path.toString());
            } catch (Exception exception) {
                exception.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
