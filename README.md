# Code Coverage
Tool that generates code coverage for a project without actually running tests for that project.

## How to Use
- Get the JAR
- Create files ```config/config.json``` in the folder where you have put the JAR (more on this later)
- Specify test file and ignored test file patterns (docs on that later)
- Run JAR
- You'll find a ```analytics/index.html``` file where you put the jar. Just open it with any browser and BOOM!, there you'll be able to find all the information this tool has to offer  

## Sample ```config.json``` file
This is the file in which you can configure the tool:
```json
{
  "projectPath": "src/test/resources/sample-projects/junit-tests-master",
  "unitExtractor": "me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor",
  "testedUnitExtractor": "me.drbaxr.codecoverage.extractors.testedunit.JavaTestedUnitExtractor",
  "testFilesPath": "config/test-files.txt",
  "ignoredTestFilesPath": "config/ignored-test-files.txt"
}
```

**DISCLAIMER:** Also contains various random repos I found on GitHub (they can be found in the test resources, and are exclusively used for test cases). I don't own any of those.

**NOTE:** Not marking a file as a test file will make it count as a source file, therefore its units are going to be counted as not being tested