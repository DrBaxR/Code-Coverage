package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.parsers.JavaASTMethodInvocationParser
import me.drbaxr.codecoverage.util.FileTools
import java.nio.file.Paths

class JavaTestedUnitExtractorAST(allCodeUnits: List<CodeUnit>) :
    TestedUnitExtractor(allCodeUnits) {

    override fun findTestedUnits(projectPath: String, testFile: String): List<CodeUnit> {
        val fileSource = FileTools.getFileLines(testFile).fold("") { acc, s -> "${acc}\n${s}" }

        // this makes it so only maven like project structure is supported for java
        val sourcepathEntries = arrayOf(
            Paths.get("${projectPath}/src/main").toAbsolutePath().toString(),
            Paths.get("${projectPath}/src/test").toAbsolutePath().toString(),
        )
        val methodInvocations = JavaASTMethodInvocationParser.parse(
            sourcepathEntries,
            Paths.get(testFile).toAbsolutePath().toString(),
            fileSource
        )

        val unitNamesSet = mutableSetOf<String>()

        methodInvocations.forEach {
            val expression = it.expression
            val typeBinding = expression?.resolveTypeBinding()

            if (typeBinding != null) {
                val bindingPackage = typeBinding.`package`.name
                val bindingName = typeBinding.name
                val methodName = it.name

                unitNamesSet.add("$bindingPackage.$bindingName.$methodName")
            }
        }

        return allCodeUnits.filter { unitNamesSet.contains(it.identifier) }
    }

}