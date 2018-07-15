package ru.star;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Taxi_1 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            String[] args1 = br.readLine().split(" ");
            int n = Integer.parseInt(args1[0]);
            long r = Long.parseLong(args1[1]);

            if (r < 0) {
                throw new IllegalArgumentException();
            }

            String[] arg2 = br.readLine().split(" ");

            if (n != arg2.length) {
                throw new IllegalArgumentException();
            }

            List<Long> points = new ArrayList<>(arg2.length);
            for (String anArg2 : arg2) {
                points.add(Long.parseLong(anArg2));
            }

//        System.out.println("n = " + n);
//        System.out.println("r = " + r);
//        System.out.print("Points = ");
//        for (Long point : points) {
//            System.out.print(point + " ");
//        }
//        System.out.println();

            Collections.sort(points);

            int minPoints = getMinPoints(n, r, points);
            System.out.println(minPoints);
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println(0);
        }
    }

    private static int getMinPoints(int n, long r, List<Long> points) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (points.get(0) + r < points.get(1)) {
            return 1 + getMinPoints(n - 1, r, points);
        }
        int countLess = countLess(points, points.get(0) + r);
        countLess = countLess(points, points.get(countLess - 1) + r);
        for (int i = 0; i < countLess; i++) {
            points.remove(0);
        }
        return 1 + getMinPoints(n - countLess, r, points);
    }

    private static int countLess(List<Long> points, long high) {
        int i = 0;
        while (i < points.size() && points.get(i) <= high) {
            i++;
        }
        return i;
    }
}

