package me.drbaxr.codecoverage.expression

import java.lang.RuntimeException

class InvalidSyntaxException(syntax: String) : RuntimeException("$syntax is not a valid syntax")