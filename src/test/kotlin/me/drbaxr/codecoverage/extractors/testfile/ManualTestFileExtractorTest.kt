package me.drbaxr.codecoverage.extractors.testfile

import kotlin.test.Test
import kotlin.test.assertEquals

class ManualTestFileExtractorTest {

    @Test
    fun `test finding test files for manually specified values in config1`() {
        val expected = listOf(
            "src/test/java/SampleTest.java",
            "src/test/java/AnotherTest.java",
            "src/test/java/package/PackageTest.java",
            "src/test/java/package/sub/TestFile.java",
            "src/test/java/package/sub/FileTest.java",
            "src/test/java/package/sub/mid/MidTestName.java",
        )
        val testFileExtractor = createManualTestFileExtractorWithConfig(1)

        assertEquals(expected.toSet(), testFileExtractor.findTestFiles().toSet())
    }

    @Test
    fun `test finding test files for manually specified values in config2`() {
        val expected = listOf(
            "src/test/java/AnotherTest.java",
            "src/test/java/package/PackageTest.java",
            "src/test/java/package/sub/TestFile.java",
            "src/test/java/package/sub/FileTest.java",
            "src/test/java/package/sub/mid/MidTestName.java",
            "src/main/java/Main.java"
        )
        val testFileExtractor = createManualTestFileExtractorWithConfig(2)

        assertEquals(expected.toSet(), testFileExtractor.findTestFiles().toSet())
    }

    @Test
    fun `test finding test files for manually specified values in config3`() {
        val expected = listOf(
            "src/test/java/SampleTest.java",
            "src/test/java/AnotherTest.java",
        )
        val testFileExtractor = createManualTestFileExtractorWithConfig(3)

        assertEquals(expected.toSet(), testFileExtractor.findTestFiles().toSet())
    }

    private fun createManualTestFileExtractorWithConfig(configNumber: Int): ManualTestFileExtractor {
        return ManualTestFileExtractor(
            "src/test/resources/test-file-extractor/maven-like",
            "src/test/resources/test-file-extractor/config$configNumber/test-files.txt",
            "src/test/resources/test-file-extractor/config$configNumber/ignored-test-files.txt",
        )
    }
}