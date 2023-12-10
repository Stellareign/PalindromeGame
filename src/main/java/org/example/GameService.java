package org.example;

import org.example.models.Gamer;


import java.util.*;
import java.util.stream.Collectors;

public class GameService {

    private static Map<String, Gamer> allGamerList = new HashMap<>();
    //    private static List<Gamer> allGamerList = new ArrayList<>();
    private static Map<String, Integer> gamerLeaderList = new HashMap<>();

    public void palindromeGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваш никнейм");

        String nickname = scanner.nextLine();

        Gamer gamer = checkGamerAndPutNewGamerToList(nickname);

        System.out.println("Введите Ваш палиндром, цифры не допускаются. Чем больше букв, тем больше очков (пробелы не учитываются).");
        String palindromeString = scanner.nextLine();

        addToGamerLeaderList(gamer, palindromeString);

        System.out.println(gamer);
        printLeaderTable();
    }
//*********************************************************************************************************************

    /**
     * Проверка строки на палиндром
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
    // проверка геймера, был ли он уже в игре
    private Gamer checkGamerAndPutNewGamerToList(String nickName) {
        HashMap<String, Integer> gamerPalindromeList = new HashMap<>();
        Gamer gamer = new Gamer(nickName, gamerPalindromeList);
        if (allGamerList.containsKey(nickName)) {
            System.out.println("Привет, " + nickName + "! Рады снова видеть вас в игре. Давайте продолжим.");
            return allGamerList.get(nickName);
        }
        allGamerList.put(nickName, gamer);
        System.out.println("Привет, " + nickName + "! Добро пожаловать в игру!");
        return gamer;
    }


    //*************************************************************************************************************
// создание таблицы лидеров
      private void addToGamerLeaderList(Gamer gamer, String string) {
        String unitString = string.replaceAll("[\\p{Punct}\\s]", "").trim().toUpperCase();

        if (palindromeCheck(string) && checkGamerPaliCollectForRepeat(string, gamer.getGamerPalindromeList())) {
            doPalindromeGamerCollection(string, gamer); // добавляем палиндром после проверки в коллекцию игрока
            allGamerList.put(gamer.getNickName(), gamer); //обновляем лист игроков

            int gamerPoints = getGamersPoints(allGamerList.get(gamer.getNickName())); // выбираем количество очков из коллекции игрока

            if (!gamerLeaderList.isEmpty() &&
                    checkGameLeadersList(gamer)) {
                System.out.println("Отличный результат, вы попали в пятёрку лидеров игры!");
                gamerLeaderList.put(gamer.getNickName(), gamerPoints);
                sortLeaderList(gamerLeaderList);

            } else if (gamerLeaderList.isEmpty()) {
                gamerLeaderList.put(gamer.getNickName(), unitString.length());
                System.out.println("Неплохой результат! Ваш суммарный балл " + gamerPoints);

            } else if (!checkGameLeadersList(gamer)) {
                System.out.println("Неплохой результат, но есть к чему стремиться! Ваш суммарный балл " + gamerPoints);
            }
        } else if (!palindromeCheck(string) ){
            System.out.println("Это не палиндром. Попробуйте ещё раз!");
        } else if (!checkGamerPaliCollectForRepeat(string, gamer.getGamerPalindromeList())) {

            System.out.println("Такой палиндром уже есть в Вашем списке. Придумайте что-нибудь другое :)");
        }
    }

    //*************************************************************************************************************
    private boolean checkGameLeadersList(Gamer gamer) {
        int minLeaderPoints = Collections.min(gamerLeaderList.values());

        return !gamerLeaderList.isEmpty() && gamer != null && getGamersPoints(gamer) >= minLeaderPoints || gamerLeaderList.size() <5;
    }

    //******************************************************************************************************************
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

    //*********************************************************************************************************************
// сортировка листа лидеров в обратном порядке по очкам игроков
    private Map<String, Integer> sortLeaderList(Map<String, Integer> leaderList) {
        return leaderList.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    }

    //*********************************************************************************************************************
    //добавление палиндрома в коллекцию игрока
    private void doPalindromeGamerCollection(String string, Gamer gamer) {

        String unitString = string.replaceAll("[\\p{Punct}\\s]", "").trim();
        HashMap<String, Integer> tempGamerPalindromeList = gamer.getGamerPalindromeList();
        tempGamerPalindromeList.put(string.toUpperCase(), unitString.length());
        gamer.setGamerPalindromeList(tempGamerPalindromeList);
    }

    //******************************************************************************************************************
    private boolean checkGamerPaliCollectForRepeat(String palindrome, HashMap<String, Integer> tempGamerPalindromeList) {
        return tempGamerPalindromeList.isEmpty() ||
                !tempGamerPalindromeList.containsKey(palindrome.toUpperCase());
    }

    //******************************************************************************************************************
    // печать таблицы лидеров
    private void printLeaderTable() {
        System.out.println("Таблица лидеров:");
        for (Map.Entry<String, Integer> entry : sortLeaderList(gamerLeaderList).entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
