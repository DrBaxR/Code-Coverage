package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testedunit.JavaTestedUnitExtractor
import me.drbaxr.codecoverage.extractors.testunit.java.JUnitTestUnitExtractor
import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor

fun main() {
//    unitSample()
//    testUnitSample()
    testedUnitSample()
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

fun testedUnitSample() {
    val ue = JavaUnitExtractor(
        "src/test/resources/sample-projects/junit-tests-master",
        ManualTestFileExtractor("src/test/resources/sample-projects/junit-tests-master")
    )

    val testFile = "src/test/resources/sample-projects/junit-tests-master/src/test/java/de/syngenio/demo3/TestController.java"
    val occ = JavaTestedUnitExtractor(ue.findUnits())
    occ.findTestedUnits(testFile).forEach { println(it.identifier) }
}