package com.example.ChrisDeretzis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedChatServerTCP {
    private static final int PORT = 1238;
    private static final int number_of_clients = 3;

    public static void main(String args[]) throws IOException {

        ServerSocket connectionSocket  = new ServerSocket(PORT);
        Socket[] datasockets = new Socket[number_of_clients];
        ServerThread[] threads = new ServerThread[number_of_clients];



        for (int i=0; i<number_of_clients; i++) {
            System.out.println("Server is waiting for client " + (i + 1) + " in port: " + PORT);
            datasockets[i] = connectionSocket.accept();
            System.out.println("Received request from " + datasockets[i].getInetAddress()); }

        for (int i=0; i<number_of_clients;i++){
            threads[i] = new ServerThread(i+1, datasockets);
            threads[i].start();
        }

//        for (int i=0;i<number_of_clients;i++) {
//            threads[i].join();
//        }

//            System.out.println("Server is waiting first client in port: " + PORT);
//            Socket dataSocket1 = connectionSocket.accept();
//            System.out.println("Received request from " + dataSocket1.getInetAddress());
//            System.out.println("Server is waiting second client in port: " + PORT);
//            Socket dataSocket2 = connectionSocket.accept();
//            System.out.println("Received request from " + dataSocket2.getInetAddress());
//
//            ServerThread sthread1 = new ServerThread(dataSocket1, dataSocket2);
//            sthread1.start();
//            ServerThread sthread2 = new ServerThread(dataSocket2, dataSocket1);
//            sthread2.start();
    }
}
