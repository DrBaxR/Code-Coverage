package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.UnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools
import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException

class JavaUnitExtractor(private val projectPath: String, private val testFileExtractor: TestFileExtractor) :
    UnitExtractor(projectPath) {

    override fun findUnits(): List<CodeUnit> {
        val testFiles = testFileExtractor.findTestFiles()
        val sourceFiles = FileTools.getFilesWithExtension(projectPath, ".java", testFiles)

        return sourceFiles.flatMap {
            val filePath = "$projectPath/$it"
            val fileLines = FileTools.getFileLines(filePath)

            val unitHeaders = UnitTools.findUnitHeaders(
                filePath,
                JavaCommentRemover(),
                JavaMethodHeaderIdentifier(),
                JavaClassHeaderIdentifier()
            )

            // TODO: exclude anonymous interfaces
            unitHeaders.map { header ->
                val headerLine = fileLines.indexOfFirst { line -> line == header } + 1

                try {
                    val matchedBraces = UnitTools.findMatchingCurlyBrace(filePath, headerLine) ?: Pair(0, 0)
                    CodeUnit(header.trim(), filePath, IntRange(headerLine, matchedBraces.second))
                } catch (e: StartingBraceNotFoundException) {
                    CodeUnit.EMPTY
                }
            }
        }.filter { !it.isEmpty() }
    }

}