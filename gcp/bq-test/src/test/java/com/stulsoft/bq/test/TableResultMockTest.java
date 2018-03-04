package com.stulsoft.bq.test;

import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.TableResult;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Attempt to mock BQ (unsuccessful)
 * <p>
 * BQ doesn't have public methods to create TableResult
 *
 * @author Yuriy Stul
 * @since 3/1/2018
 */
public class TableResultMockTest {
    @Test
    public void test1() {
        TableResult tableResultMock = mock(TableResult.class);
        when(tableResultMock.getNextPage()).thenReturn(null);
        assertNull(tableResultMock.getNextPage());
    }
}
