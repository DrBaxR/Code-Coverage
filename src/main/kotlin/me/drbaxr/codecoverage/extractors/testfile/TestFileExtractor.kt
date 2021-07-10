package me.drbaxr.codecoverage.extractors.testfile

abstract class TestFileExtractor(protected val projectPath: String) {

    abstract fun findTestFiles(): List<String>

}