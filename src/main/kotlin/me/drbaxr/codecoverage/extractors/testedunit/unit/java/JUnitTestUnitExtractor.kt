package me.drbaxr.codecoverage.extractors.testedunit.unit.java

import me.drbaxr.codecoverage.extractors.testedunit.unit.TestUnitExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaCommentRemover
import me.drbaxr.codecoverage.extractors.unit.java.JavaMethodHeaderIdentifier
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools

// todo: maybe rename this to annotation test unit extractor
class JUnitTestUnitExtractor : TestUnitExtractor {

    private val testAnnotations = listOf("@Test")
    private val commentRemover = JavaCommentRemover()
    private val methodHeaderIdentifier = JavaMethodHeaderIdentifier()

    private var fileLines = listOf<String>()

    override fun findTestUnits(filePath: String): List<CodeUnit> {
        fileLines = FileTools.getFileLines(filePath)

        val sourceLines = commentRemover.removeCommentLines(fileLines)
        val testAnnotationLines = getTestAnnotationLines(sourceLines)

        return testAnnotationLines.map { getCodeUnit(filePath, getFunctionHeaderLine(it)) }
    }

    private fun getCodeUnit(filePath: String, headerLineIndex: Int): CodeUnit {
        val bracesLines = UnitTools.findMatchingCurlyBrace(filePath, headerLineIndex) ?: Pair(-1, -1)
        return CodeUnit(fileLines[headerLineIndex].trim(), filePath, bracesLines.first..bracesLines.second, CodeUnit.UnitTypes.TEST)
    }

    private fun getFunctionHeaderLine(annotationLine: Int): Int {
        var headerLineIndex = annotationLine
        while (!methodHeaderIdentifier.isHeader(fileLines[headerLineIndex]))
            headerLineIndex++
        return headerLineIndex
    }

    private fun getTestAnnotationLines(sourceLines: List<Pair<Int, String>>): List<Int> =
        sourceLines
            .filter { testAnnotations.any { annotation -> it.second.trim().startsWith(annotation) } }
            .map { it.first }

}