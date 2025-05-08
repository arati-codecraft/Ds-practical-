import java.rmi.*;

interface ServerIntf extends Remote{

// Syntax for method declaration: access_spectfter return_type method_name(arguments...) { return value)


public double Addition (double numi, double num2) throws RemoteException; public double Subtraction (double numi, double num2) throws RemoteException;

public double Multiplication (double numi, double num2) throws RemoteException;

public double Division (double numi, double num2) throws RemoteException;
}