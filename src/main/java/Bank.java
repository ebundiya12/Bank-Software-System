import java.util.HashMap;
import java.util.Map;

public class Bank {
    public Map<String, Account> accounts;


    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;

    }

    public void account_deposit(String q_id, int amount) {
        accounts.get(q_id).deposit(amount);

    }

    public void account_withdrawal(String q_id, int amount) {
        accounts.get(q_id).withdraw(amount);
    }
}
