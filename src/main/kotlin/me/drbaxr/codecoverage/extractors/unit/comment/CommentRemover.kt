package me.drbaxr.codecoverage.extractors.unit.comment

interface CommentRemover {

    fun removeCommentLines(lines: List<String>): List<String>

}