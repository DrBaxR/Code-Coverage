package me.drbaxr.codecoverage

import com.google.gson.Gson
import me.drbaxr.codecoverage.analytics.AnalyticsGenerator
import me.drbaxr.codecoverage.extractors.testedunit.TestedUnitExtractor
import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.testfile.TestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.UnitExtractor
import me.drbaxr.codecoverage.models.CodeUnit
import me.drbaxr.codecoverage.models.Configuration
import me.drbaxr.codecoverage.util.FileTools
import org.slf4j.LoggerFactory
import java.lang.Exception

fun main() {
    val config = Gson().fromJson(
        FileTools.getFileLines("./config/config.json").fold("") { acc, s -> "$acc$s" },
        Configuration::class.java
    )

    run(config)
}

fun run(config: Configuration) {
    val logger = LoggerFactory.getLogger("main")

    val testFileExtractor: TestFileExtractor = ManualTestFileExtractor(
        config.projectPath,
        config.testFilesPath ?: "config/test-files.txt",
        config.ignoredTestFilesPath ?: "config/ignored-test-files.txt"
    )

    val possibleUnitExtractor: Any
    try {
        val unitExtractorClass = Class.forName(config.unitExtractor)
        val unitExtractorConstructor = unitExtractorClass.getConstructor(String::class.java, TestFileExtractor::class.java)
        possibleUnitExtractor = unitExtractorConstructor.newInstance(config.projectPath, testFileExtractor)
    } catch (e: Exception) {
        logger.error("Specified unit extractor is invalid")
        return
    }

    if (possibleUnitExtractor is UnitExtractor) {
        val units = possibleUnitExtractor.findUnits()

        val testFiles = testFileExtractor.findTestFiles().map { "${config.projectPath}/$it" }
        val testedUnits = mutableSetOf<CodeUnit>()

        val possibleTestedUnitExtractor: Any
        try {
            val testedUnitExtractorClass = Class.forName(config.testedUnitExtractor)
            val testedUnitExtractorConstructor = testedUnitExtractorClass.getConstructor(List::class.java)
            possibleTestedUnitExtractor = testedUnitExtractorConstructor.newInstance(units)
        } catch (e: Exception) {
            logger.error("Specified tested unit extractor is invalid")
            return
        }

        if (possibleTestedUnitExtractor is TestedUnitExtractor) {
            testFiles.forEach {
                val testedFromFile = possibleTestedUnitExtractor.findTestedUnits(it)
                testedFromFile.forEach { unit -> testedUnits.add(unit) }
            }
        } else {
            logger.error("Specified tested unit extractor is not a tested unit extractor")
        }

        val ag = AnalyticsGenerator()
        ag.generate(config.projectPath, units, testedUnits.toList())
    } else {
        logger.error("Specified unit extractor is not a unit extractor")
        return
    }
}