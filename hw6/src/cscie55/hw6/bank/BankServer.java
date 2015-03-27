package cscie55.hw6.bank;

import java.io.IOException;
import java.net.ServerSocket;

public class BankServer {

    public static void main(String[] args) throws IOException {
        // Creating a new bank
        final Bank bank = new BankImpl();        
        System.out.println("Bank Initilized");
        
        // Creating a server socket
        final ServerSocket listener = new ServerSocket(SERVER_PORT);
        System.out.println("Bank Server Started");
        
        // Listening for connection
        try {
            while (true) {
                new CommandExecutionThread(listener.accept(), bank).start();
                System.out.println("A client is connected");
            }
        } finally {
            listener.close();
        }    
    }
    
    private static final int SERVER_PORT = 9090;
}
