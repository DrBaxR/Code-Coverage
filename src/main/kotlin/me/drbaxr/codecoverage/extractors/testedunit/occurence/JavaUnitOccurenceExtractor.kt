package me.drbaxr.codecoverage.extractors.testedunit.occurence

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools

class JavaUnitOccurrenceExtractor(allCodeUnits: List<CodeUnit>) : UnitOccurrenceExtractor(allCodeUnits) {
    // TODO: make it take test files and then look in the imports instead of this
    override fun findOccurrences(testUnit: CodeUnit): List<CodeUnit> {
        val hostFileLines = FileTools.getFileLines(testUnit.hostFilePath)
        val unitBodyLinesUnified = getUnitBodyLinesUnified(hostFileLines, testUnit)
        val unitStatements = removeWrappingUnitBraces(unitBodyLinesUnified.split(";"))
        unitStatements.forEach { println(it) }

        return listOf()
    }

    private fun removeWrappingUnitBraces(unitStatements: List<String>): List<String> {
        val firstBracketIndex = unitStatements[0].indexOfFirst { it == '{' }
        val lastBracketIndex = unitStatements[unitStatements.lastIndex].indexOfLast { it == '}' }

        return unitStatements.mapIndexed { index, line ->
            when (index) {
                0 -> line.substring(firstBracketIndex + 1, line.length)
                unitStatements.lastIndex -> {
                    if (lastBracketIndex == 0)
                        ""
                    else
                        line.substring(0, lastBracketIndex)
                }
                else -> line
            }
        }.filter { it.isNotEmpty() }
    }

    private fun getUnitBodyLinesUnified(fileLines: List<String>, unit: CodeUnit): String {
        val unitBraces = UnitTools.findMatchingCurlyBrace(unit.hostFilePath, unit.linesRange.first) ?: Pair(1, 1)

        return fileLines.subList(unitBraces.first - 1, unitBraces.second).fold("") { acc, s ->
            "$acc${s.trim()}"
        }
    }

}