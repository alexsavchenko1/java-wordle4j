package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleGame game;

    @BeforeEach
    void setUp() {
        WordleDictionary dictionary = new WordleDictionary(
                List.of(
                        "герой",
                        "гонец",
                        "декан",
                        "кисть",
                        "слово"
                ),
                new PrintWriter(System.out)
        );

        game = new WordleGame(
                dictionary,
                "герой",
                new PrintWriter(System.out)
        );
    }

    @Test
    void shouldWinWhenAnswerIsCorrect() throws Exception {
        String result = game.makeMove("герой");

        assertEquals("+++++", result);
        assertTrue(game.isWon());
        assertTrue(game.isFinished());
    }

    @Test
    void shouldDecreaseStepsAfterValidMove() throws Exception {
        game.makeMove("гонец");

        assertEquals(5, game.getSteps());
    }

    @Test
    void shouldNotDecreaseStepsAfterInvalidWord() {
        assertThrows(
                InvalidWordException.class,
                () -> game.makeMove("кот")
        );

        assertEquals(6, game.getSteps());
    }

    @Test
    void shouldThrowExceptionWhenWordIsNotInDictionary() {
        assertThrows(
                WordNotFoundInDictionaryException.class,
                () -> game.makeMove("банан")
        );

        assertEquals(6, game.getSteps());
    }

    @Test
    void shouldCompareSameWords() {
        String result =
                WordleGame.compareWords("герой", "герой");

        assertEquals("+++++", result);
    }

    @Test
    void shouldCompareDifferentWords() {
        String result =
                WordleGame.compareWords("гонец", "герой");

        assertEquals("+^-^-", result);
    }

    @Test
    void shouldFinishAfterSixMoves() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.makeMove("гонец");
        }

        assertTrue(game.isFinished());
        assertFalse(game.isWon());
    }

    @Test
    void shouldCorrectlyHandleRepeatedLetters() {
        String result =
                WordleGame.compareWords("ололо", "слово");

        assertEquals("-++-+", result);
    }

    @Test
    void hintShouldBeCompatibleWithPreviousMove() throws Exception {
        String result = game.makeMove("гонец");

        String hint = game.getHint();

        assertTrue(
                game.getGuesses().size() == 1
        );

        assertEquals(
                result,
                WordleGame.compareWords("гонец", hint)
        );
    }
}