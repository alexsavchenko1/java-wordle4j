package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        try (
                PrintWriter log = new PrintWriter(
                        Files.newBufferedWriter(
                                Path.of("wordle.log"),
                                StandardCharsets.UTF_8
                        )
                );
                Scanner scanner = new Scanner(System.in)
        ) {
            try {
                WordleDictionaryLoader loader =
                        new WordleDictionaryLoader(log);

                WordleDictionary dictionary =
                        loader.load("words_ru.txt");

                WordleGame game =
                        new WordleGame(dictionary, log);

                while (!game.isFinished()) {
                    System.out.println(
                            "Введите слово. Осталось попыток: "
                                    + game.getSteps()
                    );

                    String word = scanner.nextLine();

                    if (word.isEmpty()) {
                        word = game.getHint();
                        System.out.println("Подсказка компьютера: " + word);
                    }

                    try {
                        String result = game.makeMove(word);

                        System.out.println("> " + word);
                        System.out.println("> " + result);

                    } catch (InvalidWordException e) {
                        System.out.println(
                                "Ошибка ввода: " + e.getMessage()
                        );

                    } catch (WordNotFoundInDictionaryException e) {
                        System.out.println(
                                "Ошибка ввода: " + e.getMessage()
                        );
                    }
                }

                if (game.isWon()) {
                    System.out.println("Вы победили!");
                } else {
                    System.out.println("Попытки закончились.");
                }

                System.out.println(
                        "Загаданное слово: " + game.getAnswer()
                );

            } catch (Exception e) {
                log.println("Критическая ошибка программы:");
                e.printStackTrace(log);
                log.flush();

                System.out.println(
                        "Произошла внутренняя ошибка программы."
                );
            }

        } catch (IOException e) {
            System.err.println(
                    "Невозможно создать файл журнала: "
                            + e.getMessage()
            );
        }
    }
}