package me.drbaxr.codecoverage.expression

class Expression(private val content: String) : Matcher {

    override fun matches(syntax: String, matchCase: Boolean): Boolean {
        if (syntax == "**") return true

        if (syntax.startsWith("*") && syntax.endsWith("*"))
            return contentContainsSyntaxTerm(syntax, matchCase)

        if (syntax.startsWith("*"))
            return contentEndsWithSyntaxTerm(syntax, matchCase)

        if (syntax.endsWith("*"))
            return contentStartsWithSyntaxTerm(syntax, matchCase)

        return when (matchCase) {
            true -> content == syntax
            false -> content.equals(syntax, true)
        }
    }

    private fun contentContainsSyntaxTerm(syntax: String, matchCase: Boolean): Boolean {
        val term = syntax.subSequence(1, syntax.length - 2).toString()

        if (matchCase)
            return content.contains(term)

        return content.toLowerCase().contains(term.toLowerCase())
    }

    private fun contentEndsWithSyntaxTerm(syntax: String, matchCase: Boolean): Boolean {
        val term = syntax.subSequence(1, syntax.length).toString()

        if (matchCase)
            return content.endsWith(term)

        return content.toLowerCase().endsWith(term.toLowerCase())
    }

    private fun contentStartsWithSyntaxTerm(syntax: String, matchCase: Boolean): Boolean {
        val term = syntax.subSequence(0, syntax.length - 2).toString()

        if (matchCase)
            return content.startsWith(term)

        return content.toLowerCase().startsWith(term.toLowerCase())
    }

}