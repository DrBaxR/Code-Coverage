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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodeUnit

        if (identifier != other.identifier) return false
        if (hostFilePath != other.hostFilePath) return false
        if (linesRange != other.linesRange) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + hostFilePath.hashCode()
        result = 31 * result + linesRange.hashCode()
        return result
    }

    override fun toString(): String {
        return "CodeUnit(identifier='$identifier', hostFilePath='$hostFilePath', linesRange=$linesRange)"
    }


}