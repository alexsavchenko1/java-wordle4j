package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(String fileName)
            throws IOException, EmptyDictionaryException {

        List<String> words = new ArrayList<>();

        try (
                BufferedReader reader = new BufferedReader(
                        new FileReader(
                                fileName,
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                String word = WordleDictionary.normalize(line);

                if (word.length() == 5) {
                    words.add(word);
                }
            }
        }

        if (words.isEmpty()) {
            throw new EmptyDictionaryException(
                    "Словарь игры пуст"
            );
        }

        log.println(
                "Загрузка словаря завершена. Загружено слов: "
                        + words.size()
        );
        log.flush();

        return new WordleDictionary(words, log);
    }
}