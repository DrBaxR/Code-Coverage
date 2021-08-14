package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.extractors.unit.java.JavaCommentRemover
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools

// TODO: add tests for this
class JavaTestedUnitExtractor(allCodeUnits: List<CodeUnit>) :
    TestedUnitExtractor(allCodeUnits) {

    override fun findTestedUnits(testFile: String): List<CodeUnit> {
        val fileLines = FileTools.getFileLines(testFile)
        val nonCommentLines = JavaCommentRemover().removeCommentLines(fileLines)
            .map { it.second }
        val importStatements = fileLines.filter { it.trim().startsWith("import ") }
        val importedThings = importStatements.map {
            val splitStatement = it.split(" ")
            splitStatement[splitStatement.lastIndex]
        }.map { it.substring(0 until it.indexOf(';')) }

        val packagesToLookFor = listOf(
            listOf(UnitTools.getJavaPackageName(fileLines)),
            importedThings
        ).flatten()

        val possiblyTestedUnits = allCodeUnits.filter { unit ->
            packagesToLookFor.any { UnitTools.isIdentifierPrefix(it, unit.identifier) }
        }

        val testedUnits = mutableSetOf<CodeUnit>()
        possiblyTestedUnits.forEach { unit ->
            val splitIdentifier = unit.identifier.split('.')
            val unitName = splitIdentifier[splitIdentifier.lastIndex]

            if (nonCommentLines.any { line -> splitByJavaDelimiters(line).contains(unitName) }) {
                testedUnits.add(unit)
            }
        }

        return testedUnits.toList()
    }

    private fun splitByJavaDelimiters(line: String): List<String> {
        return line.split(
            ' ', '.', ',', ';',
            '{', '}', '[', ']', '(', ')',
            '"', '\'',
            '\n', '\t'
        )
    }

}