package me.drbaxr.codecoverage.extractors.testedunit.occurence

import me.drbaxr.codecoverage.extractors.unit.java.JavaCommentRemover
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools

class JavaUnitOccurrenceExtractor(allCodeUnits: List<CodeUnit>, allTestUnits: List<CodeUnit>) :
    UnitOccurrenceExtractor(allCodeUnits, allTestUnits) {

    private val javaDelimiters = arrayOf(
        ' ', '.', ',', ';',
        '{', '}', '[', ']', '(', ')',
        '"', '\'',
        '\n', '\t',
    )

    // todo rename this
    override fun findOccurrences(testFile: String): List<CodeUnit> {
        val fileLines = FileTools.getFileLines(testFile)
        val nonCommentLines = JavaCommentRemover().removeCommentLines(fileLines)
            .map { it.second } // todo add in documentation that it depends on this
        val importStatements = fileLines.filter { it.trim().startsWith("import ") }
        val importedThings = importStatements.map {
            val splitStatement = it.split(" ")
            splitStatement[splitStatement.lastIndex]
        }.map { it.substring(0 until it.indexOf(';')) }

        val packagesToLookFor = listOf(
            listOf(JavaUnitExtractor.getPackageName(fileLines)),
            importedThings
        ).flatten()

        val possiblyTestedUnits = allCodeUnits.filter { unit ->
            packagesToLookFor.any { UnitTools.isIdentifierPrefix(it, unit.identifier) }
        }

        // todo this is where you'll probably find the problem
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