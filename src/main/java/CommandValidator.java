public class CommandValidator {

	protected final Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		CreateValidator createValidator = new CreateValidator(bank);
		DepositValidator depositValidator = new DepositValidator(bank);

		String[] words = command.split(" ");

		if (words[0].equalsIgnoreCase("create")) {
			return (createValidator.validate(command));
		}
		if (words[0].equalsIgnoreCase("deposit")) {
			return (depositValidator.validate(command));
		}
		return false;

	}

	public boolean validate_id(String command) {
		return (command.length() == 8);
	}

	public boolean is_existing_id(String id) {
		return (bank.accountExistsById(id));
	}

	public boolean validate_apr(String command) {
		double apr = Double.parseDouble(command);
		return (apr >= 0 && apr <= 10);
	}

	public boolean validate_type(String type_String) {
		return (type_String.equalsIgnoreCase("Savings") || type_String.equalsIgnoreCase("checking")
				|| type_String.equalsIgnoreCase("cd"));
	}

	public boolean validate_deposit_type(String q_id) {
		return bank.accountTypeDeposits(q_id);
	}
}
