package me.drbaxr.codecoverage.util

import kotlin.test.Test
import kotlin.test.assertEquals

class FileToolsTest {

    @Test
    fun `test get files from directory`() {
        var expected = listOf("file1.txt", "file2.txt", "dir1/file3.txt", "dir1/dir2/file4.txt")
        assertEquals(expected.toSet(), FileTools.getFilePaths("src/test/resources/file-tools/case1").toSet())

        expected = listOf("src/test.resources/case2/file1", "src/test.resources/case2/file2.txt", "src/test.resources/case2/file3.c", "src/test.resources/case2/file4.lol")
        assertEquals(expected.toSet(), FileTools.getFilePaths("src/test/resources/file-tools/case2").toSet())
    }
}