public class Cd extends Account {

	Cd(String id, String apr, String balance) {
		super(id, apr, balance);

	}

	public void create(Bank bank, String id, String apr, String balance) {
		all_accounts.add(new Cd(id, apr, balance));
		bank.accounts.put(id, new Cd(id, apr, balance));
	}

	@Override
	public double getApr() {
		return apr;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	String type() {
		return "cd";
	}

	@Override
	public boolean allowsDeposit() {
		return false;
	}

	@Override
	public boolean withinOpeningBalance(double parseDouble) {
		return (parseDouble >= 1000 && parseDouble <= 10000);
	}

	@Override
	public boolean openingBalance(double i) {
		return (i >= 1000 && i <= 10000);
	}
}
