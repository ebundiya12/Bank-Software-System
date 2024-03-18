package banking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bank {
	public Map<String, Account> accounts;

	Bank() {
		accounts = new LinkedHashMap<>();
	}

	public Map<String, Account> getAccounts() {
		return accounts;
	}

	public boolean accountExistsById(String id) {
		return accounts.get(id) != null;
	}

	public boolean accountTypeDeposits(String q_id) {
		return (accounts.get(q_id).allowsDeposit());
	}

	public boolean isDepositAmountInRange(String q_id, double i) {
		return accounts.get(q_id).withinMaximumDeposit(i);
	}

	public void accountDeposit(String q_id, String amt) {
		double amount = Double.parseDouble(amt);
		accounts.get(q_id).deposit(amount);
	}

	public boolean isWithdrawAmountInRange(String q_id, double amount) {
		return accounts.get(q_id).withinMaxWithdraw(amount);
	}

	public boolean isTransferAmountInRange(String id_1, String id_2, double amount) {
		boolean valid_id_1 = isWithdrawAmountInRange(id_1, amount);
		boolean valid_id_2 = isDepositAmountInRange(id_2, amount);

		return (valid_id_1 && valid_id_2);
	}

	public boolean accountTypeTransfers(String q_id) {
		return (accounts.get(q_id).allowsTransfer());
	}

	public void accountWithdrawal(String q_id, String amt) {
		double amount = Double.parseDouble(amt);
		accounts.get(q_id).withdraw(amount);
	}

	public void accountTransfer(String id_1, String id_2, String amt) {
		double amount = Double.parseDouble(amt);
		double actual = accounts.get(id_1).getBalance();

		accounts.get(id_1).withdraw(amount);

		accounts.get(id_2).deposit(Math.min(amount, actual));
	}

	public void remove_empty_accounts(List<Account> toRemove) {
		for (Account account : toRemove) {
			accounts.remove(account.getId());
		}
	}

	public void deduct_minimum_fee() {
		for (Map.Entry<String, Account> entry : accounts.entrySet()) {
			String q_id = entry.getValue().id;
			if (accounts.get(q_id).getBalance() < 100) {
				accounts.get(q_id).fee_collection();
			}
		}
	}

	public void calculateAccountsAPR() {
		for (Map.Entry<String, Account> entry : accounts.entrySet()) {
			String q_id = entry.getValue().id;
			accounts.get(q_id).calculate_apr();
		}
	}

	public void closeAccounts(List<Account> close) {
		for (Map.Entry<String, Account> entry : accounts.entrySet()) {
			String q_id = entry.getValue().id;
			if (accounts.get(q_id).getBalance() <= 0) {
				close.add(accounts.get(q_id));
			}
		}
	}

	public void passAccountsTime(int time) {
		List<Account> toRemove;
		toRemove = new ArrayList<>();

		while (time > 0) {

			closeAccounts(toRemove);
			deduct_minimum_fee();
			calculateAccountsAPR();
			update_withdraw_access();

			time -= 1;
		}
		remove_empty_accounts(toRemove);
	}

	private void update_withdraw_access() {
		for (Map.Entry<String, Account> entry : accounts.entrySet()) {
			String q_id = entry.getValue().id;
			if (accounts.get(q_id).type().equalsIgnoreCase("savings")) {
				accounts.get(q_id).withdrawn_month = false;
			}
			if (accounts.get(q_id).type().equalsIgnoreCase("cd")) {
				accounts.get(q_id).total_time += 1;
			}
		}
	}

	public boolean accountCanWithdrawNow(String q_id) {
		return (accounts.get(q_id).can_withdraw_this_month());
	}

	public void update_history(String id, String command) {
		accounts.get(id).transaction_history.add(command);
	}

}
