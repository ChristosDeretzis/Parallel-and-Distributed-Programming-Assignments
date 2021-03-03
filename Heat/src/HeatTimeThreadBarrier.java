package com.example.ChrisDeretzis;

public class HeatTimeThreadBarrier extends Thread{
    private double [][]table1;
    private double [][]table2;
    private int myStart;
    private int myStop;
    private int mySize;
    private int maxIters;
    private double myDiff;
    private CyclicBarrier myBarrier;
    private int row;
    private int col;
    private int heat_source;
    private double diff1;
    private double maxDiff;

    public HeatTimeThreadBarrier(int myId, int numThreads, double[][] table1, double[][] table2, int size, int max_iters, CyclicBarrier b){
        this.table1 = table1;
        this.table2 = table2;
        this.mySize = size;
        this.myStart = myId * (this.mySize / numThreads);
        this.myStop = this.myStart + (this.mySize / numThreads) - 1;
        if (myId == numThreads - 1) {
            this.myStop = mySize;
        }
        this.myBarrier = b;
        this.maxIters = max_iters;
        this.row = 1;
        this.col = 1;
        this.heat_source = 1;
        this.diff1 = 1;
        this.maxDiff = 0.0000000000000001;
    }

   public void run(){
        for (int iters = 0; iters < maxIters; iters++){
            table1[row][col] = heat_source;

            for (int i=myStart + 1; i<myStop; i++){
                for (int j=1; j<mySize-1; j++){
                    table2[i][j] = 0.25*(table1[i-1][j] + table1[i+1][j] + table1[i][j-1] + table1[i][j+1]);
                }
            }

            HeatTimeIterationBarrier.diff = 0.0;

            myBarrier.barrier();

            myDiff = 0.0;
            for (int i =myStart + 1; i<myStop; i++){
                for (int j=1; j<mySize-1; j++){
                    myDiff += myDiff + (table2[i][j]-table1[i][j])*(table2[i][j]-table1[i][j]);

                    HeatTimeIterationBarrier.lock.lock();
                    try {
                        HeatTimeIterationBarrier.diff += myDiff;
                    } finally {
                        HeatTimeIterationBarrier.lock.unlock();
                    }
                }
            }

            myBarrier.barrier();

            double diff2 = Math.sqrt(HeatTimeIterationBarrier.diff)/(mySize*mySize);
            System.out.println("Diff 1: " + diff1 + ", Diff 2: " + diff2 );

            myBarrier.barrier();

            if ((diff1 - diff2) <= maxDiff){
                System.out.println("Convergence in " + iters +" iterations");
                break;
            }

            myBarrier.barrier();

            diff1 = diff2;

            myBarrier.barrier();

            for (int i=myStart; i<myStop; i++){
                for (int j=0; j<mySize; j++){
                    table1[i][j] = table2[i][j];
                }
            }

            myBarrier.barrier();
        }
   }
}

