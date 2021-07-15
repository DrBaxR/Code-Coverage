package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaCommentRemover
import me.drbaxr.codecoverage.extractors.unit.java.JavaClassHeaderIdentifier
import me.drbaxr.codecoverage.extractors.unit.java.JavaMethodHeaderIdentifier
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor
import me.drbaxr.codecoverage.util.UnitTools

fun main(args: Array<String>) {
//    val test = JavaUnitExtractor(
//        "src/test/resources/unit-extractor/java/case2",
//        ManualTestFileExtractor("src/test/resources/unit-extractor/java/case1")
//    )

    val test = JavaUnitExtractor(
        "src/test/resources/unit-extractor/java/case2",
        ManualTestFileExtractor(
            "src/test/resources/unit-extractor/java/case2",
            "src/test/resources/unit-extractor/java/case2/config/test-files.txt",
            "src/test/resources/unit-extractor/java/case2/config/ignored-test-files.txt"
        )
    )

    val units = test.findUnits()
    units.forEach {
        println(it.identifier)
        println(it.hostFilePath)
        println(it.linesRange)
    }
}