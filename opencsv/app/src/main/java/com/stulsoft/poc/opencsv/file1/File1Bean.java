/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.opencsv.file1;

import com.opencsv.bean.CsvBindByName;

/**
 * @author Yuriy Stul
 */
public class File1Bean {
    @CsvBindByName
    private String header_1;

    @CsvBindByName
    private Double header_2;

    @CsvBindByName
    private Integer header_3;

    @Override
    public String toString() {
        return "File1Bean: "
                + "header_1=[" + header_1 + "]"
                + ", header_2=[" + header_2 + "]"
                + ", header_3=[" + header_3 + "]";
    }
}
