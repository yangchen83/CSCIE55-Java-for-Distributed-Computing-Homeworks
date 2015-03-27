package cscie55.hw5.bank;

import java.util.Queue;

import cscie55.hw5.bank.command.Command;

public class CommandExecutionThread extends Thread{
    //fields
    private final Bank bank;
    private final Queue<Command> commandQueue;
    private final boolean executeCommandInsideMonitor;
 
    //constructor
    public CommandExecutionThread(Bank bank, Queue<Command> commandQueue, boolean executeCommandInsideMonitor){
        super();
        this.bank = bank;
        this.commandQueue = commandQueue;
        this.executeCommandInsideMonitor = executeCommandInsideMonitor;  
    }
    
    
    @Override
    public void run()
    {   boolean running = true;
        while (running) {
           Command command;
           synchronized(commandQueue){
               // put threads to sleep while the queue is empty
               while (commandQueue.size() == 0){
                   try {
                       commandQueue.wait();
                    } catch (InterruptedException e) { }
               }
               // get the command and remove it from the queue
               command = commandQueue.remove();
               //run command inside of monitor
               if (executeCommandInsideMonitor) {
                   if (command.isStop()) {
                       running = false; // stop the while loop
                   }
                   try {
                       command.execute(bank);
                   } catch (InsufficientFundsException e) { }  
                }
               commandQueue.notifyAll();
           } // end of synchronized
           
           //run command outside of monitor
           if (!executeCommandInsideMonitor) {
               if (command.isStop()) {
                   running = false; //stop the while loop
               }
               try {
                   command.execute(bank);
               } catch (InsufficientFundsException e) { }  
            }
       }
    }
}
