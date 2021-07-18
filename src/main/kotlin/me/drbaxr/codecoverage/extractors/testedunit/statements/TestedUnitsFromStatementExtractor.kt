package me.drbaxr.codecoverage.extractors.testedunit.statements

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.models.CodeStatement

interface TestedUnitsFromStatementExtractor {

    fun findTestedUnitsFromStatement(codeStatement: CodeStatement): List<CodeUnit>

}