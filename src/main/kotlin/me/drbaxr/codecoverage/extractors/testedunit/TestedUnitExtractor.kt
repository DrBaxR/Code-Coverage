package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.models.CodeUnit

abstract class TestedUnitExtractor(val allCodeUnits: List<CodeUnit>) {

    abstract fun findTestedUnits(projectPath: String, testFile: String): List<CodeUnit>

}