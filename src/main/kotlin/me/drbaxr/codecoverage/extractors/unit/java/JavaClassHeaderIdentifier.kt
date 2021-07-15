package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.unit.HeaderIdentifier

class JavaClassHeaderIdentifier : HeaderIdentifier {
    override fun isHeader(expression: String): Boolean {
        val trimmedExpression = expression.trim()
        val splitExpression = trimmedExpression.split(" ")

        return splitExpression.contains("class")
    }
}