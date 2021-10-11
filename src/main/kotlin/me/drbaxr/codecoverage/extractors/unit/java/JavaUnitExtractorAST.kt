package me.drbaxr.codecoverage.extractors.unit.java

import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.UnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.util.FileTools
import me.drbaxr.codecoverage.util.UnitTools
import me.drbaxr.codecoverage.util.exceptions.StartingBraceNotFoundException
import org.eclipse.jdt.core.dom.ASTNode
import org.eclipse.jdt.core.dom.TypeDeclaration

class JavaUnitExtractorAST(private val projectPath: String, private val testFileExtractor: TestFileExtractor) :
    UnitExtractor(projectPath, testFileExtractor) {
    override fun findUnits(): List<CodeUnit> {
        val testFiles = testFileExtractor.findTestFiles()
        val sourceFiles = FileTools.getFilesWithExtension(projectPath, ".java", testFiles)

        val units: List<List<CodeUnit>> = sourceFiles.map { filePath ->
            val fileLines = FileTools.getFileLines("${projectPath}/${filePath}")
            val fileSource = fileLines.fold("") { acc, s -> "${acc}${s}\n" }
            val methodDeclarations = JavaASTParser.parse(fileSource)
            val packageName = UnitTools.getJavaPackageName(fileLines)

            // TODO: somehow find out where it starts and where it ends
            // get current class name
            // make identifier

            methodDeclarations.map {
                val method = it.first
                val startLine = it.second

                var braces: Pair<Int, Int>?
                try {
                    braces = UnitTools.findMatchingCurlyBrace("${projectPath}/${filePath}", startLine - 1)
                } catch (e: StartingBraceNotFoundException) {
                    braces = Pair(startLine, startLine)
                }

                var className = ""
                val potentialClass = method.parent
                if (potentialClass is TypeDeclaration) {
                    className = ".${potentialClass.name}"
                }

                CodeUnit(
                    "${packageName}${className}.${method.name}",
                    "${projectPath}/${filePath}",
                    startLine..braces!!.second,
                    CodeUnit.UnitTypes.CODE
                )
            }
        }

        return units.flatten()
    }
}