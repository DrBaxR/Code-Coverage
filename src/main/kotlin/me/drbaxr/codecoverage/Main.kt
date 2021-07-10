package me.drbaxr.codecoverage

import me.drbaxr.codecoverage.util.FileTools


fun main(args: Array<String>) {
    val files = FileTools.getFilePaths(".")

    files.forEach { println(it) }
}