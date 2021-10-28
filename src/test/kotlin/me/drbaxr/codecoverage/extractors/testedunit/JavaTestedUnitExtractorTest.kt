package me.drbaxr.codecoverage.extractors.testedunit

import me.drbaxr.codecoverage.extractors.testfile.ManualTestFileExtractor
import me.drbaxr.codecoverage.extractors.unit.java.JavaUnitExtractor
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaTestedUnitExtractorTest {

    @Test
    fun `test demo package`() {
        val expected = setOf("de.syngenio.demo.MyTestClass", "de.syngenio.demo.MyTestClass.doSomething")

        val ue = JavaUnitExtractor(
            "src/test/resources/sample-projects/junit-tests-master",
            ManualTestFileExtractor("src/test/resources/sample-projects/junit-tests-master")
        )

        val testFile =
            "src/test/resources/sample-projects/junit-tests-master/src/test/java/de/syngenio/demo/TestMyTestClass.java"
        val actual = JavaTestedUnitExtractor(ue.findUnits())
            .findTestedUnits("src/test/resources/sample-projects/junit-tests-master", testFile)
            .map { it.identifier }.toSet()

        assertEquals(expected, actual)

    }

    @Test
    fun `test demo2 package`() {
        val expected = setOf(
            "de.syngenio.demo3.Actor",
            "de.syngenio.demo3.Actor.moveMotor",
            "de.syngenio.demo3.Actor.stopMotor",
            "de.syngenio.demo3.Controller",
            "de.syngenio.demo3.Controller.singleDecision",
            "de.syngenio.demo3.Sensor",
            "de.syngenio.demo3.Sensor.getBrightness",
            "de.syngenio.demo3.Sensor.getTemperature",
            "de.syngenio.demo3.Sensor.isMotorBlocked"
        )

        val ue = JavaUnitExtractor(
            "src/test/resources/sample-projects/junit-tests-master",
            ManualTestFileExtractor("src/test/resources/sample-projects/junit-tests-master")
        )

        val testFile =
            "src/test/resources/sample-projects/junit-tests-master/src/test/java/de/syngenio/demo3/TestController.java"
        val actual = JavaTestedUnitExtractor(ue.findUnits())
            .findTestedUnits("src/test/resources/sample-projects/junit-tests-master", testFile)
            .map { it.identifier }.toSet()

        assertEquals(expected, actual)
    }

}