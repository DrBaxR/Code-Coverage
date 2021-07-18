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
            val packageName = getPackageName(fileLines)

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
                    CodeUnit(
                        "$packageName.${getUnitName(getUnitName(header.trim()))}",
                        filePath,
                        IntRange(headerLine, matchedBraces.second)
                    )
                } catch (e: StartingBraceNotFoundException) {
                    CodeUnit.EMPTY
                }
            }
        }.filter { !it.isEmpty() }
    }

    private fun getPackageName(fileLines: List<String>): String {
        var i = 0;
        while (fileLines[i].trim() == "")
            i++

        val maybePackageLine = fileLines[i].trim().replace(";", "")
        return when (maybePackageLine.contains("package ")) {
            true -> maybePackageLine.removePrefix("package ").trim()
            false -> ""
        }
    }

    private fun getUnitName(unitHeader: String): String {
        return when {
            unitHeader.contains("(") -> findMethodName(unitHeader)
            unitHeader.contains("class ") -> findClassName(unitHeader)
            else -> unitHeader
        }
    }

    private fun findClassName(unitHeader: String): String {
        val nameStartIndex = unitHeader.indexOf("class ") + "class ".length
        val nameEndIndex = findClassNameEndIndex(nameStartIndex, unitHeader)

        return unitHeader.substring(nameStartIndex..nameEndIndex)
    }

    private fun findMethodName(unitHeader: String): String {
        val unitNameStartIndex = findMethodNameStartIndex(unitHeader)
        val unitNameEndIndex = findMethodNameEndIndex(unitNameStartIndex, unitHeader)

        return unitHeader.substring(unitNameStartIndex..unitNameEndIndex)
    }

    private fun findMethodNameEndIndex(unitNameStartIndex: Int, unitHeader: String): Int {
        var unitNameEndIndex = unitNameStartIndex

        for (i in unitNameStartIndex..unitHeader.length) {
            if (unitHeader[i] == ' ' || unitHeader[i] == '(') {
                unitNameEndIndex = i - 1
                break
            }
        }
        return unitNameEndIndex
    }

    private fun findMethodNameStartIndex(unitHeader: String): Int {
        var unitNameStartIndex = -1

        for (i in 0..unitHeader.lastIndex) {
            if (unitHeader[i] != '(')
                continue

            var initialSpaces = true
            for (j in i - 1 downTo 0) {
                if (unitHeader[j] != ' ') {
                    initialSpaces = false
                } else if (!initialSpaces) {
                    unitNameStartIndex = j
                    break
                }
            }

            break
        }

        unitNameStartIndex += 1
        return unitNameStartIndex
    }

    private fun findClassNameEndIndex(nameStartIndex: Int, unitHeader: String): Int {
        var nameEndIndex = -1

        for (i in nameStartIndex..unitHeader.lastIndex) {
            if (unitHeader[i] == ' ' || unitHeader[i] == '{') {
                nameEndIndex = i - 1
                break
            }
        }

        if (nameEndIndex == -1)
            nameEndIndex = unitHeader.lastIndex
        return nameEndIndex
    }

}