package me.drbaxr.codecoverage.util

import kotlin.test.Test
import kotlin.test.assertEquals

class FileToolsTest {

    @Test
    fun `test get files from directory`() {
        var expected = listOf("file1.txt", "file2.txt", "dir1/file3.txt", "dir1/dir2/file4.txt")
        assertEquals(expected.toSet(), FileTools.getDirectoryFilePaths("src/test/resources/file-tools/case1").toSet())

        expected = listOf(
            "src/test.resources/case2/file1",
            "src/test.resources/case2/file2.txt",
            "src/test.resources/case2/file3.c",
            "src/test.resources/case2/file4.lol"
        )
        assertEquals(expected.toSet(), FileTools.getDirectoryFilePaths("src/test/resources/file-tools/case2").toSet())
    }

    @Test
    fun `test get lines from file`() {
        var expected = listOf("line1", "line2", "li ne 123")
        assertEquals(expected, FileTools.getFileLines("src/test/resources/file-tools/case1/file1.txt"))

        expected = listOf("another test of", "", "reding line s from a file", "", "123asd")
        assertEquals(expected, FileTools.getFileLines("src/test/resources/file-tools/case1/file2.txt"))
    }

    @Test
    fun `will get files with specified extensions that are not found in second parameter`() {
        val expected = listOf(
            "src/main/java/package/SomeClass.java",
            "src/main/java/package/SomeInterface.java",
            "src/main/java/Main.java",
        )

        val excludedPaths = listOf(
            "src/test/java/package/sub/mid/MidTestName.java",
            "src/test/java/package/sub/FileTest.java",
            "src/test/java/package/sub/TestFile.java",
            "src/test/java/package/PackageTest.java",
            "src/test/java/AnotherTest.java",
            "src/test/java/SampleTest.java",
        )

        assertEquals(expected.toSet(), FileTools.getFilesWithExtension("src/test/resources/test-file-extractor/maven-like", ".java", excludedPaths).toSet())
    }

    @Test
    fun `no ignored files will give all files with extension`() {
        val expected = listOf(
            "src/main/java/package/SomeClass.java",
            "src/main/java/package/SomeInterface.java",
            "src/main/java/Main.java",
            "src/test/java/package/sub/mid/MidTestName.java",
            "src/test/java/package/sub/FileTest.java",
            "src/test/java/package/sub/TestFile.java",
            "src/test/java/package/PackageTest.java",
            "src/test/java/AnotherTest.java",
            "src/test/java/SampleTest.java",
        )

        assertEquals(expected.toSet(), FileTools.getFilesWithExtension("src/test/resources/test-file-extractor/maven-like", ".java").toSet())
    }

}