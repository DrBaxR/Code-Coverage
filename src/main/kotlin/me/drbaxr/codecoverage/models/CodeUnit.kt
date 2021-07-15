package me.drbaxr.codecoverage.models

class CodeUnit(
    val identifier: String,       // some would say 'unique identifier' ie. full classpath
    val hostFilePath: String,
    val linesRange: IntRange,   // should be from where head is located to where body ends
) {
    companion object {
        val EMPTY = CodeUnit("", "", -1..-1)
    }

    fun isEmpty() = this == EMPTY
}