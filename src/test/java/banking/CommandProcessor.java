package banking;

public class CommandProcessor {
	public static final String CREATE = "create";
	public static final String DEPOSIT = "deposit";

	Bank bank;
	Savings savings;
	Checking checking;
	Cd cd;
	CreateProcessor createProcessor;
	DepositProcessor depositProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String command) {
		createProcessor = new CreateProcessor(bank);
		depositProcessor = new DepositProcessor(bank);

		String[] actual = command.split(" ");

		if (actual[0].equalsIgnoreCase(CREATE)) {
			createProcessor.create_process(command);
		}

		if (actual[0].equalsIgnoreCase(DEPOSIT)) {
			depositProcessor.deposit_process(command);
		}

	}

}
