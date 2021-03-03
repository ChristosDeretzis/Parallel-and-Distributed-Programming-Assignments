import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static java.lang.Thread.sleep;

public class ProducerClient {
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT;
    public static final int numOfIterations = 50;
    public static final int scale = 1000;

    public static void main(String[] args) throws Exception{
        try {
            // Locate rmi registry
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            // Look up for a specific name and get remote reference (stub)
            String rmiObjectName = "ProdCons";
            ProdCons prodCons = (ProdCons) registry.lookup(rmiObjectName);

            // Producer
            for (int i=0; i<numOfIterations; i++){
                prodCons.put(i);
                try {
                    sleep((int)(Math.random()*scale));
                } catch (InterruptedException e) { }
                System.out.println("Producer : " + i);
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
