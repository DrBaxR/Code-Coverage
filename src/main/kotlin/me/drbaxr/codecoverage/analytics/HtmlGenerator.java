package me.drbaxr.codecoverage.analytics;

import j2html.tags.ContainerTag;
import kotlin.Pair;
import kotlin.ranges.IntRange;
import me.drbaxr.codecoverage.models.Analytics;
import me.drbaxr.codecoverage.models.CodeUnit;
import me.drbaxr.codecoverage.util.FileTools;
import me.drbaxr.codecoverage.util.UnitTools;

import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class HtmlGenerator {

    public void generate(List<CodeUnit> allUnits, List<CodeUnit> testedUnits, Analytics analytics) {
        // TODO: check https://j2html.com/examples.html and make html
        System.out.println("Generating HTML...");

        Map<String, Set<CodeUnit>> filesToUnitsMap = UnitTools.Companion.getFilesToUnitsMap(allUnits);
        Set<String> fileNames = filesToUnitsMap.keySet();

        filesToUnitsMap.keySet().forEach(file -> {
            getTestedUnitHeatMap(file, FileTools.Companion.getFileLines(file).size(), allUnits, testedUnits);
        });

        // TODO: refactor this shit
        // TODO: also include all units count and tested units count
        // TODO: also add a progress bar thing to show coverage
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
                    each(fileNames, fileName -> {
                            int[] heatmap = getTestedUnitHeatMap(fileName, FileTools.Companion.getFileLines(fileName).size(), allUnits, testedUnits);
                            String[] fileLines = FileTools.Companion.getFileLines(fileName).toArray(String[]::new);
                            List<Pair<Integer, String>> indexedFileLines = new ArrayList<>();
                            for (int i = 0; i < fileLines.length; ++i) {
                                indexedFileLines.add(new Pair<>(i + 1, fileLines[i]));
                            }


                            return div(
                                attrs(".file"),
                                div(
                                    attrs(".file-name"),
                                    fileName
                                ),
                                div(
                                    attrs(".file-lines-container"),
                                    each(indexedFileLines, indexedLine -> {
                                            String classes = ".file-line";

                                            if (heatmap[indexedLine.getFirst()] < 0) {
                                                classes += ".red";
                                            } else if (heatmap[indexedLine.getFirst()] > 0) {
                                                classes += ".green";
                                            }

                                            return div(
                                                attrs(classes), // TODO: add a class to it if it's tested in smt
                                                span(attrs(".whitespace"), getLineWhitespace(indexedLine.getSecond())),
                                                span(indexedLine.getSecond().trim())
                                            );
                                        }
                                    )
                                )
                            );
                        }
                    )
                )
            )
        );

        try {
            // TODO: also create css file in resources and create it if it does not exist
            // TODO: also, not sure what happens if no index.html file exists
            String fileName = "./analytics/index.html";
            FileWriter writer = new FileWriter(fileName);

            writer.write(html.renderFormatted());

            writer.close();

            System.out.println("Successfully generated ./analytics/index.html file!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] getTestedUnitHeatMap(String file, int linesNumber, List<CodeUnit> allUnits, List<CodeUnit> testedUnits) {
        List<CodeUnit> fileAllUnits = allUnits.stream()
            .filter(unit -> unit.getHostFilePath().equals(file)).collect(Collectors.toList());
        List<CodeUnit> fileTestedUnits = testedUnits.stream()
            .filter(unit -> unit.getHostFilePath().equals(file)).collect(Collectors.toList());

        int[] heatmap = new int[linesNumber + 2];
        Arrays.fill(heatmap, 0);

        fileAllUnits.forEach(unit -> {
            IntRange linesRange = unit.getLinesRange();
            heatmap[linesRange.getFirst()] = -1;
        });

        fileTestedUnits.forEach(unit -> {
            IntRange linesRange = unit.getLinesRange();
            heatmap[linesRange.getFirst()] = 1;
        });


        return heatmap;
    }

    private String getLineWhitespace(String line) {
        StringBuilder whitespace = new StringBuilder();

        while (line.startsWith("\t")) {
            whitespace.append("--");
            line = line.replaceFirst("\t", "");
        }

        while (line.startsWith(" ")) {
            whitespace.append("-");
            line = line.replaceFirst(" ", "");
        }

        return whitespace.toString();
    }
}
