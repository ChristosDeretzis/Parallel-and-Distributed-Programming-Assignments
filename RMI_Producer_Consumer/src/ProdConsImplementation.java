import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProdConsImplementation extends UnicastRemoteObject implements ProdCons{
    private int[] contents;
    private boolean bufferEmpty = true;
    private boolean bufferFull = false;
    private final int size;
    private int front, back, counter = 0;

    // Constructor
    public  ProdConsImplementation(int s) throws RemoteException {
        this.size = s;
        contents = new int[size];
        for (int i=0; i<size; i++)
            contents[i] = 0;
        this.front = 0;
        this.back = -1;
    }

    // Put an item into buffer
    public synchronized void put(int data)
    {
        while (bufferFull) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        back = (back + 1) % size;
        contents[back] = data;
        counter++;
        System.out.println("Item " + data + " added in loc " + back + ". Count = " + counter);
        bufferEmpty = false;
        if (counter==size)
        {
            bufferFull = true;
            System.out.println("The buffer is full");
        }
        notifyAll();
    }

    // Get an item from bufffer
    public synchronized int get()
    {
        while (bufferEmpty) {
            try {
                wait();
            }
            catch (InterruptedException e) {}
        }
        int data = contents[front];
        front = (front + 1) % size;
        counter--;
        System.out.println("Item " + data + " removed from loc " + front + ". Count = " + counter);
        bufferFull = false;
        if (counter==0)
        {
            bufferEmpty = true;
            System.out.println("The buffer is empty");
        }
        notifyAll();
        return data;
    }
}
