public class ServerProtocol {
    private static CountPi piCalculation;

    public ServerProtocol(CountPi c) {
        piCalculation = c;
    }

    public String send_initial_information_to_client(int id, int number_of_steps){
        return String.valueOf(id) + " " + String.valueOf(number_of_steps);
    }

    public String add_partial_sum_to_final_PI(String input){
        String[] input_array = input.split("\\s+");
        double part_of_pi = Double.parseDouble(input_array[0]);
        int Client_id = Integer.parseInt(input_array[1]);

        piCalculation.calculate_all_of_pi(part_of_pi);

        String output = "Part of pi from client " + Client_id + " was added to the main pi successfully";
        return output;
    }
}
