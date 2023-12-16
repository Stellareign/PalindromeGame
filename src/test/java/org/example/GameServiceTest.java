package org.example;

import org.example.models.Gamer;
import org.example.service.GameMaRepository;
import org.example.service.GameService;
import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.util.HashMap;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.example.service.GameMaRepository.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {
    private final GameService gameService = new GameService();
    Map<String, Integer> gamerLeaderListTest = GameMaRepository.getGamerLeaderList();
    Map<String, Gamer> allGamerListTest = GameMaRepository.getAllGamerList();
    String nickname1 = "Вася";
    String palindromeString = "потоп";
    String palindromeString2 = "топот";
    String notPalindromeString = "краска";
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    String gameAnswer1 = "Привет, " + nickname1 + "! Добро пожаловать в игру! " +
            "\n Для того, чтобы начать, необходимо написать палиндром - фразу или слово, которые читаются одинаково " +
            "\n в обе стороны, цифры не допускаются. Чем больше букв, тем больше очков (пробелы не учитываются).";
    String gameAnswer2 = "Неплохой результат! Ваш суммарный балл " + palindromeString.length();
    String gameAnswerNoPalindrome = "Это не палиндром. Попробуйте ещё раз!";

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

//******************************************* ТЕСТЫ: *******************************************************************
    @Test
    @DisplayName(value = "Введён палиндром")
    void palindromeGameWhenIsPalindromeTest() {

        allGamerListTest.clear(); // чистим мапу после предыдущего тестирования
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

        allGamerListTest.clear(); // чистим мапу после предыдущего тестирования
        gamerLeaderListTest.clear();
        Gamer gamer = gameService.checkGamerAndPutNewGamerToList(nickname1);


        gameService.addToGamerLeaderList(gamer, notPalindromeString);
        String expectedOutput = gameAnswer1 + System.lineSeparator() + gameAnswerNoPalindrome + System.lineSeparator();

        assertTrue(allGamerListTest.containsKey("Вася"));
        assertEquals(gamerLeaderListTest.get("Вася"), null);
        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }

    @Test
    @DisplayName(value = "Когда тот же игрок вводит палиндром")
    void palindromeGameWhenIsPalindromeAttempt2Test() {
        HashMap<String, Integer> gamerPalindromeList = new HashMap<>();
        gamerPalindromeList.put(palindromeString, 5);
        Gamer gamerForTest = new Gamer("Вася", gamerPalindromeList);
        allGamerListTest.put("Вася", gamerForTest);
        gamerLeaderListTest.put("Вася", 5);

        Gamer gamer = gameService.checkGamerAndPutNewGamerToList(nickname1);
        String gameAnswer3 = "Привет, Вася! Рады снова видеть Вас в игре. Давайте продолжим.";
        String gameAnswer4 = "Отличный результат, Вы попали в пятёрку лидеров игры!";

        gameService.addToGamerLeaderList(gamer, palindromeString2);
        String expectedOutput = gameAnswer3 + System.lineSeparator() + gameAnswer4 + System.lineSeparator();

        assertTrue(allGamerListTest.containsKey("Вася"));
        assertEquals(getGamerLeaderList().get("Вася"), 10);

        assertEquals(expectedOutput, outputStreamCaptor.toString());
    }
}