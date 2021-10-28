package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.unit.HeaderIdentifier

@Deprecated("No longer needed")
class JavaMethodHeaderIdentifier : HeaderIdentifier {
    private val methodModifiers = listOf(
        "public",
        "private",
        "protected",
        "final",
        "static",
        "abstract",
        "transient",
        "synchronized",
        "volatile",
    )

    private val illegalExpressions = listOf(
        "new ".toRegex(),
        "for *\\(".toRegex(),
        "=|-|\\+|\\*|\\.".toRegex(),
        "if *\\(".toRegex(),
        "switch *\\(".toRegex(),
        "catch *\\(".toRegex(),
        "while *\\(".toRegex(),
    )

    override fun isHeader(expression: String): Boolean {
        if (containsIllegalExpression(expression))
            return false

        val expressionWithoutModifiersAndType = removeModifiersAndType(expression)
        return containsParenthesis(expressionWithoutModifiersAndType)
    }

    private fun removeModifiersAndType(expression: String): String {
        val trimmedExpression = expression.trim()
        val splitExpression = trimmedExpression.split(" ")
        val filteredSplitExpression = splitExpression.filter { !methodModifiers.contains(it) }

        return filteredSplitExpression.subList(1, filteredSplitExpression.size).fold("") { acc, word -> acc + word }
    }

    private fun containsParenthesis(expression: String): Boolean {
        val trimmedExpression = expression.trim()
        return trimmedExpression.contains('(')
    }

    private fun containsIllegalExpression(expression: String): Boolean {
        return illegalExpressions.fold(false) { acc, expr -> acc || expression.contains(expr) }
    }
}