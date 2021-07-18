package me.drbaxr.codecoverage.extractors.testedunit.statements.java

import me.drbaxr.codecoverage.models.CodeUnit
import kotlin.test.Test
import kotlin.test.assertEquals

class JUnitTestStatementsExtractorTest {

    private val statementExtractor = JUnitTestStatementsExtractor()

    @Test
    fun `when unit has no test statements will find no statements`() {
        val testedUnit = CodeUnit(
            "test",
            "src/test/resources/test-statements/junit/Empty.java",
            4..9
        )

        assertEquals(listOf(), statementExtractor.findTestStatements(testedUnit))
    }

    @Test
    fun `one line statements should be found`() {

    }

}