package me.drbaxr.codecoverage.util

import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class UnitToolsTest {

    @Test
    fun `test matching braces for empty file`() {
        assertEquals(null, UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Empty.java", 1))
    }

    @Test
    fun `test matching braces for simple case`() {
        assertEquals(Pair(1, 17), UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Simple.java", 1))
        assertEquals(Pair(3, 15), UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Simple.java", 3))
        assertEquals(Pair(12, 12), UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Simple.java", 12))
    }

    @Test
    fun `test matching braces for complex case`() {
        assertEquals(Pair(3, 13), UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Complex.java", 1))
        assertEquals(Pair(7, 10), UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Complex.java", 5))
    }

    @Test
    fun `test matching braces failures`() {
        assertFailsWith<StartingBraceNotFoundException> { UnitTools.findMatchingCurlyBrace("src/test/resources/unit-tools/Complex.java", 14) }
    }

    @Test
    fun `test find unit headers`() {

    }
}