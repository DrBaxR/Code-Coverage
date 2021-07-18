package me.drbaxr.codecoverage.extractors.testedunit.statements.java

import me.drbaxr.codecoverage.extractors.testedunit.statements.TestStatementsExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.models.CodeStatement

class JUnitTestStatementsExtractor : TestStatementsExtractor {

    override fun findTestStatements(unit: CodeUnit): List<CodeStatement> {
        return listOf()
    }

}