package com.sfs.global.qa.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomGenerator {

    private RandomGenerator() {

    }

    public static final SecureRandom random = new SecureRandom();

    public static String getRandomByDateTime() {
        return new SimpleDateFormat("ddMMyyhhmmss").format(new Date());
    }

    public static int randomInt(int max) {
        return random.nextInt(max);
    }

    public static int randomInt() {
        return random.nextInt();
    }

    public static int randomInt(int min, int max) {
        return (min + random.nextInt((max - min) + 1));
    }

    public static int getRandomIntegerInRangeInclusive(int min, int max) {
        if ((max - min) > 0) {
            return min + random.nextInt((max - min) + 1);
        } else {
            return min;
        }
    }

    public static int getRandomIntegerInRangeExclusive(int min, int max) {
        if ((max - min) > 0) {
            return min + random.nextInt(max - min);
        } else {
            return min;
        }
    }

    public static int randomIntByFigures(int numFigures) {
        return RandomGenerator.randomInt((int) Math.pow(10,(double)numFigures - 1), ((int)Math.pow(10,numFigures) - 1));
    }

    public static String getRandomNumberByFigures(int numFigures) {
        return String.valueOf(Math.round(random.nextFloat() * Math.pow(10, numFigures)));
    }

    public static double getRandomdoubleRangeInclusive(int min, int max) {
        double x = (random.nextDouble() * ((max - min) + 1)) + min;
        return Math.round(x * 100.00) / 100.00;
    }

    public static String getrandomString(int targetStringLength) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        int length = targetStringLength;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
