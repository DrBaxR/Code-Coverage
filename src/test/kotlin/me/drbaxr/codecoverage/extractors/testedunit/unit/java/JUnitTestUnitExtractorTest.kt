package me.drbaxr.codecoverage.extractors.testedunit.unit.java

import me.drbaxr.codecoverage.models.CodeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class JUnitTestUnitExtractorTest {

    private val junitTestUnitExtractor = JUnitTestUnitExtractor()

    @Test
    fun `for empty file will find no units`() {
        assertEquals(listOf(), junitTestUnitExtractor.findTestUnits("src/test/resources/test-unit-extractor/junit/Empty.java"))
    }

    @Test
    fun `for regularly written file will find units annotated with Test`() {
        val filePath = "src/test/resources/test-unit-extractor/junit/Simple.java"

        val expected = listOf(
            CodeUnit("public void m5() {", filePath, 58..62, CodeUnit.UnitTypes.TEST),
            CodeUnit("public void m7() {", filePath, 70..72, CodeUnit.UnitTypes.TEST),
            CodeUnit("public void m8() {", filePath, 75..78, CodeUnit.UnitTypes.TEST),
        )

        assertEquals(expected, junitTestUnitExtractor.findTestUnits(filePath))
    }

    @Test
    fun `for fucked up spacing will still work`() {
        val filePath = "src/test/resources/test-unit-extractor/junit/FuckedUp.java"

        val expected = listOf(
            CodeUnit("public void m5()", filePath, 67..71, CodeUnit.UnitTypes.TEST),
            CodeUnit("public void m7()", filePath, 82..84, CodeUnit.UnitTypes.TEST),
            CodeUnit("public void m8(){", filePath, 94..97, CodeUnit.UnitTypes.TEST),
        )

        assertEquals(expected, junitTestUnitExtractor.findTestUnits(filePath))
    }

}