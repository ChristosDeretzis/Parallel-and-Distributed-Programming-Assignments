package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            String rmiObjectName = "Calculator";
            server.Calculator calculator = (server.Calculator)registry.lookup(rmiObjectName);

            double result = calculator.divide(5, 6.7);
            System.out.println("Result: " + result);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Remote exception");
        } catch (NotBoundException e) {
            e.printStackTrace();
            System.out.println("Not Bound Exception");
        }
    }
}
