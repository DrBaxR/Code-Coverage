package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.extractors.testedunit.statements.TestStatementsExtractor
import me.drbaxr.codecoverage.extractors.testedunit.statements.TestedUnitsFromStatementExtractor
import me.drbaxr.codecoverage.models.CodeUnit

abstract class TestedUnitExtractor(testFiles: List<String>, units: List<CodeUnit>) {

    protected abstract val testStatementsFinder: TestStatementsExtractor
    protected abstract val testedUnitsFromStatementExtractor: TestedUnitsFromStatementExtractor

    abstract fun findTestedUnits(): List<CodeUnit>

}