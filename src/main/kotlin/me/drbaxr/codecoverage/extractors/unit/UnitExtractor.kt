package me.drbaxr.codecoverage.extractors.unit

import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.models.CodeUnit

abstract class UnitExtractor(projectPath: String, testFileExtractor: TestFileExtractor) {

    abstract fun findUnits(): List<CodeUnit>

}