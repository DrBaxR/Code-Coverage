package me.drbaxr.codecoverage.expression

interface Matcher {

    fun matches(syntax: String, matchCase: Boolean = true): Boolean

}