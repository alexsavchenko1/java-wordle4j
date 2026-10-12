package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private final List<String> words;
    private final PrintWriter log;

    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = words;
        this.log = log;

        log.println("Создан словарь. Количество слов: " + words.size());
        log.flush();
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWords() {
        return words;
    }

    public static String normalize(String word) {
        return word
                .toLowerCase()
                .replace('ё', 'е');
    }
}