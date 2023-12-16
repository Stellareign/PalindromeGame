package org.example;

import org.example.models.Gamer;
import org.example.service.GameMaRepository;
import org.example.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static org.example.service.GameMaRepository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {
    private GameService gameService = new GameService();

    String nickname1 = "Вася";
    String palindromeString = "потоп";
    String notPalindromeString = "пото";
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Map<String, Gamer> allGamerListTest = GameMaRepository.getAllGamerList();
    String gameAnswer1 = "Привет, " + nickname1 + "! Добро пожаловать в игру! " +
            "\n Для того, чтобы начать, необходимо написать палиндром - фразу или слово, которые читаются одинаково " +
            "\n в обе стороны, цифры не допускаются. Чем больше букв, тем больше очков (пробелы не учитываются).";
    String gameAnswer2 = "Неплохой результат! Ваш суммарный балл " + palindromeString.length();
    String gameAnswerNoPalindrome = "Это не палиндром. Попробуйте ещё раз!";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName(value = "Проверка основного метода игры, введён палиндром")
    void palindromeGameWhenIsPalindromeTest() {

        Gamer gamer = gameService.checkGamerAndPutNewGamerToList(nickname1);

        gameService.addToGamerLeaderList(gamer, palindromeString);
        String expectedOutput = gameAnswer1 + System.lineSeparator() + gameAnswer2 + System.lineSeparator();

        assertTrue(allGamerListTest.containsKey("Вася"));
        assertEquals(getGamerLeaderList().get("Вася"), 5);

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName(value = "Когда введён не палиндром")
    void palindromeGameWhenIsNotPalindromeTest() {

        Gamer gamer = gameService.checkGamerAndPutNewGamerToList(nickname1);

        gameService.addToGamerLeaderList(gamer, notPalindromeString);
        String expectedOutput = gameAnswer1 + System.lineSeparator() + gameAnswerNoPalindrome + System.lineSeparator();

        assertTrue(allGamerListTest.containsKey("Вася"));
        assertEquals(getGamerLeaderList().get("Вася"), null);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}