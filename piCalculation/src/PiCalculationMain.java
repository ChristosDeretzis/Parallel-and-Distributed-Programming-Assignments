import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PiCalculationMain {
    public static Lock lock = new ReentrantLock();
    public static double sum = 0;

    public static void main(String[] args) {
        int size = 0;
        int numOfThreads = 0;

        if (args.length != 2) {
            System.out.println("Usage: java PiCalculationMain <vector size> <number of threads>");
            System.exit(1);
        }

        try {
            size = Integer.parseInt(args[0]);
            numOfThreads = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Integer argument expected");
            System.exit(1);
        }

        if (numOfThreads == 0) {
            numOfThreads = Runtime.getRuntime().availableProcessors();
        }

        long start = System.currentTimeMillis();

        PiCalculationThread[] threads = new PiCalculationThread[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new PiCalculationThread(i, numOfThreads, size);
            threads[i].start();
        }

        for (int i = 0; i < numOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) { }
        }

        long elapsedTimeMillis = System.currentTimeMillis()-start;
        System.out.println("time in seconds = "+ (elapsedTimeMillis/1000.0));
        System.out.println("difference between real pi: " + (double)(Math.PI - sum));

        System.out.println(sum);
    }
}
