package com.example.ChrisDeretzis;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ChatClientWithThreads {
    private static final int PORT = 1238;
    public static void main(String args[]) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        Socket dataSocket = new Socket(host,PORT);
        SendThread send = new SendThread(dataSocket);
        Thread thread = new Thread(send);
        thread.start();
        ReceiveThread receive = new ReceiveThread(dataSocket);
        Thread thread2 = new Thread(receive);
        thread2.start();
    }
}

class SendThread implements Runnable{

    private Socket dataSocket;

    public SendThread(Socket soc){
        dataSocket = soc;
    }

    public void run() {
        try{
            System.out.println("Connection established");
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
            OutputStream os = dataSocket.getOutputStream();
            PrintWriter out = new PrintWriter(os,true);
            System.out.print("Send message, CLOSE for exit:");
            String msg = user.readLine();
            while(!msg.equals("CLOSE")) {
                out.println(msg);
                System.out.print("Send message:");
                msg = user.readLine();
            }
            out.println(msg);
            dataSocket.close();
            System.out.println("Data Socket closed");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}

class ReceiveThread implements Runnable{

    private Socket dataSocket;

    public ReceiveThread(Socket soc){
        dataSocket = soc;
    }

    public void run() {
        try{
            // Get the ingoing stream
            InputStream is = dataSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String msg = in.readLine();
            String message = msg.substring(0, msg.lastIndexOf(" "));
            String id = msg.substring(msg.lastIndexOf(" ")+1);
            while(msg != null) {
                System.out.println("\nReceived message from user " + id + ": " + message);
                System.out.print("Send message: ");
                msg = in.readLine();
                message = msg.substring(0, msg.lastIndexOf(" "));
                id = msg.substring(msg.lastIndexOf(" "));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
