package me.drbaxr.codecoverage.analytics;

import j2html.tags.ContainerTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kotlin.Pair;
import kotlin.ranges.IntRange;
import me.drbaxr.codecoverage.models.Analytics;
import me.drbaxr.codecoverage.models.CodeUnit;
import me.drbaxr.codecoverage.util.FileTools;
import me.drbaxr.codecoverage.util.UnitTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static j2html.TagCreator.*;

public class HtmlGenerator {

    private final Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);

    private final String projectPath;
    private final List<CodeUnit> allUnits;
    private final List<CodeUnit> testedUnits;
    private final Analytics analytics;

    public HtmlGenerator(String projectPath, List<CodeUnit> allUnits, List<CodeUnit> testedUnits, Analytics analytics) {
        this.projectPath = projectPath;
        this.allUnits = allUnits;
        this.testedUnits = testedUnits;
        this.analytics = analytics;
    }

    public void generate() {
        logger.info("Generating HTML report...");

        Map<String, Set<CodeUnit>> filesToUnitsMap = UnitTools.Companion.getFilesToUnitsMap(allUnits);
        Set<String> fileNames = filesToUnitsMap.keySet();

        filesToUnitsMap.keySet().forEach(file -> getTestedUnitHeatMap(file, FileTools.Companion.getFileLines(file).size(), allUnits, testedUnits));

        createAnalyticsFiles(generateHtml(fileNames), "./analytics");
    }

    private ContainerTag generateHtml(Set<String> fileNames) {
        return html(
            head(
                title("Project Code Coverage"),
                link().withRel("stylesheet").withHref("styles.css")
            ),
            body(
                div(
                    attrs(".summary"),
                    h2(
                        attrs(".header"),
                        "Code Coverage: " + Math.round(analytics.getCodeCoverage() * 100) / 100f + "%"
                    ),
                    div(
                        attrs(".progress-bar"),
                        div(attrs(".progress"))
                            .withStyle("width: " + Math.round(analytics.getCodeCoverage()) + "%;")
                    ),
                    h2(
                        attrs(".header"),
                        "Line Coverage: " + Math.round(analytics.getLineCoverage() * 100) / 100f + "%"
                    ),
                    div(
                        attrs(".progress-bar"),
                        div(attrs(".progress"))
                            .withStyle("width: " + Math.round(analytics.getLineCoverage()) + "%;")
                    ),
                    generateUnitsTreeList("Found Testable Units (" + allUnits.size() + ")", allUnits, testedUnits)
                ),
                div(
                    attrs(".files-list"),
                    each(fileNames, this::generateFilesListHtml)
                )
            ),
            script().withSrc("main.js")
        );
    }

    private ContainerTag generateFilesListHtml(String fileName) {
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
                fileName.replaceFirst(projectPath + "/", "")
            ),
            div(
                attrs(".file-lines-container"),
                each(indexedFileLines, indexedLine -> generateFileLineHtml(indexedLine, heatmap)
                )
            )
        );
    }

    // TODO: maybe make tree reflect files tree
    private ContainerTag generateUnitsTreeList(String listRootText, List<CodeUnit> units, List<CodeUnit> testedUnits) {
        Map<String, Set<CodeUnit>> filesToUnitsMap = UnitTools.Companion.getFilesToUnitsMap(units);

        return ul(
            attrs(".tree"),
            li(
                span(
                    attrs(".caret.root"),
                    listRootText
                ),
                ul(
                    attrs(".nested"),
                    each(filesToUnitsMap, fileToUnits -> li(
                        span(
                            attrs(".caret"),
                            fileToUnits.getKey().replaceFirst(projectPath + "/", "") + " (" + fileToUnits.getValue().size() + ")" // TODO: Check why some are empty
                        ),
                        ul(
                            attrs(".nested"),
                            each(fileToUnits.getValue(), unit -> li(
                                attrs(
                                    testedUnits.stream()
                                        .filter(u -> u.getIdentifier().equals(unit.getIdentifier()))
                                        .findFirst()
                                        .map(u -> ".c-green")
                                        .orElse(".c-red")
                                ),
                                unit.getIdentifier())
                            )
                        )
                    ))
                )
            )
        );
    }

    private ContainerTag generateFileLineHtml(Pair<Integer, String> indexedLine, int[] heatmap) {
        String classes = ".file-line";

        if (heatmap[indexedLine.getFirst()] < 0) {
            classes += ".red";
        } else if (heatmap[indexedLine.getFirst()] > 0) {
            classes += ".green";
        }

        return div(
            attrs(classes),
            span(attrs(".whitespace"), getLineWhitespace(indexedLine.getSecond())),
            span(indexedLine.getSecond().trim())
        );
    }

    private void createAnalyticsFiles(ContainerTag html, String folderPath) {
        File analyticsFolder = new File(folderPath);
        if (analyticsFolder.mkdir()) {
            logger.info("Created " + folderPath + " folder");
        }

        try {
            generateHtmlFile(html, folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            generateResourceFile(folderPath, "styles.css");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            generateResourceFile(folderPath, "main.js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateHtmlFile(ContainerTag html, String folderPath) throws IOException {
        String fileName = folderPath + "/index.html";
        FileWriter writer = new FileWriter(fileName);

        writer.write(html.renderFormatted());

        writer.close();
        logger.info("Generated " + fileName + " file");
    }

    private void generateResourceFile(String folderPath, String resourcePath) throws IOException {
        String fileName = folderPath + "/" + resourcePath;
        String content = FileTools.Companion.getFileFromResourceContent(resourcePath);
        FileWriter writer = new FileWriter(fileName);

        writer.write(content);

        writer.close();
        logger.info("Generated " + fileName + " file");
    }

    // TODO: make ALL tested lines green - will indicate tested lines metric
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
