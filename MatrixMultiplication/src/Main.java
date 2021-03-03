import java.util.Scanner;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the size of the array: ");
        int size = scanner.nextInt();

        if (size <= 0) {
            System.out.println("size should be positive integer");
            System.exit(1);
        }

        int[][] a = new int[size][size];
        int[][] b = new int[size][size];
        int[][] finalTable = new int[size][size];

        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                a[i][j] = i;
                b[i][j] = j;
            }
        }

        long start = System.currentTimeMillis();

        MatrixMultiplyThread threads[][] = new MatrixMultiplyThread[size][size];

        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                threads[i][j] = new MatrixMultiplyThread(a[i],getColumn(b ,j), i, j, finalTable);
                threads[i][j].start();
            }
        }

        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                try {
                    threads[i][j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time to fill in the table: " + elapsedTime + "\n");

        System.out.println("Final Table: ");
        for (int i=0;i<size;i++){
            for (int j=0;j<size;j++){
                if (j== size-1){
                    System.out.print(finalTable[i][j] + "\n");
                } else {
                    System.out.print(finalTable[i][j] + " ");
                }
            }
        }

    }

    static int[] getColumn(int[][] matrix, int column) {
        return IntStream.range(0, matrix.length)
                .map(i -> matrix[i][column]).toArray();
    }
}


