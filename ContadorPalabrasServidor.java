import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContadorPalabrasServidor extends UnicastRemoteObject implements ContadorPalabrasRemoto {

    public ContadorPalabrasServidor() throws RemoteException {
        super();
    }

    @Override
    public int contarPalabras(String filePath, long start, long end) throws RemoteException {
        int wordCount = 0;
        long currentPosition = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.skip(start);
            currentPosition = start;
            String line;

            while ((line = reader.readLine()) != null && currentPosition < end) {
                wordCount += line.split("\\s+").length;
                currentPosition += line.length() + 1; // Sumamos la longitud de la línea y el salto de línea
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordCount;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Por favor, proporciona el número de puerto como argumento.");
            return;
        }

        try {
            int port = Integer.parseInt(args[0]);
            Registry registry = LocateRegistry.createRegistry(port);
            ContadorPalabrasRemoto contador = new ContadorPalabrasServidor();
            registry.rebind("ContadorPalabras", contador);
            System.out.println("Servidor RMI iniciado en el puerto " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
