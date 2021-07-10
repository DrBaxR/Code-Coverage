package me.drbaxr.codecoverage.extractors.testfile

abstract class TestFileExtractor(val projectPath: String) {

    abstract fun findTestFiles(): List<String>

}