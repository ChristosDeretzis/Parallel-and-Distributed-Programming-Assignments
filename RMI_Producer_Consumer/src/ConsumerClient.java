import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Thread.sleep;

public class ConsumerClient {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;
    private static final int scale = 1000;

    public static void main(String[] args) throws Exception{
        try {
            // Locate rmi registry
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            // Look up for a specific name and get remote reference (stub)
            String rmiObjectName = "ProdCons";
            ProdCons prodCons = (ProdCons) registry.lookup(rmiObjectName);

            // Consumer
            int value;
            while (true) {
                try {
                    sleep((int)(Math.random()*scale));
                } catch (InterruptedException e) { }
                value = prodCons.get();
                System.out.println("Consumer < " + value);
            }
        } catch (RemoteException re) {
            System.out.println("Remote Exception");
            re.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }
    }
}
