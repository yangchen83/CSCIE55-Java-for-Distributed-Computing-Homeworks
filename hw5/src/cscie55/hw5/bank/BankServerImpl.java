package cscie55.hw5.bank;

import java.util.LinkedList;
import java.util.Queue;

import cscie55.hw5.bank.command.Command;

public class BankServerImpl implements BankServer {
    //fields
    private final Bank bank;
    private final Queue<Command> commandQueue;
    private final int threadNumber;
    private final CommandExecutionThread[] commandExecutionThreads;
    
    //constructor
    public BankServerImpl(Bank bank, int threadNumber, Boolean executeCommandInsideMonitor){
        this.bank = bank;
        this.commandQueue = new LinkedList<Command>();
        this.threadNumber = threadNumber;
        this.commandExecutionThreads = new CommandExecutionThread[threadNumber];
        // initiate the threads
        for (int i = 0; i < threadNumber; i++) {
            commandExecutionThreads[i] = new CommandExecutionThread(bank, commandQueue, executeCommandInsideMonitor); 
        }
        // start all the threads
        for (CommandExecutionThread commandExecutionThread : commandExecutionThreads) {
            commandExecutionThread.start();
        }
    }

    @Override
    public void execute(Command command) {
        synchronized(commandQueue){
            commandQueue.add(command);
            commandQueue.notifyAll();
        }
    }

    @Override
    public void stop() throws InterruptedException {
        synchronized(commandQueue){
            // add a stop command for each thread
            for (int i = 0; i < threadNumber; i++){
                commandQueue.add(Command.stop());
            }
            commandQueue.notifyAll();
        }           
        for (CommandExecutionThread commandExecutionThread : commandExecutionThreads) {
            commandExecutionThread.join();
        }
    }

    @Override
    public long totalBalances() {
        return bank.totalBalances();
    }

}
