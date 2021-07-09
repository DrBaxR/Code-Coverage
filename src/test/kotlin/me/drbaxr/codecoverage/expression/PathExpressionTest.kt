package me.drbaxr.codecoverage.expression

import kotlin.test.Test
import kotlin.test.assertEquals

class PathExpressionTest {

    var pathExpression = PathExpression("")

    @Test
    fun `test single expression`() {
        pathExpression = PathExpression("PathExpressionTest.kt")

        with(pathExpression) {
            assertEquals(matches("**"), true)
            assertEquals(matches("Path*"), true)
            assertEquals(matches("*.kt"), true)
            assertEquals(matches("*Test.kt"), true)
            assertEquals(matches("*test.KT", false), true)
            assertEquals(matches("*expr*", false), true)

            assertEquals(matches("path*"), false)
            assertEquals(matches(""), false)
        }


    }

    @Test
    fun `test compound expression`() {
        pathExpression = PathExpression("test/kotlin/PathExpressionTest.kt")

        with(pathExpression) {
            assertEquals(matches("test/kotlin/PathExpressionTest.kt"), true)
            assertEquals(matches("t*/kotlin*/*Test*"), true)
            assertEquals(matches("t*/kotlin*/*test*", false), true)
            assertEquals(matches("**/**/*Path*"), true)
            assertEquals(matches("tEst/**/**", false), true)
            assertEquals(matches("tEst/**", false), true)

            assertEquals(matches("t*/kotlin*/*test*"), false)
            assertEquals(matches("tEst/**/**"), false)
        }
    }

    @Test
    fun `test wildcard compound partial with term in the front`() {
        pathExpression = PathExpression("test/kotlin/PathExpressionTest.kt")

        with(pathExpression) {
            assertEquals(matches("test/**"), true)
            assertEquals(matches("TEST/**", false), true)
            assertEquals(matches("test/**/**/**"), true)
            assertEquals(matches("test/kotlin/**"), true)
            assertEquals(matches("*t/*ot*/**"), true)

            assertEquals(matches("TEST/**"), false)
            assertEquals(matches("test123/**"), false)
            assertEquals(matches("*t/*to/**"), false)
            assertEquals(matches("*t/*to/**/**/**"), false)
        }
    }

    @Test
    fun `test wildcard compound partial with term in the back`() {
        pathExpression = PathExpression("test/kotlin/PathExpressionTest.kt")

        with(pathExpression) {
        assertEquals(matches("**/PathExpressionTest.kt"), true)
            assertEquals(matches("**/kotlin/PathExpressionTest.kt"), true)
            assertEquals(matches("**/kotlin/PathExpressiontest.kt", false), true)
            assertEquals(matches("**/test/kotlin/PathExpressionTest.kt"), true)
            assertEquals(matches("**/kotlin/*.kt"), true)

            assertEquals(matches("**/kotlin/PathExpressiontest.kt"), false)
            assertEquals(matches("**/not/test/kotlin/PathExpressionTest.kt"), false)
            assertEquals(matches("**/test/not/PathExpressionTest.kt"), false)
            assertEquals(matches("**/test/PathExpressionTest.kt"), false)
        }
    }

    @Test
    fun `test wildcard compound partial with term in middle`() {
        pathExpression = PathExpression("dir1/dir2/dir3/dir4/dir5/file.txt")

        with(pathExpression) {
            assertEquals(matches("**/dir1/**"), true)
            assertEquals(matches("**/dir2/**"), true)
            assertEquals(matches("**/dir3/**"), true)
            assertEquals(matches("**/dir4/**"), true)
            assertEquals(matches("**/dir5/**"), true)
            assertEquals(matches("**/file.txt/**"), true)
            assertEquals(matches("**/file.tXt/**", false), true)
            assertEquals(matches("**/dir2/dir3/**"), true)
            assertEquals(matches("**/dir2/**/dir4/**"), true)
            assertEquals(matches("**/dir*/**/*4/**"), true)

            assertEquals(matches("**/dir1/dir3/**"), false)
            assertEquals(matches("**/DIR*/**/*4/**"), false)
            assertEquals(matches("**/file/**"), false)
            assertEquals(matches("**/file.tXt/**"), false)

        }
    }

}