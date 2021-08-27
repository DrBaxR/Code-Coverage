package me.drbaxr.codecoverage.analytics;

import j2html.tags.ContainerTag;
import me.drbaxr.codecoverage.models.Analytics;
import me.drbaxr.codecoverage.models.CodeUnit;
import me.drbaxr.codecoverage.util.FileTools;
import me.drbaxr.codecoverage.util.UnitTools;

import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static j2html.TagCreator.*;

public class HtmlGenerator {

    public void generate(List<CodeUnit> allUnits, List<CodeUnit> testedUnits, Analytics analytics) {
        // TODO: check https://j2html.com/examples.html and make html
        System.out.println("Generating HTML...");

        Map<String, Set<CodeUnit>> filesToUnitsMap = UnitTools.Companion.getFilesToUnitsMap(allUnits);
        Set<String> fileNames = filesToUnitsMap.keySet();

        ContainerTag html = html(
            head(
                title("Project Code Coverage"),
                link().withRel("stylesheet").withHref("styles.css")
            ),
            body(
                div(
                    attrs(".summary"),
                    h2("Code Coverage: " + Math.round(analytics.getCodeCoverage() * 100) / 100f + "%"),
                    h2("Line Coverage: " + Math.round(analytics.getLineCoverage() * 100) / 100f + "%")
                ),
                div(
                    attrs(".files-list"),
                    each(fileNames, fileName ->
                        div(
                            attrs(".file"),
                            div(
                                attrs(".file-name"),
                                fileName
                            ),
                            div(
                                attrs(".file-lines-container"),
                                each(FileTools.Companion.getFileLines(fileName), line ->
                                    div(
                                        attrs(".file-line"), // TODO: add a class to it if it's tested in smt
                                        span(attrs(".whitespace"), getLineWhitespace(line)),
                                        span(line.trim())
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );

        try {
            // TODO: also create css file in resources and create it if it does not exist
            String fileName = "./analytics/index.html";
            FileWriter writer = new FileWriter(fileName);

            writer.write(html.renderFormatted());

            writer.close();

            System.out.println("Successfully generated ./analytics/index.html file!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLineWhitespace(String line) {
        StringBuilder whitespace = new StringBuilder();

        while(line.startsWith("\t")) {
            whitespace.append("--");
            line = line.replaceFirst("\t", "");
        }

        while(line.startsWith(" ")) {
            whitespace.append("-");
            line = line.replaceFirst(" ", "");
        }

        return whitespace.toString();
    }
}
