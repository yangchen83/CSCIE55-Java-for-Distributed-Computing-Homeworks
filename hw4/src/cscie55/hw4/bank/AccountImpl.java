package cscie55.hw4.bank;

public class AccountImpl implements Account{
    // fields
    private final int id;
    private long balance;
    
    // constructor
    public AccountImpl(int id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account that = (Account) object;
        return this.id == that.id();
    }
    
    @Override
    public int id() {
        return id;
    }

    @Override
    public long balance() {
        return balance;
    }

    @Override
    public void deposit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        } else {
            balance = balance + amount;    
        }
    }

    @Override
    public void withdraw(long amount) throws InsufficientFundsException {
        if (amount <=0) {
            throw new IllegalArgumentException();
        }
        if (balance < amount) {
            throw new InsufficientFundsException(this, amount);
        }
        balance = balance - amount;
    }

}
