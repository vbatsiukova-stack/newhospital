package com.solvd.hospital.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class HospitalTextAnalyzer {

    public static void analyze(String inputPath, String outputPath) throws IOException {
        String text = Files.readString(Path.of(inputPath), StandardCharsets.UTF_8);

        Map<String, Integer> wordCounts = new TreeMap<>();

        String[] words = text.toLowerCase().split("[^\\p{L}]+");

        for (String word : words) {
            if (!word.isEmpty()) {
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("word,count\n");

        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            result.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append("\n");
        }

        Files.writeString(Path.of(outputPath), result.toString(), StandardCharsets.UTF_8);
    }
}