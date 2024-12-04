import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContadorPalabrasRemoto extends Remote {
    int contarPalabras(String filePath, long start, long end) throws RemoteException;
}


