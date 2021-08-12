package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testedunit.occurence.JavaUnitOccurrenceExtractor
import me.drbaxr.codecoverage.extractors.testedunit.unit.java.JUnitTestUnitExtractor
import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit

fun main() {
//    unitSample()
//    testUnitSample()

    val ue = JavaUnitExtractor(
        "src/test/resources/sample-projects/junit-tests-master",
        ManualTestFileExtractor("src/test/resources/sample-projects/junit-tests-master")
    )

    ue.findUnits()
        .forEach { println(it.identifier) }

    val occ = JavaUnitOccurrenceExtractor(listOf(), listOf())
    occ.findOccurrences("src/test/resources/sample-projects/junit-tests-master/src/test/java/de/syngenio/demo/TestMyTestClass.java")
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

//    val test = JavaUnitExtractor(
//        "src/test/resources/sample-projects/junit-tests-master",
//        ManualTestFileExtractor("src/test/resources/sample-projects/junit-tests-master")
//    )

    val units = test.findUnits()
    units.forEach {
        println(it.identifier)
    }
}

fun testUnitSample() {
    val extractor = JUnitTestUnitExtractor()
    extractor.findTestUnits("src/test/resources/test-unit-extractor/junit/Found.java").forEach { println(it) }
}