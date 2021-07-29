package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.models.CodeUnit

abstract class TestedUnitExtractor(testFiles: List<String>, units: List<CodeUnit>) {

    abstract fun findTestedUnits(): List<CodeUnit>

}