package com.example.ChrisDeretzis;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket myDataSocket;
    private Socket[] allSockets;
    private InputStream is;
    private BufferedReader in;
    private OutputStream[] os;
    private PrintWriter[] out;
    private int clientID;
    private static final String EXIT = "CLOSE";

    public ServerThread(int client_id, Socket[] sockets)
    {
        clientID = client_id;

        myDataSocket = sockets[client_id-1];
        allSockets = sockets;

        os = new OutputStream[3];
        out = new PrintWriter[3];
        try {
            is = myDataSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            for (int i=0;i<sockets.length;i++){
                if(i != client_id-1) {
                    os[i] = allSockets[i].getOutputStream();
                    out[i] = new PrintWriter(os[i], true);
                }
            }
        }
        catch (IOException e)	{
            System.out.println("I/O Error " + e);
        }
    }

    public void run()
    {
        String inmsg, outmsg;

        try {
            inmsg = in.readLine();
            ServerProtocol app = new ServerProtocol();
            outmsg = app.processRequest(inmsg);

            while(!outmsg.equals(EXIT)) {
                for (int i=0;i<allSockets.length;i++){
                    if (i != (clientID-1))
                        out[i].println(outmsg + " " + String.valueOf(clientID));
                }
                inmsg = in.readLine();
                outmsg = app.processRequest(inmsg);
            }

            myDataSocket.close();
            System.out.println("Data socket closed");

        } catch (IOException e)	{
            System.out.println("I/O Error " + e);
        }
    }
}
