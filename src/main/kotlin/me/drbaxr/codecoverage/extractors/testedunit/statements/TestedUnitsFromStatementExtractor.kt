package me.drbaxr.codecoverage.extractors.testedunit.statements

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.models.TestStatement

interface TestedUnitsFromStatementExtractor {

    fun findTestedUnitsFromStatement(testStatement: TestStatement): List<CodeUnit>

}