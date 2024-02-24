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

	public boolean accountTypeDeposits(String q_id) {
		return (accounts.get(q_id).allowsDeposit());
	}

	public void account_deposit(String q_id, int amount) {
		accounts.get(q_id).deposit(amount);

	}

	public void account_withdrawal(String q_id, int amount) {
		accounts.get(q_id).withdraw(amount);
	}

	public boolean accountExistsById(String id) {
		return accounts.get(id) != null;
	}

	public boolean isDepositAmountInRange(String q_id, double amount) {
		return accounts.get(q_id).withinMaximumDeposit(amount);
	}

}
