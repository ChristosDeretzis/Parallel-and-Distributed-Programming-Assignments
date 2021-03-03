package com.example.ChrisDeretzis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HeatTimeIterationBarrier {
    static double diff;
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        int size = 0;
        int numThreads = 0;
        int maxIters = 0;

        if (args.length != 3) {
            System.out.println("Usage: java HeatIteration <size> <maxIters> <number of threads>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
            maxIters = Integer.parseInt(args[1]);
            numThreads = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException nfe) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }
        if (size <= 0) {
            System.out.println("Attention: <size> should be >0");
            System.exit(1);
        }
        if (maxIters <= 0) {
            System.out.println("Attention: <maxIters> should be >0");
            System.exit(1);
        }
        if (numThreads <= 0) numThreads = Runtime.getRuntime().availableProcessors();

        double[][] table1 = new double[size][size];
        double[][] table2 = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                table1[i][j] = 0;
                table2[i][j] = 0;
            }
        // get current time
        long start = System.currentTimeMillis();

        // create threads
        HeatTimeThreadBarrier threads[] = new HeatTimeThreadBarrier[numThreads];
        // create barrier
        CyclicBarrier barrier = new CyclicBarrier(numThreads);


        // thread execution
        for(int i = 0; i < numThreads; i++)
        {
            threads[i] = new HeatTimeThreadBarrier(i, numThreads, table1, table2, size, maxIters, barrier);
            threads[i].start();
        }

        // wait for threads to terminate
        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        // get current time and calculate elapsed time
        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("time in ms = "+ elapsedTimeMillis);


        for (int i = 0; i < size; i++) {
            for (int j=0; j < size; j++){
                System.out.printf("%12.10f ", table2[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
