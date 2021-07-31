package me.drbaxr.codecoverage.extractors.testedunit.occurence

import me.drbaxr.codecoverage.models.CodeUnit

abstract class UnitOccurrenceExtractor(val allCodeUnits: List<CodeUnit>) {

    abstract fun findOccurrences(testUnit: CodeUnit): List<CodeUnit>

}