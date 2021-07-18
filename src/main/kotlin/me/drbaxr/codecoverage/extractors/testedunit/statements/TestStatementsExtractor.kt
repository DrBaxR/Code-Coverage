package me.drbaxr.codecoverage.extractors.testedunit.statements

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.models.CodeStatement

interface TestStatementsExtractor {

    fun findTestStatements(unit: CodeUnit): List<CodeStatement>

}