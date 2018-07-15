package ru.star;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Taxi_2 {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static class Point {
        long x;
        long y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Root {
        Point startPoint;
        Point endPoint;

        Root(Point startPoint, Point endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
        }
    }

    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(br.readLine());

            if (n == 1) {
                System.out.println(1);
                return;
            }

            List<Point> startCoordiantes = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                String[] coord = br.readLine().split(" ");
                startCoordiantes.add(new Point(Long.parseLong(coord[0]), Long.parseLong(coord[1])));
            }

            List<Root> roots = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                String[] coord = br.readLine().split(" ");
                roots.add(new Root(
                        new Point(Long.parseLong(coord[0]), Long.parseLong(coord[1])),
                        new Point(Long.parseLong(coord[2]), Long.parseLong(coord[3]))
                ));
            }

            long[][] times = new long[n][n];
            int[] arr  = new int[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    times[i][j] = getTime(startCoordiantes.get(i), roots.get(j));
                }
                arr[i] = i+1;
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    System.out.print(times[i][j] + " ");
                }
                System.out.println();
            }

            int count = fuctorial(arr.length);
            int max = arr.length - 1;
            System.out.println("Вариантов " + count);
            int shift = max;
            int minIndex = 0;
            long curr_max = 0;
            int[] rez = new int[n];
            while (count > 0) {
                int t = arr[shift];
                arr[shift] = arr[shift - 1];
                arr[shift - 1] = t;
                System.out.println("t = " + t);
                print(arr, times);
                long max1 = getMax(arr, times);
                if (curr_max < max1) {
                    curr_max = max1;
                    minIndex = t;
                    rez = Arrays.copyOf(arr, arr.length);
                }
                count--;
                if (shift < 2) {
                    shift = max;
                } else {
                    shift--;
                }
            }
            System.out.println("minSum = " + curr_max);
            System.out.println("minIndex = " + (minIndex-1));
            for (int i = 0; i < n; i++) {
                System.out.println(rez[i]);
            }
            // В итоге нужно было искать минимум из максимумов
        } catch (Exception ex) {
            System.out.println(0);
            ex.printStackTrace();
        }
    }

    private static long getMax(int[] arr, long[][] times) {
        long max = 0;
        for (int i = 0; i < arr.length; i++) {
            int j = arr[i];
            if (max < times[i][j-1]) {
                max = times[i][j-1];
            }
        }
        return max;
    }

    private static long getTime(Point point, Root root) {
        return getTime(point, root.startPoint) + getTime(root.startPoint, root.endPoint);

    }

    private static long getTime(Point point1, Point point2) {
        return Math.abs(point2.x - point1.x) + Math.abs(point2.y - point1.y);

    }

    static void print(int[] arr, long[][] times) {
        long rez = 0L;
        for (int i = 0; i < arr.length; i++) {
            int j = arr[i];
            rez += times[i][j-1];
            System.out.println("i = " + i + "; rez = " + times[i][j-1]);
        }
        System.out.println(Arrays.toString(arr) +  " = " + rez);
    }

    static int fuctorial(int n) {
        return (n > 0) ? n * fuctorial(n - 1) : 1;
    }
}

