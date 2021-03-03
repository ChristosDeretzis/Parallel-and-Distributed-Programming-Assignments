import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedEchoServerTCP {
    private static final int PORT = 1235;
    private static CountPi countPi = new CountPi();
    private static int numOfClients = 8;

    public static void main(String[] args) throws IOException{
        ServerSocket connectionSocket = new ServerSocket(PORT);

        Socket[] dataSockets = new Socket[numOfClients];
        ServerThread[] sthread = new ServerThread[numOfClients];

        System.out.println("Server is listening to Port " + PORT);
        for (int i=0;i<numOfClients;i++){
            dataSockets[i] = connectionSocket.accept();
            System.out.println("Received request from " + dataSockets[i].getInetAddress() + " with id of " + i);

            sthread[i] = new ServerThread(dataSockets[i], countPi, i);
            sthread[i].start();
        }

        for (int i=0; i<numOfClients;i++) {
            sthread[i].join();
        }
    }
}
