package me.drbaxr.codecoverage.util.exceptions

import java.lang.RuntimeException

class StartingBraceNotFoundException(lineNumber: Int) : RuntimeException("No starting brace found after line $lineNumber") {
}