package me.drbaxr.codecoverage.util.exceptions

import java.lang.RuntimeException

class StartingBraceNotFoundException(lineNumber: Int, filePath: String) : RuntimeException("No starting brace found after line $lineNumber in file \"$filePath\"") {
}