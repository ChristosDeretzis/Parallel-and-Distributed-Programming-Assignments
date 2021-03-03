import java.io.*;
import java.net.Socket;

public class EchoClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 1235;
    private static CountPi countPi = new CountPi();
    private static int numOfClients = 8;

    public static void main(String[] args) throws IOException {
        Socket dataSocket = new Socket(HOST, PORT);

        InputStream is = dataSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        OutputStream os = dataSocket.getOutputStream();
        PrintWriter out = new PrintWriter(os, true);

        String input_message, output_message, final_message;
        ClientProtocol clientProtocol = new ClientProtocol(countPi);

        input_message = in.readLine();
        output_message = clientProtocol.process_part_of_pi(input_message, numOfClients);
        out.println(output_message);
        final_message = in.readLine();
        System.out.println(final_message);

    }
}