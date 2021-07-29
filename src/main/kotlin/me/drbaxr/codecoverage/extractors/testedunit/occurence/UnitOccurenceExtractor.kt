package me.drbaxr.codecoverage.extractors.testedunit.occurence

import me.drbaxr.codecoverage.models.CodeUnit

interface UnitOccurrenceExtractor {

    fun findOccurrences(unit: CodeUnit): List<CodeUnit>

}