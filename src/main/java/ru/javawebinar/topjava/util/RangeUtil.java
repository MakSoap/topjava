package ru.javawebinar.topjava.util;

public class RangeUtil {

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T currentValue, T startValue, T endValue) {
        return currentValue.compareTo(startValue) >= 0 && currentValue.compareTo(endValue) < 0;
    }
    public static <T extends Comparable<T>> boolean isInterval(T currentValue, T startValue, T endValue) {
        return currentValue.compareTo(startValue) >= 0 && currentValue.compareTo(endValue) <= 0;
    }
}
