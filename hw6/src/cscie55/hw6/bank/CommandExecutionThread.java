package cscie55.hw6.bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import cscie55.hw6.bank.command.Command;

public class CommandExecutionThread extends Thread {
    // fields
    private final Socket socket;
    private final Bank bank;
    
    // constructor
    CommandExecutionThread(Socket socket, Bank bank) {
        this.socket = socket;
        this.bank = bank;
    }
    
    public void run(){
        PrintWriter out = null;
        try {
            // getting client input
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // output back to client
            out = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                String input = in.readLine();
                // break the loop if there is no more input
                if (input == null || input.equals(".")) {
                    break;
                }
                // parse input into command and execute it
                Command cmd = Command.parse(input);
                String output = cmd.execute(bank);
                if (output == null) {
                    output = "";
                }
                out.println (output);
            }           
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InsufficientFundsException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }
}
