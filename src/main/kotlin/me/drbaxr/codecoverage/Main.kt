package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.extractors.unit.comment.JavaCommentRemover
import me.drbaxr.codecoverage.extractors.unit.identifiers.JavaClassHeaderIdentifier
import me.drbaxr.codecoverage.extractors.unit.identifiers.JavaMethodHeaderIdentifier
import me.drbaxr.codecoverage.util.UnitTools

fun main(args: Array<String>) {
    val test = UnitTools.findUnitHeaders(
        "src/test/resources/unit-tools/test.java",
        JavaCommentRemover(),
        JavaMethodHeaderIdentifier(),
        JavaClassHeaderIdentifier()
    )

    test.forEach { println(it) }
}