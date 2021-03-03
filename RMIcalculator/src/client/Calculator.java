package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote{
    double add(double a, double b)  throws RemoteException;
    double substract(double a, double b) throws RemoteException;
    double multiply(double a, double b) throws RemoteException;
    double divide(double a, double b) throws RemoteException;
}