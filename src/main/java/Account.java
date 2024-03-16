import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Account {

	public List<Account> all_accounts;
	protected double balance;
	protected double apr;
	protected String id;
	protected String type;
	DecimalFormat decimalFormat;

	Account(String id, String apr, String bal) {
		decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		this.balance = Double.parseDouble(decimalFormat.format(Double.parseDouble(bal)));
		this.apr = Double.parseDouble(apr);
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

	public void deposit(double amount) {
		this.balance += amount;
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

	public boolean openingBalance(double i) {
		return (i == 0);
	}

}
