package org.example.service;

import org.example.models.Gamer;

import java.util.HashMap;
import java.util.Map;

public class GameRepository {
    protected static Map<String, Gamer> allGamerList = new HashMap<>();
    protected static Map<String, Integer> gamerLeaderList = new HashMap<>();
}
