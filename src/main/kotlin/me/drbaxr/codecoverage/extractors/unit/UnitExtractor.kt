package me.drbaxr.codecoverage.extractors.unit

import me.drbaxr.codecoverage.models.CodeUnit

abstract class UnitExtractor(projectPath: String) {

    abstract fun findUnits(): List<CodeUnit>

}