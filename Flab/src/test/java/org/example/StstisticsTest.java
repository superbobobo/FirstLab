package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        statistics = new Statistics();
    }

    @Test
    void testAddInteger() {
        statistics.addInteger(10);
        statistics.addInteger(5);
        statistics.addInteger(20);

        assertEquals(3, statistics.getIntegerCount());
        assertEquals(5, statistics.getMinInteger());
        assertEquals(20, statistics.getMaxInteger());
    }

    @Test
    void testAddFloat() {
        statistics.addFloat(10.5);
        statistics.addFloat(3.2);
        statistics.addFloat(15.7);

        assertEquals(3, statistics.getFloatCount());
        assertEquals(3.2, statistics.getMinFloat(), 0.001);
        assertEquals(15.7, statistics.getMaxFloat(), 0.001);
        assertEquals(29.4, statistics.getFloatSum(), 0.001);
    }

    @Test
    void testAddString() {
        statistics.addString("hello");
        statistics.addString("world");
        statistics.addString("test");

        assertEquals(3, statistics.getStringCount());
        assertEquals(4, statistics.getMinStringLength());
        assertEquals(5, statistics.getMaxStringLength());
    }
}