package me.drbaxr.codecoverage.analytics

import me.drbaxr.codecoverage.models.CodeUnit

class AnalyticsGenerator {

    fun generate(allUnits: List<CodeUnit>, testedUnits: List<CodeUnit>) {
        // TODO: check https://j2html.com/examples.html and make html

//        println("All Units Found:")
//        allUnits.forEach { println(it.identifier) }
//
//        println("\nTested Units Found:")
//        testedUnits.forEach { println(it.identifier) }

        println("Code Coverage: ${testedUnits.size.toFloat() / allUnits.size.toFloat() * 100}%")
        println("Line Coverage: ${calculateLineCoverage(allUnits, testedUnits)}%")

        HtmlGenerator().generate()
    }

    private fun calculateLineCoverage(allUnits: List<CodeUnit>, testedUnits: List<CodeUnit>): Float {
        val totalTestableLines = allUnits.fold(0.0f) { acc, unit -> acc + unit.linesRange.last - unit.linesRange.first }
        val testedLines = testedUnits.fold(0.0f) { acc, unit -> acc + unit.linesRange.last - unit.linesRange.first }

        return testedLines / totalTestableLines * 100
    }

    private fun getFilesToUnitsMap(units: List<CodeUnit>): Map<String, Set<CodeUnit>> {
        val filesToUnitsMap = mutableMapOf<String, MutableSet<CodeUnit>>()

        units.forEach {
            val unitsSet = filesToUnitsMap[it.hostFilePath]

            if (unitsSet != null) {
                unitsSet.add(it)
            } else {
                filesToUnitsMap[it.hostFilePath] = mutableSetOf()
            }
        }

        return filesToUnitsMap
    }

}