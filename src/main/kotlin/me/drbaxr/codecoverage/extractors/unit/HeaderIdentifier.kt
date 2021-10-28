package me.drbaxr.codecoverage.extractors.unit

@Deprecated("Will probably be removed since implementation of parsers will come from third party")
interface HeaderIdentifier {

    fun isHeader(expression: String): Boolean

}