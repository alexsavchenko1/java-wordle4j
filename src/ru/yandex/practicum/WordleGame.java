package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordleGame {

    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    private final PrintWriter log;

    private final List<String> guesses = new ArrayList<>();
    private final List<String> results = new ArrayList<>();
    private final Set<String> suggestedWords = new HashSet<>();

    private boolean won = false;

    public WordleGame(
            WordleDictionary dictionary,
            PrintWriter log
    ) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
        this.log = log;

        log.println("Начало игры.");
        log.println("Загаданное слово: " + answer);
        log.flush();
    }

    public WordleGame(
            WordleDictionary dictionary,
            String answer,
            PrintWriter log
    ) {
        this.dictionary = dictionary;
        this.answer = WordleDictionary.normalize(answer);
        this.steps = 6;
        this.log = log;

        log.println("Начало игры с заданным ответом: " + this.answer);
        log.flush();
    }

    public String makeMove(String word)
            throws InvalidWordException,
            WordNotFoundInDictionaryException {

        String normalizedWord =
                WordleDictionary.normalize(word);

        if (!normalizedWord.matches("[а-я]{5}")) {
            throw new InvalidWordException(
                    "Слово должно состоять из пяти русских букв"
            );
        }

        if (!dictionary.contains(normalizedWord)) {
            throw new WordNotFoundInDictionaryException(
                    "Слово отсутствует в словаре"
            );
        }

        String result =
                compareWords(normalizedWord, answer);

        guesses.add(normalizedWord);
        results.add(result);

        steps--;

        if (normalizedWord.equals(answer)) {
            won = true;
        }

        log.println(
                "Ход: "
                        + normalizedWord
                        + ", результат: "
                        + result
                        + ", осталось попыток: "
                        + steps
        );
        log.flush();

        return result;
    }

    public String getHint() {
        List<String> candidates = new ArrayList<>();

        for (String candidate : dictionary.getWords()) {

            if (suggestedWords.contains(candidate)) {
                continue;
            }

            boolean suitable = true;

            for (int i = 0; i < guesses.size(); i++) {

                String expectedResult =
                        results.get(i);

                String candidateResult =
                        compareWords(
                                guesses.get(i),
                                candidate
                        );

                if (!expectedResult.equals(candidateResult)) {
                    suitable = false;
                    break;
                }
            }

            if (suitable) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException(
                    "Не найдено подходящих слов"
            );
        }

        Random random = new Random();

        String hint =
                candidates.get(
                        random.nextInt(candidates.size())
                );

        suggestedWords.add(hint);

        log.println("Подсказка компьютера: " + hint);
        log.flush();

        return hint;
    }

    public static String compareWords(
            String guess,
            String answer
    ) {
        char[] result = {
                '-', '-', '-', '-', '-'
        };

        boolean[] used = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {

            if (result[i] == '+') {
                continue;
            }

            for (int j = 0; j < 5; j++) {

                if (
                        !used[j]
                                && guess.charAt(i)
                                == answer.charAt(j)
                ) {
                    result[i] = '^';
                    used[j] = true;
                    break;
                }
            }
        }

        return new String(result);
    }

    public boolean isWon() {
        return won;
    }

    public boolean isFinished() {
        return won || steps == 0;
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public List<String> getResults() {
        return results;
    }
}