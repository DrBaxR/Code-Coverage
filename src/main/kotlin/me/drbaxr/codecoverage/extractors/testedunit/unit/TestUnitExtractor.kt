package me.drbaxr.codecoverage.extractors.testedunit.unit

import me.drbaxr.codecoverage.models.CodeUnit

interface TestUnitExtractor {

    fun findTestUnits(filePath: String): List<CodeUnit>

}