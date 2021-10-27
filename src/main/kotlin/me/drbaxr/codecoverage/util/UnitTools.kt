package me.drbaxr.codecoverage.util

import me.drbaxr.codecoverage.extractors.unit.CommentRemover
import me.drbaxr.codecoverage.extractors.unit.HeaderIdentifier
import me.drbaxr.codecoverage.extractors.unit.java.JavaCommentRemover
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException

class UnitTools {
    companion object {
        // startLine is the number from which to start searching for the open '{'
        // first number in pair is line where '{' is found and second is where '}' is
        fun findMatchingCurlyBrace(filePath: String, startLine: Int): Pair<Int, Int>? {
            val fileLines = FileTools.getFileLines(filePath)

            if (fileLines.isEmpty())
                return null


            val firstBraceLineIndex =
                fileLines.mapIndexed { index, line -> Pair(index, line) }.indexOfFirst { it.second.contains("{") && it.first >= startLine - 1 }

            if (firstBraceLineIndex < 0)
                throw StartingBraceNotFoundException(startLine, filePath)

            var openBraces = 0
            for (currentLineIndex in firstBraceLineIndex..fileLines.lastIndex) {
                for (j in 0..fileLines[currentLineIndex].lastIndex)
                    openBraces = calculateNextValueForOpenBraces(openBraces, fileLines[currentLineIndex][j])

                if (openBraces <= 0)
                    return Pair(firstBraceLineIndex + 1, currentLineIndex + 1)
            }

            return null
        }

        fun findUnitHeaders(
            filePath: String,
            commentRemover: CommentRemover,
            vararg identifiers: HeaderIdentifier
        ): List<String> {
            val fileLines = FileTools.getFileLines(filePath)
            val linesThatAreNotComments = commentRemover.removeCommentLines(fileLines)

            return linesThatAreNotComments.map { it.second }.filter {
                identifiers.fold(false) { acc, identifier ->
                    acc || identifier.isHeader(it)
                }
            }
        }

        private fun calculateNextValueForOpenBraces(currentValue: Int, currentCharacter: Char): Int {
            return when (currentCharacter) {
                '{' -> currentValue + 1
                '}' -> currentValue - 1
                else -> currentValue
            }
        }

        // todo refactor this
        fun isIdentifierPrefix(identifier: String, unitIdentifier: String): Boolean {
            val splitUnitIdentifier = unitIdentifier.split('.')
            val splitIdentifier = identifier.split('.')
            var ok = true

            if (splitIdentifier.size > splitUnitIdentifier.size)
                ok = false

            if (ok)
                for (i in 0..splitIdentifier.lastIndex)
                    if (splitUnitIdentifier[i] != splitIdentifier[i])
                        ok = false

            return ok
        }

        fun getJavaPackageName(fileLines: List<String>): String {
            val commentRemover = JavaCommentRemover()
            val nonCommentLines = commentRemover.removeCommentLines(fileLines)

            var i = 0
            while (nonCommentLines[i].second.trim() == "")
                i++

            val maybePackageLine = fileLines[nonCommentLines[i].first - 1].trim().replace(";", "")
            return when (maybePackageLine.contains("package ")) {
                true -> maybePackageLine.removePrefix("package ").trim()
                false -> "" // TODO: rethink this because it makes no sense
            }
        }

        fun getFilesToUnitsMap(units: List<CodeUnit>): Map<String, Set<CodeUnit>> {
            // problem here?
            val filesToUnitsMap = mutableMapOf<String, MutableSet<CodeUnit>>()

            units.forEach {
                val unitsSet = filesToUnitsMap[it.hostFilePath]

                if (unitsSet != null) {
                    unitsSet.add(it)
                } else {
                    filesToUnitsMap[it.hostFilePath] = mutableSetOf(it)
                }
            }

            return filesToUnitsMap
        }
    }
}