import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProdCons extends Remote {
    int get() throws RemoteException;
    void put(int element) throws RemoteException;
}
