public class Savings extends Account {


    Savings(String id, String apr, String balance) {
        super(id, apr, balance);

    }

    public void create(Bank bank, String id, String apr, String balance) {
        all_accounts.add(new Savings(id, apr, balance));
        bank.accounts.put(id, new Savings(id, apr, balance));
    }


    @Override
    public String getApr() {
        return apr;
    }

    @Override
    String getBalance() {
        return balance;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    String type() {
        return "savings";
    }


}
