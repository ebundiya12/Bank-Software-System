package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

abstract class Account {

	public boolean withdrawn_month;
	public int total_time = 0;
	public List<String> transaction_history;
	protected double balance;
	protected double apr;
	protected String id;
	DecimalFormat decimalFormat;

	Account(String id, String apr, String bal) {
		decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		this.balance = Double.parseDouble(decimalFormat.format(Double.parseDouble(bal)));
		this.apr = Double.parseDouble(apr);
		this.id = id;
		transaction_history = new ArrayList<>();
	}

	public boolean openingBalance(double i) {
		return (i == 0);
	}

	public void withdraw(double amount) {
		if (amount <= balance) {
			balance = balance - amount;
		} else {
			balance = 0;
		}
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public String type() {
		return "Savings";
	}

	public double getApr() {
		return apr;
	}

	double getBalance() {
		balance = Double.parseDouble(decimalFormat.format(balance));
		return balance;
	}

	public String getId() {
		return id;
	}

	public boolean withinMaximumDeposit(double amount) {
		return false;
	}

	public boolean withinMaxWithdraw(double amount) {
		double max_withdraw = getBalance();
		return (amount >= max_withdraw);
	}

	public boolean allowsDeposit() {
		return true;
	}

	public boolean allowsTransfer() {
		return true;
	}

	public void calculate_apr() {
		double account_apr = (getApr() / 100) / 12;
		account_apr *= balance;
		balance += account_apr;
		balance = Double.parseDouble(decimalFormat.format(balance));
	}

	public void fee_collection() {
		if (balance <= 25) {
			balance = 0;
		} else {
			balance -= 25;
		}
	}

	public boolean can_withdraw_this_month() {
		return true;
	}

	public void get_account_status() {
		String account_type = type();
		String bal = decimalFormat.format(getBalance());
		String account_apr = decimalFormat.format(getApr());
		String account_id = getId();
		String status = account_type + " " + account_id + " " + bal + " " + account_apr;

		transaction_history.set(0, status);
	}

	public List<String> get_Transaction_history() {
		get_account_status();
		return transaction_history;
	}
}
