package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor


fun main(args: Array<String>) {
    val testFileExtractor = ManualTestFileExtractor(".")

    testFileExtractor.findTestFiles().forEach { println(it) }
}