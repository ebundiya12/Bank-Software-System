package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainStorage {

	public List<String> invalid_storage;
	public List<String> output_list;
	Bank bank;

	public MainStorage(Bank bank) {
		this.bank = bank;
		invalid_storage = new ArrayList<>();
		output_list = new ArrayList<>();
	}

	public void store_invalid_command(String command) {
		invalid_storage.add(command);
	}

	public void store_account_transactions() {
		for (Map.Entry<String, Account> entry : bank.getAccounts().entrySet()) {
			String q_id = entry.getValue().id;
			List<String> new_l = bank.getAccounts().get(q_id).get_Transaction_history();
			output_list.addAll(new_l);
		}
	}

	public void merge_stored_commands() {
		output_list.addAll(invalid_storage);
	}

	public List<String> get_invalid_commands() {
		return invalid_storage;
	}

	public List<String> get_stored_output_list() {
		store_account_transactions();
		merge_stored_commands();
		return output_list;
	}
}
