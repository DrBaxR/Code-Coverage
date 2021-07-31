package me.drbaxr.codecoverage.models

class CodeUnit(
    val identifier: String,     // some would say 'unique identifier' ie. package.methodName
    val hostFilePath: String,   // path is relative to directory where jar is
    val linesRange: IntRange,   // should be from where head is located to where body ends
    val unitType: UnitTypes?,
) {
    companion object {
        val EMPTY = CodeUnit("", "", -1..-1, null)
    }

    fun isEmpty() = this == EMPTY
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodeUnit

        if (identifier != other.identifier) return false
        if (hostFilePath != other.hostFilePath) return false
        if (linesRange != other.linesRange) return false
        if (unitType != other.unitType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + hostFilePath.hashCode()
        result = 31 * result + linesRange.hashCode()
        result = 31 * result + (unitType?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "CodeUnit(identifier='$identifier', hostFilePath='$hostFilePath', linesRange=$linesRange, UnitType=$unitType)"
    }


    enum class UnitTypes {
        CODE,
        TEST,
    }
}