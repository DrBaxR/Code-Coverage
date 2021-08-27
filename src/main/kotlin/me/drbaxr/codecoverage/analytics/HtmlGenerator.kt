package me.drbaxr.codecoverage.analytics

import j2html.TagCreator.*
import java.io.File

class HtmlGenerator {

    fun generate() {
        println("Generating HTML...")

        val html = html(
            head(
                title("This is title LMAO")
            ),
            body(
                main(
                    attrs("#main.content"),
                    h1("Welcome to hell :)")
                )
            )
        )

        File("./analysis/index.html").writeText(html.renderFormatted())
    }

}