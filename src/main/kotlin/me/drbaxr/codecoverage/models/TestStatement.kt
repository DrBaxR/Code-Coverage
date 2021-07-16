package me.drbaxr.codecoverage.models

import me.drbaxr.codecoverage.extractors.testedunit.statements.TestedUnitsFromStatementExtractor
import me.drbaxr.codecoverage.util.FileTools

data class TestStatement(
    val hostFilePath: String,
    val linesRange: IntRange,
) {

    val statement: List<String>
        get() = FileTools.getFileLines(hostFilePath).subList(linesRange.first - 1, linesRange.last)
}