package banking;

import java.util.ArrayList;
import java.util.List;

public class MainStorage {

	public List<String> invalid_storage;
	Bank bank;

	public MainStorage(Bank bank) {
		this.bank = bank;
		invalid_storage = new ArrayList<>();
	}

	public void store_invalid_command(String command) {
		invalid_storage.add(command);
	}

	public List<String> get_invalid_commands() {
		return invalid_storage;
	}
}
