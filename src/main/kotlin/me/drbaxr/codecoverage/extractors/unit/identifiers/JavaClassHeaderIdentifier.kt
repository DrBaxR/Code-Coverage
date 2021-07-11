package me.drbaxr.codecoverage.extractors.unit.identifiers

class JavaClassHeaderIdentifier : HeaderIdentifier {
    override fun isHeader(expression: String): Boolean {
        val trimmedExpression = expression.trim()
        val splitExpression = trimmedExpression.split(" ")

        return splitExpression.contains("class")
    }
}