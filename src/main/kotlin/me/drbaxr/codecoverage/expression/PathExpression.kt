package me.drbaxr.codecoverage.expression

class PathExpression(val path: String) : Matcher {

    private val orderedExpressions: List<Matcher> = path.split("/").map { Expression(it) }

    override fun matches(syntax: String, matchCase: Boolean): Boolean {
        if (syntax == "**")
            return true

        var splitSyntax = syntax.split("/")

        if (splitSyntax.size == orderedExpressions.size) {
            val syntaxExpressionMap = splitSyntax.zip(orderedExpressions)

            if (doAllMapPairsMatch(syntaxExpressionMap, matchCase))
                return true
        } else if (splitSyntax[0] == "**" && splitSyntax[splitSyntax.lastIndex] == "**") {
            splitSyntax = splitSyntax.subList(1, splitSyntax.lastIndex)

            if (splitSyntax.size <= orderedExpressions.size)
                return doesPathExpressionContainMatchWithin(splitSyntax, matchCase)
        } else if (splitSyntax[0] == "**") {
            splitSyntax = splitSyntax.subList(1, splitSyntax.size)

            if (splitSyntax.size <= orderedExpressions.size) {
                val lastExpressions = orderedExpressions.subList(
                    orderedExpressions.lastIndex - splitSyntax.size + 1,
                    orderedExpressions.size
                )
                val syntaxLastExpressionMap = splitSyntax.zip(lastExpressions)

                return doAllMapPairsMatch(syntaxLastExpressionMap, matchCase)
            }
        } else if (splitSyntax[splitSyntax.lastIndex] == "**") {
            splitSyntax = splitSyntax.subList(0, splitSyntax.lastIndex)

            if (splitSyntax.size <= orderedExpressions.size) {
                val firstExpressions = orderedExpressions.subList(
                    0,
                    splitSyntax.size
                )
                val syntaxFirstExpressionMap = splitSyntax.zip(firstExpressions)

                return doAllMapPairsMatch(syntaxFirstExpressionMap, matchCase)
            }
        }

        return false
    }

    private fun doesPathExpressionContainMatchWithin(
        splitSyntax: List<String>,
        matchCase: Boolean
    ): Boolean {
        var existsMatch = false

        orderedExpressions.forEach {
            if (it.matches(splitSyntax[0], matchCase)) {
                val index = orderedExpressions.indexOf(it)

                if (index + splitSyntax.size <= orderedExpressions.size) {
                    val orderedExpressionsSegment =
                        orderedExpressions.subList(index, index + splitSyntax.size)

                    val syntaxOrderedExpressionsSegmentMap = splitSyntax.zip(orderedExpressionsSegment)

                    existsMatch = existsMatch || doAllMapPairsMatch(
                        syntaxOrderedExpressionsSegmentMap,
                        matchCase
                    )
                }
            }
        }

        return existsMatch
    }

    private fun doAllMapPairsMatch(
        syntaxExpressionMap: List<Pair<String, Matcher>>,
        matchCase: Boolean
    ) = syntaxExpressionMap.fold(true) { acc, pair ->
        acc && pair.second.matches(
            pair.first,
            matchCase
        )
    }
}