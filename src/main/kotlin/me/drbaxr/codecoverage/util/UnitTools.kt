package me.drbaxr.codecoverage.util

import me.drbaxr.codecoverage.extractors.unit.comment.CommentRemover
import me.drbaxr.codecoverage.extractors.unit.identifiers.HeaderIdentifier
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
                fileLines.indexOfFirst { it.contains('{') && fileLines.indexOf(it) >= startLine - 1 }

            if (firstBraceLineIndex < 0)
                throw StartingBraceNotFoundException(startLine)

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

            return linesThatAreNotComments.filter {
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
    }
}