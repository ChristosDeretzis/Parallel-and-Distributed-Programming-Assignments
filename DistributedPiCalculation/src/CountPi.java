public class CountPi {
    private static volatile double pi = 0.0;

    // this function calculates the part of pi in each client
    public static double calculate_part_of_pi(int id, int number_of_clients, int number_of_steps){
        int myStart = id * (number_of_steps/number_of_clients);

        int myStop;
        if(id == number_of_clients - 1){
            myStop = number_of_steps;
        } else {
            myStop = myStart + (number_of_steps/number_of_clients);
        }

        double step = 1.0 / number_of_steps;
        double sum = 0.0;

        for (int i=myStart; i<myStop; i++){
            double x = ((double)i + 0.5)*step;
            sum += 4.0/(1.0 + Math.pow(x,2));
        }

        double part_of_pi = step*sum;
        return part_of_pi;
    }

    // This function add all of partial sums of pi in a global variable
    public synchronized void calculate_all_of_pi(double part_of_pi){
        double aNum;
        aNum = pi;

        try {
            Thread.sleep((int)Math.random()*100);
        } catch (InterruptedException e){ }

        aNum += part_of_pi;

        try {
            Thread.sleep((int)Math.random()*100);
        } catch (InterruptedException e) { }

        pi = aNum;
        System.out.println("PI from thread " + Thread.currentThread().getName() + " is " + aNum);
    }
}
