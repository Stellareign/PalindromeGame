package org.example.models;

import java.util.HashMap;
import java.util.Objects;

public class Gamer {
    private String nickName;
    private HashMap<String, Integer> gamerPalindromeList;

    public Gamer() {
    }

    public Gamer(String nickName) {

        this.nickName = nickName;
    }

    public Gamer(String nickName, HashMap<String, Integer> gamerPalindromeList) {
        this.nickName = nickName;
        this.gamerPalindromeList = gamerPalindromeList;
    }

    public HashMap<String, Integer> getGamerPalindromeList() {
        return gamerPalindromeList;
    }

    public void setGamerPalindromeList(HashMap<String, Integer> gamerPalindromeList) {
        this.gamerPalindromeList = gamerPalindromeList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gamer gamer = (Gamer) o;
        return Objects.equals(nickName, gamer.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName);
    }


    @Override
    public String toString() {
        return "Gamer{" +
                "nickName='" + nickName + '\'' +
                ", gamerPalindromeList=" + gamerPalindromeList +
                '}';
    }
}
