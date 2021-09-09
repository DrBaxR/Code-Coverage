# Code Coverage
Tool that generates code coverage for a project without actually running tests for that project.

## How to Use
- Get the JAR
- Create files ```config/test-files.txt``` and ```config/ignored-test-files.txt``` in the folder where you have put the JAR
- Specify test file and ignored test file patterns in those files (docs on that later)
- Specify path to project
- Run JAR
- You'll find a ```analytics/index.html``` file where you put the jar. Just open it with any browser and BOOM!, there you'll be able to find all the information this tool has to offer  

**DISCLAIMER:** Also contains various random repos I found on GitHub (they can be found in the test resources, and are exclusively used for test cases). I don't own any of those.

**NOTE:** Not marking a file as a test file will make it count as a source file, therefore its units are going to be counted as not being tested