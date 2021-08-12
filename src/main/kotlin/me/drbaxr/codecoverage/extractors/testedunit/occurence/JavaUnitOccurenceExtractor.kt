package me.drbaxr.codecoverage.extractors.testedunit.occurence

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools

class JavaUnitOccurrenceExtractor(allCodeUnits: List<CodeUnit>, allTestUnits: List<CodeUnit>) :
    UnitOccurrenceExtractor(allCodeUnits, allTestUnits) {

    override fun findOccurrences(testFile: String): List<CodeUnit> {
        val importStatements = FileTools.getFileLines(testFile).filter { it.trim().startsWith("import ") }
        val importedThings = importStatements.map {
            val splitStatement = it.split(" ")
            splitStatement[splitStatement.lastIndex]
        }.map { it.substring(0 until it.indexOf(';')) }

        // for each imported thing check if there are units that have similar identifiers
        // if they correspond 100%, they are tested. if not, check for occurrences of things
        // that are not included there
        // todo check occurrences in the bodies of test units

//        importedThings.forEach { println(it) }


        return listOf()
    }

}