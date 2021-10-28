package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.UnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.parsers.JavaASTMethodParser
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools
import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException
import org.eclipse.jdt.core.dom.TypeDeclaration

class JavaUnitExtractorAST(private val projectPath: String, private val testFileExtractor: TestFileExtractor) :
    UnitExtractor(projectPath, testFileExtractor) {
    override fun findUnits(): List<CodeUnit> {
        val testFiles = testFileExtractor.findTestFiles()
        val sourceFiles = FileTools.getFilesWithExtension(projectPath, ".java", testFiles)

        val units: List<List<CodeUnit>> = sourceFiles.map { filePath ->
            val fileLines = FileTools.getFileLines("${projectPath}/${filePath}")
            val fileSource = fileLines.fold("") { acc, s -> "${acc}${s}\n" }
            val methodDeclarations = JavaASTMethodParser.parse(fileSource)
            val packageName = UnitTools.getJavaPackageName(fileLines)

            methodDeclarations.map {
                val method = it.first
                val startLine = it.second

                val braces: Pair<Int, Int>? = try {
                    UnitTools.findMatchingCurlyBrace("${projectPath}/${filePath}", startLine)
                } catch (e: StartingBraceNotFoundException) {
                    // this shouldn't happen if method has a body !
                    Pair(startLine - 1, startLine - 1)
                }

                var className = ""
                val potentialClass = method.parent
                if (potentialClass is TypeDeclaration) {
                    className = potentialClass.name.toString()
                }

                CodeUnit(
                    "${packageName}.${className}.${method.name}",
                    "${projectPath}/${filePath}",
                    startLine..braces!!.second,
                    CodeUnit.UnitTypes.CODE
                )
            }
        }

        return units.flatten()
    }
}