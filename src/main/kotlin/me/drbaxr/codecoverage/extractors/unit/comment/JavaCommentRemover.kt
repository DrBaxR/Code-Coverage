package me.drbaxr.codecoverage.extractors.unit.comment

class JavaCommentRemover : CommentRemover {
    override fun removeCommentLines(lines: List<String>): List<String> {
        var notCommentLines = removeSingleLineComments(lines)
        notCommentLines = removeMultiLineComments(notCommentLines)

        return notCommentLines
    }

    private fun removeSingleLineComments(lines: List<String>): List<String> {
        val notCommentLines = mutableListOf<String>()
        lines.forEach {
            val trimmedLine = it.trim()
            if (!trimmedLine.startsWith("//"))
                notCommentLines.add(it)
        }

        return notCommentLines
    }

    private fun removeMultiLineComments(lines: List<String>): List<String> {
        val notCommentLines = mutableListOf<String>()
        var isInComment = false

        for (line in lines) {
            if (line.contains("/*"))
                isInComment = true
            if (line.contains("*/"))
                isInComment = false
            else if (!isInComment)
                notCommentLines.add(line)
        }

        return notCommentLines
    }
}