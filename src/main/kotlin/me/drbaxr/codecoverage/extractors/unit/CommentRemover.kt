package me.drbaxr.codecoverage.extractors.unit

interface CommentRemover {

    fun removeCommentLines(lines: List<String>): List<String>

}