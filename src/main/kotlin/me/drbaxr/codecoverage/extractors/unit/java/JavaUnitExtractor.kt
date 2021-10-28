package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.UnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools
import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException

@Deprecated("Use JavaUnitExtractorAST instead")
class JavaUnitExtractor(private val projectPath: String, private val testFileExtractor: TestFileExtractor) :
    UnitExtractor(projectPath, testFileExtractor) {

    private val classUnitNames: MutableList<String> = mutableListOf()

    override fun findUnits(): List<CodeUnit> {
        val testFiles = testFileExtractor.findTestFiles()
        val sourceFiles = FileTools.getFilesWithExtension(projectPath, ".java", testFiles)
        val units = getUnitsFromSourceFiles(sourceFiles)

        return getUnitsWithCompleteIdentifiers(units)
    }

    private fun getUnitsWithCompleteIdentifiers(incompleteUnits: List<CodeUnit>): List<CodeUnit> {
        val completeUnits = mutableListOf<CodeUnit>()

        incompleteUnits.forEach { unit ->
            val splitIdentifier = unit.identifier.split('.')

            if (classUnitNames.contains(splitIdentifier[splitIdentifier.lastIndex])) {
                completeUnits.add(unit)
            } else {
                val parentClass = findUnitParentClass(incompleteUnits, unit)
                val completeUnit = getCompleteUnit(parentClass, unit)

                completeUnits.add(completeUnit)
            }
        }

        return completeUnits
    }

    private fun findUnitParentClass(incompleteUnits: List<CodeUnit>, unit: CodeUnit): CodeUnit = incompleteUnits.find {
        it.hostFilePath == unit.hostFilePath && it.linesRange.first <= unit.linesRange.first && it.linesRange.last >= unit.linesRange.last
    } ?: CodeUnit.EMPTY

    private fun getCompleteUnit(parentClass: CodeUnit, unit: CodeUnit): CodeUnit {
        val splitUnitIdentifier = unit.identifier.split('.')
        val splitParentClassIdentifier = parentClass.identifier.split('.')

        val unitPackageName = splitUnitIdentifier
            .subList(0, splitUnitIdentifier.lastIndex)
            .fold("") { acc, s -> if (acc != "") "$acc.$s" else s }
        val className = splitParentClassIdentifier[splitParentClassIdentifier.lastIndex]
        val unitName = splitUnitIdentifier[splitUnitIdentifier.lastIndex]

        return CodeUnit(
            "$unitPackageName.$className.$unitName",
            unit.hostFilePath,
            unit.linesRange,
            unit.unitType
        )
    }

    // also adds the names of classes to the classUnitNames list
    private fun getUnitsFromSourceFiles(sourceFiles: List<String>) = sourceFiles.flatMap {
        val filePath = "$projectPath/$it"
        val fileLines = FileTools.getFileLines(filePath)
        val packageName = UnitTools.getJavaPackageName(fileLines)

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
                    IntRange(headerLine, matchedBraces.second),
                    CodeUnit.UnitTypes.CODE,
                )
            } catch (e: StartingBraceNotFoundException) {
                CodeUnit.EMPTY
            }
        }
    }.filter { !it.isEmpty() }

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

        val className = unitHeader.substring(nameStartIndex..nameEndIndex)
        classUnitNames.add(className)

        return className
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