package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    @Test
    void shouldLoadDictionary() throws Exception {
        WordleDictionaryLoader loader =
                new WordleDictionaryLoader(
                        new PrintWriter(System.out)
                );

        WordleDictionary dictionary =
                loader.load("words_ru.txt");

        assertFalse(dictionary.getWords().isEmpty());
    }
}