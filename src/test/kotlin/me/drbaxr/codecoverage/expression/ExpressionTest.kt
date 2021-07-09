package me.drbaxr.codecoverage.expression

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.todo

class ExpressionTest {

    private var expression = Expression("")

    @Test
    fun `test matches ends with`() {
        val syntax = "*Test"
        testMatchExpressionWithSyntax("matchesTest", syntax, true, "should only match thing that ends with wanted syntax")
        testMatchExpressionWithSyntax("matchestest", syntax, false, "should not match lowercase")
        testMatchExpressionWithSyntax("notTestGood", syntax, false, "should not match thing that has searched thing in middle")
        testMatchExpressionWithSyntax("notSupposedToMatch", syntax, false, "should not match thing unrelated")
        testMatchExpressionWithSyntax("Test", syntax, true, "should also match term")
    }

    private fun testMatchExpressionWithSyntax(expressionValue: String, syntax: String, expected: Boolean, message: String) {
        expression = Expression((expressionValue))
        assertEquals(expression.matches(syntax), expected, message)
    }

    @Test
    fun `test matches starts with`() {
        val syntax = "Test*"
        testMatchExpressionWithSyntax("TestGood", syntax, true, "should match thing that starts with stuff")
        testMatchExpressionWithSyntax("notTestGood", syntax, false, "should not match thing with search term in middle")
        testMatchExpressionWithSyntax("unrelated", syntax, false, "should not match thing that is unrelated")
        testMatchExpressionWithSyntax("Test", syntax, true, "should also match term")
    }

    @Test
    fun `test matches contains`() {
        val syntax = "*Test*"
        testMatchExpressionWithSyntax("somethingTestSomething", syntax, true, "should match if it contains term")
        testMatchExpressionWithSyntax("TestGood", syntax, true, "should match thing that starts with stuff")
        testMatchExpressionWithSyntax("GoodTest", syntax, true, "should match thing that starts with stuff")
        testMatchExpressionWithSyntax("notRelated", syntax, false, "should not match unrelated")
        testMatchExpressionWithSyntax("notRelated", syntax, false, "should not match unrelated")
        testMatchExpressionWithSyntax("Test", syntax, true, "should also match term")
    }

    @Test
    fun `test matches wildcard`() {
        val syntax = "**"
        testMatchExpressionWithSyntax("nskjdvnx.c", syntax, true, "should match anything")
        testMatchExpressionWithSyntax("ppppppppp", syntax, true, "should match anything")
    }

    @Test
    fun `test matches full syntax`() {
        val syntax = "fullMatch"
        testMatchExpressionWithSyntax("fullMatch", syntax, true, "should match full syntax")
        testMatchExpressionWithSyntax("fullMatch123", syntax, false, "should not match if it doesn't match the whole thing")
    }

    @Test
    fun `test matches without case`() {
        expression = Expression("cooltest")
        assertEquals(expression.matches("*TeSt", false), true, "should match since case is irrelevant")
        assertEquals(expression.matches("COOl*", false), true, "should match since case is irrelevant")
        assertEquals(expression.matches("*LteS*", false), true, "should match since case is irrelevant")
        assertEquals(expression.matches("CoOLTest", false), true, "should match full syntax")
    }

}