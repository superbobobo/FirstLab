package org.example;

public class Statistics {
    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;
    private int minInteger = Integer.MAX_VALUE;
    private int maxInteger = Integer.MIN_VALUE;
    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double floatSum = 0;
    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void addInteger(int number) {
        integerCount++;
        minInteger = Math.min(minInteger, number);
        maxInteger = Math.max(maxInteger, number);
    }

    public void addFloat(double number) {
        floatCount++;
        minFloat = Math.min(minFloat, number);
        maxFloat = Math.max(maxFloat, number);
        floatSum += number;
    }

    public void addString(String str) {
        stringCount++;
        minStringLength = Math.min(minStringLength, str.length());
        maxStringLength = Math.max(maxStringLength, str.length());
    }

    public int getIntegerCount() {
        return integerCount;
    }

    public int getFloatCount() {
        return floatCount;
    }

    public int getStringCount() {
        return stringCount;
    }

    public int getMinInteger() {
        return integerCount > 0 ? minInteger : Integer.MAX_VALUE;
    }

    public int getMaxInteger() {
        return integerCount > 0 ? maxInteger : Integer.MIN_VALUE;
    }

    public double getMinFloat() {
        return floatCount > 0 ? minFloat : Double.MAX_VALUE;
    }

    public double getMaxFloat() {
        return floatCount > 0 ? maxFloat : Double.MIN_VALUE;
    }

    public double getFloatSum() {
        return floatSum;
    }

    public int getMinStringLength() {
        return stringCount > 0 ? minStringLength : Integer.MAX_VALUE;
    }

    public int getMaxStringLength() {
        return stringCount > 0 ? maxStringLength : 0;
    }

    public void printStatistics(boolean full) {
        System.out.println("Краткая статистика:");
        System.out.println("Целые числа: " + integerCount);
        System.out.println("Вещественные числа: " + floatCount);
        System.out.println("Строки: " + stringCount);

        if (full) {
            System.out.println("\nПолная статистика:");
            if (integerCount > 0) {
                System.out.println("Целые числа: min=" + minInteger + ", max=" + maxInteger);
            }
            if (floatCount > 0) {
                System.out.println("Вещественные числа: min=" + minFloat + ", max=" + maxFloat + ", сумма=" + floatSum + ", среднее=" + (floatSum / floatCount));
            }
            if (stringCount > 0) {
                System.out.println("Строки: min длина=" + minStringLength + ", max длина=" + maxStringLength);
            }
        }
    }
}