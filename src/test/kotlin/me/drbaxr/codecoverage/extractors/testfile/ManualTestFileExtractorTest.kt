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
        val testFileExtractor = ManualTestFileExtractor(
            "src/test/resources/test-file-extractor/maven-like",
            "src/test/resources/test-file-extractor/config1/test-files.txt",
            "src/test/resources/test-file-extractor/config1/ignored-test-files.txt",
        )

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
        val testFileExtractor = ManualTestFileExtractor(
            "src/test/resources/test-file-extractor/maven-like",
            "src/test/resources/test-file-extractor/config2/test-files.txt",
            "src/test/resources/test-file-extractor/config2/ignored-test-files.txt",
        )

        assertEquals(expected.toSet(), testFileExtractor.findTestFiles().toSet())
    }

    @Test
    fun `test finding test files for manually specified values in config3`() {
        val expected = listOf(
            "src/test/java/SampleTest.java",
            "src/test/java/AnotherTest.java",
        )
        val testFileExtractor = ManualTestFileExtractor(
            "src/test/resources/test-file-extractor/maven-like",
            "src/test/resources/test-file-extractor/config3/test-files.txt",
            "src/test/resources/test-file-extractor/config3/ignored-test-files.txt",
        )

        assertEquals(expected.toSet(), testFileExtractor.findTestFiles().toSet())
    }
}