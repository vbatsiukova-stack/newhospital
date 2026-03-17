package utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class UniqueWordsApp {
    public static void main(String[] args) throws Exception {
        String text = FileUtils.readFileToString(new File("input.txt"), StandardCharsets.UTF_8);
        String[] words = StringUtils.split(text);

        Set<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            uniqueWords.add(word.toLowerCase());
        }

        FileUtils.writeStringToFile(
                new File("result.txt"),
                "Unique words count: " + uniqueWords.size(),
                StandardCharsets.UTF_8
        );

        System.out.println("Done. Check result.txt");
    }
}