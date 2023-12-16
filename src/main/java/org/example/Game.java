package org.example;

import org.example.models.Gamer;
import org.example.service.GameService;

import java.util.Scanner;

public class Game {
     private final GameService gameService = new GameService();

    /**
     * ИГРА
     * Метод для игры в палиндром.
     * Пользователь вводит свой никнейм и палиндром (без цифр, с пробелами),
     * за который начисляются очки в зависимости от количества букв.
     * Добавляет игрока в список игроков, если его там нет.
     * Добавляет очки игроку и обновляет таблицу лидеров.
     * Выводит информацию о текущем игроке и таблицу лидеров.
     */
    public void palindromeGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваш никнейм");

        String nickname = scanner.nextLine();

        Gamer gamer = gameService.checkGamerAndPutNewGamerToList(nickname);

        System.out.println("Введите Ваш палиндром.");
        String palindromeString = scanner.nextLine();

        gameService.addToGamerLeaderList(gamer, palindromeString);

        System.out.println(gamer);
        gameService.printLeaderTable();
    }
}
