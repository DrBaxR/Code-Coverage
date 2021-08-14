package me.drbaxr.codecoverage.extractors.testunit

import me.drbaxr.codecoverage.models.CodeUnit

// TODO: maybe remove this, since it is not used in tested unit extractor
interface TestUnitExtractor {

    fun findTestUnits(filePath: String): List<CodeUnit>

}