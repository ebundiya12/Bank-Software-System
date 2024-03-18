package banking;

public class Cd extends Account {

    Cd(String id, String apr, String bal) {
        super(id, apr, bal);
    }

    public void create(Bank bank, String id, String apr, String balance) {
        bank.accounts.put(id, new Cd(id, apr, balance));
    }

    @Override
    public void deposit(double amount) {
    }

    @Override
    public void withdraw(double amount) {
        balance = 0;
    }

    @Override
    public boolean can_withdraw_this_month() {
        return (total_time >= 12);
    }

    @Override
    public String type() {
        return "Cd";
    }

    @Override
    public boolean openingBalance(double i) {
        return (i >= 1000 && i <= 10000);
    }

    @Override
    public boolean allowsDeposit() {
        return false;
    }

    @Override
    public boolean allowsTransfer() {
        return false;
    }

    @Override
    public void calculate_apr() {
        double account_apr = (getApr() / 100) / 12;
        account_apr *= balance;

        int i = 0;
        while (i < 4 && i >= 0) {
            balance += account_apr;
            i += 1;
        }
    }


}
