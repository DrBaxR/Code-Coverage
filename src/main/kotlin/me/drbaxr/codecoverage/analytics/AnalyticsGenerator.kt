package me.drbaxr.codecoverage.analytics

import me.drbaxr.codecoverage.models.CodeUnit

class AnalyticsGenerator {

    fun generate(allUnits: List<CodeUnit>, testedUnits: List<CodeUnit>) {
        // TODO: check https://j2html.com/examples.html and make html

        println("All Units Found:")
        allUnits.forEach { println(it.identifier) }

        println("\nTested Units Found:")
        testedUnits.forEach { println(it.identifier) }

        println("\nCode Coverage For Units: ${testedUnits.size.toFloat() / allUnits.size.toFloat() * 100}%")

        // TODO: also add line coverage
    }

}