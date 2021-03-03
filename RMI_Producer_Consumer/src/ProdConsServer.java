import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ProdConsServer {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;

    public static void main(String[] args) throws Exception {
        // Should be first, especially if server is NOT localhost
        System.setProperty("java.rmi.server.hostname", HOST);

        // Remote Object Creation
        int bufferSize = 15;
        ProdCons prodCons = new ProdConsImplementation(bufferSize);

        Registry registry = LocateRegistry.createRegistry(PORT);

        // Bind remote object to a name and publish in rmi registry
        String rmiObjectName = "ProdCons";
        registry.rebind(rmiObjectName, prodCons);
        System.out.println("Remote object bounded.");

        // Server is running until we press a key
        System.out.println("Press <Enter> for exit.");
        System.in.read();

        // Free space and clear rmi registry
        UnicastRemoteObject.unexportObject(prodCons, true);
        registry.unbind(rmiObjectName);
        System.out.println("Remote object unbounded.");
    }
}
