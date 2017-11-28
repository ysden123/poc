/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */

/**
 * @author Yuriy Stul.
 */
public class TestRunner {
    static void test1() {
        System.out.println("==>test1");
        FS fs = new FS();

        fs.AddDir("test1", "red", 1, 0);
        fs.AddFile("file1", 2, "blue", 1);
        fs.ShowDirsandFile();

        System.out.println("<==test1");
    }

    static void test2() {
        System.out.println("==>test2");
        FS fs = new FS();

        fs.AddDir("test1", "red", 1, 0);
        fs.AddFile("file1", 2, "blue", 1);

        fs.AddDir("test12", "red", 3, 0);
        fs.AddFile("file1", 4, "blue", 3);

        fs.DeleteDir(1);

        fs.ShowDirsandFile();
        System.out.println("<==test2");
    }

    static void test3() {
        System.out.println("==>test3");
        FS fs = new FS();

        fs.AddDir("test1", "red", 1, 0);
        fs.AddFile("file1", 2, "blue", 1);

        fs.AddDir("test12", "red", 3, 0);
        fs.AddFile("file11", 4, "blue", 3);

        fs.DeleteFile(4);

        fs.ShowDirsandFile();
        System.out.println("<==test3");
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }
}
