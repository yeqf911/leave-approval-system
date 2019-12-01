package com.lyx.las.helper;

import java.util.Random;

public class AccessTokenHelper {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    private static final AccessTokenHelper INSTANCE = new AccessTokenHelper();

    private AccessTokenHelper() {
    }

    public static AccessTokenHelper getInstance() {
        return INSTANCE;
    }


    public String generateAccessToken() {
        Random random = new Random();
        int rand;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            rand = random.nextInt(54);
            sb.append(chars[rand]);
        }
        return sb.toString();
    }
}
