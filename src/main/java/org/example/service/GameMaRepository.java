package org.example.service;

import org.example.models.Gamer;

import java.util.HashMap;
import java.util.Map;

public class GameMaRepository {
    protected static Map<String, Gamer> allGamerList = new HashMap<>();
    protected static Map<String, Integer> gamerLeaderList = new HashMap<>();

    public static Map<String, Gamer> getAllGamerList() {
        return allGamerList;
    }

    public static Map<String, Integer> getGamerLeaderList() {
        return gamerLeaderList;
    }
}
