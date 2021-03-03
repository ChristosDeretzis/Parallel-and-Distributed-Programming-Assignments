import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket dataSocket;
    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;
    private CountPi count;
    private int Client_id;

    public ServerThread(Socket socket, CountPi c, int id){
        dataSocket = socket;
        try {
            is = dataSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            os = dataSocket.getOutputStream();
            out = new PrintWriter(os, true);
            count = c;
            Client_id = id;
        } catch (IOException e){
            System.out.println("I/O Error: " + e);
        }
    }

    public void run(){
        String initial_message, calculated_pi;

        try {
            ServerProtocol app = new ServerProtocol(count);

            initial_message = app.send_initial_information_to_client(Client_id, 100000);
            out.println(initial_message);

            calculated_pi = in.readLine();
            String message_to_client = app.add_partial_sum_to_final_PI(calculated_pi);
            out.println(message_to_client);

            dataSocket.close();
        } catch (IOException e) {
            System.out.println("I/O Exception: "  + e);
        }
    }

}
