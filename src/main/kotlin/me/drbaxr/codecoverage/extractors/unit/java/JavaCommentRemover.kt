package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.unit.CommentRemover

class JavaCommentRemover : CommentRemover {
    // pair of initial line number  in file (not index of line in array) and the content of said line
    override fun removeCommentLines(lines: List<String>): List<Pair<Int, String>> {
        var notCommentLines = removeSingleLineComments(lines.mapIndexed { index, line -> Pair(index, line) })
        notCommentLines = removeMultiLineComments(notCommentLines)

        return notCommentLines.map { Pair(it.first + 1, it.second) }
    }

    private fun removeSingleLineComments(lines: List<Pair<Int, String>>): List<Pair<Int, String>> {
        val notCommentLines = mutableListOf<Pair<Int, String>>()
        lines.forEach {
            val trimmedLine = it.second.trim()
            if (!trimmedLine.startsWith("//"))
                notCommentLines.add(it)
        }

        return notCommentLines
    }

    private fun removeMultiLineComments(lines: List<Pair<Int, String>>): List<Pair<Int, String>> {
        val notCommentLines = mutableListOf<Pair<Int, String>>()
        var isInComment = false

        for (line in lines) {
            if (line.second.contains("/*"))
                isInComment = true
            if (line.second.contains("*/"))
                isInComment = false
            else if (!isInComment)
                notCommentLines.add(line)
        }

        return notCommentLines
    }
}