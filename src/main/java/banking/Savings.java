package banking;

public class Savings extends Account {

    Savings(String id, String apr, String balance) {
        super(id, apr, balance);

    }

    public void create(Bank bank, String id, String apr, String balance) {
        bank.accounts.put(id, new Savings(id, apr, balance));
    }

    @Override
    public boolean withinMaximumDeposit(double amount) {
        double max_deposit = 2500;
        return (amount <= max_deposit && amount >= 0);
    }

    @Override
    public boolean withinMaxWithdraw(double amount) {
        double max_withdraw = 1000;
        return (amount <= max_withdraw && amount >= 0);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
        } else {
            balance = 0;
        }
        withdrawn_month = true;
    }

    @Override
    public boolean can_withdraw_this_month() {
        return (!withdrawn_month);
    }

}
