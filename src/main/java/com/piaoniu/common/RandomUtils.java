package com.piaoniu.common;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomUtils {
    private static final char[] charSet = {'a', 'b', 'c', 'd', 'e', 'f',
                                           'g', 'h', 'i', 'j', 'k', 'l',
                                           'm', 'n', 'o', 'p', 'q', 'r',
                                           's', 't', 'u', 'v', 'w', 'x',
                                           'y', 'z', '1', '2', '3', '4',
                                           '5', '6', '7', '8', '9', '0'};

    private static final char[] charSet_62 = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

    private static final int length = charSet.length;
    private static final int length_62 = charSet_62.length;

    public static String randomStr(int size) {
        return randomStr(size, charSet, length);
    }

    public static String randomStr_62(int size) {
        return randomStr(size, charSet_62, length_62);
    }

    private static final char[] charSetNumber = { '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '0'};

    private static final int lengthNumber = charSetNumber.length;

    public static String randomNumberStr(int size){
        return randomStr(size, charSetNumber, lengthNumber);
    }

    private static String randomStr(int size,char[] charSet,int length){
        Random random = new Random();
        char[] result = new char[size];
        for(int i=0;i<size;i++){
            result[i] = charSet[random.nextInt(length)];
        }
        return new String(result);
    }

    public static <T> List<T> randomCandidateByWeight(List<WeightedItem<T>> items){
        List<T> result = new ArrayList<>();
        int total = items.stream().mapToInt(WeightedItem::getWeight).sum();
        int size = items.size();
        while (result.size() < size) {
            int random = new Random().nextInt(total);
            WeightedItem<T> chosen = null;
            int sumPrevious = 0;
            ListIterator<WeightedItem<T>> iterator = items.listIterator();
            while (iterator.hasNext() && sumPrevious <= random) {
                WeightedItem<T> next = iterator.next();
                sumPrevious += next.getWeight();
            }
            chosen = iterator.previous();
            iterator.remove();
            result.add(chosen.getItem());
        }
        return result;
    }

    public static <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
 }
