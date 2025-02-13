package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            FileFilterUtility utility = new FileFilterUtility();
            utility.processFiles(args);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}