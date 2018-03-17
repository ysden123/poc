package com.stulsoft.bq.test;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Yuriy Stul
 * @since 3/1/2018
 */
public class MockitoTest {
    @Test
    public void mockTest() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }
}
