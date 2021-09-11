package me.drbaxr.codecoverage.models

data class Configuration(
    var projectPath: String,
    var unitExtractor: String,
    var testedUnitExtractor: String,
    var testFilesPath: String?,
    var ignoredTestFilesPath: String?,
)