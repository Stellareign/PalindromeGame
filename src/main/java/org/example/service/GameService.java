package org.example.service;

import org.example.models.Gamer;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.service.GameMaRepository.allGamerList;
import static org.example.service.GameMaRepository.gamerLeaderList;

public class GameService {

    /**
     * Метод проверяет наличия игрока в списке игроков и добавляет нового игрока.
     * Если игрок уже есть в списке, метод возвращает его объект.
     * Если игрока нет в списке, метод создает новый объект игрока и добавляет его в список.
     * Метод также выводит соответствующее приветственное сообщение для игрока.
     *
     * @param nickName никнейм игрока
     * @return объект игрока
     */
    public Gamer checkGamerAndPutNewGamerToList(String nickName) {
        HashMap<String, Integer> gamerPalindromeList = new HashMap<>();
        Gamer gamer = new Gamer(nickName, gamerPalindromeList);
        if (allGamerList.containsKey(nickName)) {
            System.out.println("Привет, " + nickName + "! Рады снова видеть Вас в игре. Давайте продолжим.");
            return allGamerList.get(nickName);
        }
        allGamerList.put(nickName, gamer);
        System.out.println("Привет, " + nickName + "! Добро пожаловать в игру! " +
                        "\n Для того, чтобы начать, необходимо написать палиндром - фразу или слово, которые читаются одинаково " +
                        "\n в обе стороны, цифры не допускаются. Чем больше букв, тем больше очков (пробелы не учитываются).");
        return gamer;
    }


    //*************************************************************************************************************
    /**
     * Метод добавляет во время игры палиндром в коллекцию игрока, обновляет список всех игроков
     * и проверяет результат игрока.
     *
     * @param gamer  объект класса Gamer, представляющий игрока
     * @param string строка, содержащая палиндром, который нужно добавить в коллекцию игрока
     */
    public void addToGamerLeaderList(Gamer gamer, String string) {
        String unitString = string.replaceAll("[\\p{Punct}\\s]", "").trim().toUpperCase();

        if (palindromeCheck(string) && checkGamerPaliCollectForRepeat(string, gamer.getGamerPalindromeList())) {
            doPalindromeGamerCollection(string, gamer); // добавляем палиндром после проверки в коллекцию игрока
            allGamerList.put(gamer.getNickName(), gamer); //обновляем лист игроков

            int gamerPoints = getGamersPoints(allGamerList.get(gamer.getNickName())); // выбираем количество очков из коллекции игрока

            if (!gamerLeaderList.isEmpty() &&
                    checkGameLeadersList(gamer)) {
                System.out.println("Отличный результат, Вы попали в пятёрку лидеров игры!");
                gamerLeaderList.put(gamer.getNickName(), gamerPoints);
                sortLeaderList(gamerLeaderList);

            } else if (gamerLeaderList.isEmpty()) {
                gamerLeaderList.put(gamer.getNickName(), unitString.length());
                System.out.println("Неплохой результат! Ваш суммарный балл " + gamerPoints);

            } else if (!checkGameLeadersList(gamer)) {
                System.out.println("Неплохой результат, но есть к чему стремиться! Ваш суммарный балл " + gamerPoints);
            }
        } else if (!palindromeCheck(string)) {
            System.out.println("Это не палиндром. Попробуйте ещё раз!");
        } else if (!checkGamerPaliCollectForRepeat(string, gamer.getGamerPalindromeList())) {

            System.out.println("Такой палиндром уже есть в Вашем списке. Придумайте что-нибудь другое :)");
        }
    }

    //******************************************************************************************************************
    //**************************************ВСПОмОГАТЕЛЬНЫЕ МЕТОДЫ******************************************************
    /**
     * ПРОВЕРКА СТРОКИ ГНА ПАЛИНДРОМ
     *
     * @param str
     * @return
     */
    private boolean palindromeCheck(String str) {

        String unitString = str.replaceAll("[\\p{Punct}\\s]", "").trim().toUpperCase();
        if (unitString.length() > 2) {
            int left = 0;
            int right = unitString.length() - 1;

            while (left < right) {
                if (unitString.charAt(left) != unitString.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
        return false;
    }

    //*************************************************************************************************************
    /**
     * ПРОВЕРКА ЛИСТА ЛИДЕРОВ
     *
     * @param gamer
     * @return возвращает true, если лист лидеров пуст, если суммарное количесво очков игрока больше или равно
     * меньшему количеству баллов в таблице или если количество лидеров меньше пяти
     */
    private boolean checkGameLeadersList(Gamer gamer) {
        int minLeaderPoints = Collections.min(gamerLeaderList.values());

        return !gamerLeaderList.isEmpty() && gamer != null &&
                getGamersPoints(gamer) >= minLeaderPoints || gamerLeaderList.size() < 5;
    }

    //******************************************************************************************************************
    /**
     * ПОЛУЧЕНИЕ СУММАРНОГО КОЛИЧЕСТВАБАЛЛОВ ИГРОКА
     *
     * @param gamer
     * @return
     */
    private int getGamersPoints(Gamer gamer) {

        int sum = 0;
        HashMap<String, Integer> tempGamerPalindromeList = gamer.getGamerPalindromeList();
        if (!tempGamerPalindromeList.isEmpty()) {
            for (Map.Entry<String, Integer> entry : tempGamerPalindromeList.entrySet()) {
                sum += entry.getValue();
            }
            return sum;
        }
        return 0;
    }

    //******************************************************************************************************************/

    /**
     * Сортировка таблицы лидеров в обратном порядке по количеству очков: от большего к меньшему
     *
     * @param leaderList
     * @return
     */
    private Map<String, Integer> sortLeaderList(Map<String, Integer> leaderList) {
        return leaderList.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    }

    //******************************************************************************************************************
    /**
     * ДОБАВЛЕНИЕ ПАЛИНДРОМА В КОЛЛЕКЦИЮ ИГРОКА
     *
     * @param string
     * @param gamer
     */
    private void doPalindromeGamerCollection(String string, Gamer gamer) {

        String unitString = string.replaceAll("[\\p{Punct}\\s]", "").trim();
        HashMap<String, Integer> tempGamerPalindromeList = gamer.getGamerPalindromeList();
        tempGamerPalindromeList.put(string.toUpperCase(), unitString.length());
        gamer.setGamerPalindromeList(tempGamerPalindromeList);
    }

    //******************************************************************************************************************
    /**
     * ПРОВЕРКА ПАЛИНДРОМА, ВВЕДЁННОГО ИГРОКОМ, НА ПОВТОР
     *
     * @param palindrome
     * @param tempGamerPalindromeList
     * @return возвращает true, если коллекция палиндромов игрока пуста или не содержит введённый палиндром
     */
    private boolean checkGamerPaliCollectForRepeat(String palindrome,
                                                   HashMap<String, Integer> tempGamerPalindromeList) {
        return tempGamerPalindromeList.isEmpty() ||
                !tempGamerPalindromeList.containsKey(palindrome.toUpperCase());
    }

    //******************************************************************************************************************
    /**
     * ПЕЧАТЬ ТАБЛИЦЫ ЛИДЕРОВ ИГРЫ
     * Метод выводит на печать таблицу лидеров игры
     */
    public void printLeaderTable() {
        System.out.println("Таблица лидеров:");
        for (Map.Entry<String, Integer> entry : sortLeaderList(gamerLeaderList).entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
