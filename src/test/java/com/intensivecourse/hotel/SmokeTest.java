package com.intensivecourse.hotel;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class SmokeTest {

    @Test
    void junitWorks(){
        String hello = "Hello, JUnit!";
        assertNotNull(hello);
        assertEquals("Hello, JUnit!", hello);
        assertTrue(hello.length() > 0);
    }

    @Test
    void mockitoWorks(){
        List<String> mockedList = mock(List.class);
        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
        verify(mockedList, times(1)).size();
    }

}
