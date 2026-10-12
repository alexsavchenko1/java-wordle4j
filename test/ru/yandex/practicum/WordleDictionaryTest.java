package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    @Test
    void shouldContainExistingWord() {
        WordleDictionary dictionary = new WordleDictionary(
                List.of("герой", "слово", "декан"),
                new PrintWriter(System.out)
        );

        assertTrue(dictionary.contains("герой"));
    }

    @Test
    void shouldNotContainUnknownWord() {
        WordleDictionary dictionary = new WordleDictionary(
                List.of("герой", "слово", "декан"),
                new PrintWriter(System.out)
        );

        assertFalse(dictionary.contains("кисть"));
    }

    @Test
    void shouldNormalizeWord() {
        String result = WordleDictionary.normalize("ЁЖИКИ");

        assertEquals("ежики", result);
    }
}