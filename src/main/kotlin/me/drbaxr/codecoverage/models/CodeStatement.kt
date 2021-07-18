package me.drbaxr.codecoverage.models

import me.drbaxr.codecoverage.util.FileTools

data class CodeStatement(
    val hostTestUnit: CodeUnit,
    val linesRange: IntRange,
) {

    val statement: List<String>
        get() = FileTools.getFileLines(hostTestUnit.hostFilePath).subList(linesRange.first - 1, linesRange.last)
}