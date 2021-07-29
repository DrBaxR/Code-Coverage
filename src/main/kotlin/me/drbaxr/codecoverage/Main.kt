package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testedunit.unit.java.JUnitTestUnitExtractor
import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor

fun main() {
//    unitSample()
    testUnitSample()
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
        println(it)
    }
}

fun testUnitSample() {
    var extractor = JUnitTestUnitExtractor()
    extractor.findTestUnits("src/test/resources/test-unit-extractor/junit/Found.java").forEach { println(it) }
}