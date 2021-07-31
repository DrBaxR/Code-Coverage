package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testedunit.occurence.JavaUnitOccurrenceExtractor
import me.drbaxr.codecoverage.extractors.testedunit.unit.java.JUnitTestUnitExtractor
import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit

fun main() {
    unitSample()
//    testUnitSample()

//    val occ = JavaUnitOccurrenceExtractor()
//    occ.findOccurrences(CodeUnit("asd", "src/test/resources/test-unit-extractor/junit/Found.java", 23..27, CodeUnit.UnitTypes.TEST))
}

fun unitSample() {
//    val test = JavaUnitExtractor(
//        "src/test/resources/unit-extractor/java/case2",
//        ManualTestFileExtractor("src/test/resources/unit-extractor/java/case1")
//    )

//    val test = JavaUnitExtractor(
//        "src/test/resources/unit-extractor/java/case2",
//        ManualTestFileExtractor(
//            "src/test/resources/unit-extractor/java/case2",
//            "src/test/resources/unit-extractor/java/case2/config/test-files.txt",
//            "src/test/resources/unit-extractor/java/case2/config/ignored-test-files.txt"
//        )
//    )

    val test = JavaUnitExtractor(
        "src/test/resources/sample-projects/Game-master",
        ManualTestFileExtractor("src/test/resources/sample-projects/Game-master")
    )

    val units = test.findUnits()
    units.forEach {
        println(it.identifier)
    }
}

fun testUnitSample() {
    val extractor = JUnitTestUnitExtractor()
    extractor.findTestUnits("src/test/resources/test-unit-extractor/junit/Found.java").forEach { println(it) }
}