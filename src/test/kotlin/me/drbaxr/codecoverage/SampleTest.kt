package me.drbaxr.codecoverage

import org.junit.Test
import kotlin.test.assertEquals

internal class SampleTest {

    private val testSample = Sample()

    @Test
    fun sum() {
        assertEquals(42, testSample.sum(40, 2))
        assertEquals(2, testSample.sum(1, 1))
    }

    @Test
    fun diff() {
        assertEquals(-1, testSample.diff(0, 1))
        assertEquals(12, testSample.diff(14, 2))
    }
}