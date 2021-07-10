package me.drbaxr.codecoverage.extractors.testfile

import me.drbaxr.codecoverage.expression.PathExpression
import me.drbaxr.codecoverage.util.FileTools

class ManualTestFileExtractor(
    projectPath: String,
    testFilesConfigPath: String = "config/test-files.txt",
    ignoredTestFilesPath: String = "config/ignored-test-files.txt",
) : TestFileExtractor(projectPath) {

    private val projectFilePaths = FileTools.getDirectoryFilePaths(projectPath).map { PathExpression(it) }
    private val testFilesSyntaxList = FileTools.getFileLines(testFilesConfigPath)
    private val ignoredTestFilesSyntaxList = FileTools.getFileLines(ignoredTestFilesPath)

    private fun getMatchingFiles(): List<PathExpression> {
        return projectFilePaths.filter { existsMatchingFileSyntax(it) }
    }

    private fun existsMatchingFileSyntax(expression: PathExpression): Boolean {
        return testFilesSyntaxList.fold(false) { acc, syntax -> acc || expression.matches(syntax) }
    }

    private fun existsMatchingIgnoredSyntax(expression: PathExpression): Boolean {
        return ignoredTestFilesSyntaxList.fold(false) { acc, syntax -> acc || expression.matches(syntax) }
    }

    override fun findTestFiles(): List<String> {
        return getMatchingFiles().filter { !existsMatchingIgnoredSyntax(it) }.map { it.path }
    }
}