import java.util.ArrayList;
import java.util.List;

class Account {

	public List<Account> all_accounts;
	protected String balance;
	protected String apr;
	protected String id;
	protected String type;

	Account(String id, String apr, String balance) {
		this.balance = balance;
		this.apr = apr;
		this.id = id;
		all_accounts = new ArrayList<>();
	}

	static Account savings(String id, String apr, String balance) {
		return new Savings(id, apr, balance);
	}

	static Account checking(String id, String apr, String balance) {
		return new Checking(id, apr, balance);
	}

	static Account cd(String id, String apr, String balance) {
		return new Checking(id, apr, balance);
	}

	String type() {
		return type;
	}

	public String getApr() {
		return apr;
	}

	String getBalance() {
		return balance;
	}

	public String getId() {
		return id;
	}

	public void deposit(int amount) {
		double bal = Double.parseDouble(balance);
		bal += amount;
		balance = Double.toString(bal);

	}

	public void withdraw(int amount) {
		double bal = Double.parseDouble(balance);
		if (amount <= bal) {
			bal = bal - amount;
			balance = Double.toString(bal);
		} else {
			bal = 0;
			balance = Double.toString(bal);
			System.err.println("withdraw amount too high");
		}

	}

	public boolean allowsDeposit() {
		return true;
	}

	public boolean withinMaximumDeposit(double amount) {
		return false;
	}

	public boolean withinOpeningBalance(double parseDouble) {
		return (parseDouble == 0);
	}
}
