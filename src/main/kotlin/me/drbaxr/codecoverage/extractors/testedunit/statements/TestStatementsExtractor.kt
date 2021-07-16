package me.drbaxr.codecoverage.extractors.testedunit.statements

import me.drbaxr.codecoverage.models.TestStatement

interface TestStatementsExtractor {

    fun findTestStatements(): List<TestStatement>

}