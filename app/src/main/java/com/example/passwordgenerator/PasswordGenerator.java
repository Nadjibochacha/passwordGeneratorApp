package com.example.passwordgenerator;
import java.util.Random;

public class PasswordGenerator {
    public static String generatePassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()-_=+{}[]";
        String allChars = upper + lower + digits + symbols;

        StringBuilder password = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(allChars.length());
            password.append(allChars.charAt(index));
        }

        return password.toString();
    }
}
