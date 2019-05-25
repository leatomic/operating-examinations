package com.lklk.examination;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印区间内的所有质数，如打印[2, 10]内的所有质数，则查找结果包括：{2, 3, 5, 7}
 */
public class PrimeFinder {

    public static void main(String[] args) {
        PrimeFinder finder = new PrimeFinder();

        int from = 2, to = 10;
        finder.findAllBetween(from, to)
                .forEach(System.out::println);

//        from = 3; to = 18;
//        finder.findAllBetween(from, to)
//                .forEach(System.out::println);
    }

    public List<Integer> findAllBetween(int low, int high) {

        if (low < 2 || low >= high ) {
            throw new IllegalArgumentException("low: " + low + ", high: " + high
                    + " didn't meet the condition 2 <= low < high");
        }

        List<Integer> primes = new ArrayList<>();

        // 提前处理包括2和/或3的情况
        if (low <= 3) {
            primes.add(3);
            if (low == 2)
                primes.add(2);
        }


        /*
            除了2和3，非6的倍数-1，或者非6的倍数+1的，都可以拆为2个质因数相乘来表示，肯定为合数
            这里只考虑6x-1，以及6x+1的情况，从小于等于low的（6x-1）的最大值：largest6xd1开始：
            例如：low = 2，high = 18，则
            largest6xd1: -1
            num: -1, 1    ->    5, 7    ->    11, 13    ->    17, 19
        */
        int num;
        int largest6xd1 = low - (low % 6 + 1) % 6;      // 不大于low的（6x-1）的最大值
        for (int i = largest6xd1; i <= high; i += 6) {

            num = i;
            if (num >= low && isPrimeInternally(num))
                primes.add(num);

            num = i + 2;
            if (num >= low && num <= high && isPrimeInternally(num))
                primes.add(num);

        }

        return primes;

    }

    private boolean isPrimeInternally(int num) {
        int sqrt = (int) Math.sqrt(num);
        for (int i = 5; i <= sqrt; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

}
