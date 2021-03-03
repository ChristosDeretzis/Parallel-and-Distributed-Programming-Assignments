public class PiCalculationThread2 {
    private int myId;
    private int myStart;
    private int myStop;
    private double mySum;
    private int size;

    public PiCalculationThread2(int id, int numOfThreads, int size){
        this.myId = id;
        this.mySum = 0.0;
        this.size = size;
        this.myStart = this.myId * (size/numOfThreads);
        this.myStop = this.myStart + (size/numOfThreads);
        if (this.myId == (numOfThreads-1)) this.myStop = size;
    }

    public void run(){
        double step = 1.0 / size;
        for (long i=this.myStart; i<this.myStop; i++){
            double x = ((double)i + 0.5)*step;
            this.mySum += 4.0/(1.0 + Math.pow(x,2));
        }
        double myPi = step*this.mySum;

        synchronized (this.getClass()){
            PiCalculationMain.sum += myPi;
        }
    }
}
