package cscie55.hw6.bank;

import java.util.ArrayList;
import java.util.List;

public class BankImpl implements Bank {
    // fields
    private final List<Account> accounts;
    
    // constructor
    public BankImpl() {
        accounts = new ArrayList<Account>();
    }

    @Override
    public void addAccount(Account account) throws DuplicateAccountException {
        if (accounts.contains(account)) {
            throw new DuplicateAccountException(account.id());
        }
        accounts.add(account);
    }



    @Override
    public void transfer(int fromId, int toId, long amount)
            throws InsufficientFundsException {
        if (amount <=0) {
            throw new IllegalArgumentException();
        }
        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);
        synchronized (fromAccount){
            try {
                fromAccount.withdraw(amount);
            } catch (InsufficientFundsException e ) {
                return; //exit method if insufficient funds
            }
        }
        synchronized (toAccount){
            try {
                toAccount.deposit(amount);
            } catch (Exception e) {
                // if the method executed to here , the amount should be > 0
                // but if other exception happens, the money need to go back to fromAccount
                fromAccount.deposit(amount);
            }
        }
    }

    @Override
    public long totalBalances() {
        long balance = 0L;
        for (Account a : accounts) {
            balance = balance + a.balance();
        }
        return balance;
    }

    public int numberOfAccounts() {
        return accounts.size();
    }

    @Override
    public void deposit(int accountId, long amount) {
        accounts.get(accountId).deposit(amount);
    }

    @Override
    public void deleteAllAccounts() {
        accounts.clear();
    }

}
