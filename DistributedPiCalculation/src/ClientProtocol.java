import java.io.IOException;

public class ClientProtocol {
    private CountPi calculatePi;

    public ClientProtocol(CountPi c){
        calculatePi = c;
    }

    public String process_part_of_pi(String input, int number_of_clients) throws IOException {
        String[] data = input.split("\\s+");

        int id = Integer.parseInt(data[0]);
        int number_of_total_moves = Integer.parseInt(data[1]);

        double part_of_pi = calculatePi.calculate_part_of_pi(id, number_of_clients, number_of_total_moves);

        return String.valueOf(part_of_pi) + " " + String.valueOf(id);
    }


}
