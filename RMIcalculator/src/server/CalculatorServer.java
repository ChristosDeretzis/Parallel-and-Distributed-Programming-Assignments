package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer {
    public static final String HOST = "localhost";
    public static final int PORT = Registry.REGISTRY_PORT;

    public static void main(String[] args) throws Exception {

        Calculator robj = new CalculatorImpl();

        Registry registry = LocateRegistry.createRegistry(PORT);

        String rmiObjectName =  "Calculator";
        registry.rebind(rmiObjectName, robj);
    }
}
