package me.drbaxr.codecoverage.models

data class CodeUnit(
    val identifier: String,       // some would say 'unique identifier' ie. full classpath
    val hostFilePath: String,
    val linesRange: IntRange,   // should be from where head is located to where body ends
)