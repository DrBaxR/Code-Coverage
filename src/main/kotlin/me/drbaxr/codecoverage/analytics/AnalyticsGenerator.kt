package me.drbaxr.codecoverage.analytics

import me.drbaxr.codecoverage.models.Analytics
import me.drbaxr.codecoverage.models.CodeUnit

class AnalyticsGenerator {

    fun generate(allUnits: List<CodeUnit>, testedUnits: List<CodeUnit>): Analytics {
        val analytics = Analytics(
            testedUnits.size.toFloat() / allUnits.size.toFloat() * 100,
            calculateLineCoverage(allUnits, testedUnits),
        )

//        println("Code Coverage: ${testedUnits.size.toFloat() / allUnits.size.toFloat() * 100}%")
//        println("Line Coverage: ${calculateLineCoverage(allUnits, testedUnits)}%")

        HtmlGenerator().generate(allUnits, testedUnits, analytics)

        return analytics
    }

    private fun calculateLineCoverage(allUnits: List<CodeUnit>, testedUnits: List<CodeUnit>): Float {
        val totalTestableLines = allUnits.fold(0.0f) { acc, unit -> acc + unit.linesRange.last - unit.linesRange.first }
        val testedLines = testedUnits.fold(0.0f) { acc, unit -> acc + unit.linesRange.last - unit.linesRange.first }

        return testedLines / totalTestableLines * 100
    }
}