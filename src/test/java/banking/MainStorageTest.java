package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainStorageTest {

	Bank bank;
	MainStorage mainStorage;
	Savings savings;
	CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		mainStorage = new MainStorage(bank);
		savings = new Savings("12345678", "1", "0");
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void invalid_storage_is_initially_empty() {
		List<String> actual = mainStorage.get_invalid_commands();
		assertEquals(0, actual.size());
	}

	@Test
	void output_list_is_initially_empty() {
		List<String> actual = mainStorage.get_stored_output_list();
		assertEquals(0, actual.size());
	}

	@Test
	void typo_in_deposit_command_is_added_to_invalid_storage() {
		String inv_deposit = "depositt 12345678 600";
		mainStorage.store_invalid_command(inv_deposit);
		List<String> actual = mainStorage.get_invalid_commands();
		assertEquals(inv_deposit, actual.get(0));
	}

	@Test
	void invalid_deposit_command_is_added_to_invalid_storage() {
		String inv_deposit = "depositt 12345678";
		mainStorage.store_invalid_command(inv_deposit);
		List<String> actual = mainStorage.get_invalid_commands();
		assertEquals(inv_deposit, actual.get(0));
	}

	@Test
	void invalid_create_command_is_added_to_invalid_storage() {
		String inv_create = "create savings 1234567 50";
		mainStorage.store_invalid_command(inv_create);
		List<String> actual = mainStorage.get_invalid_commands();
		assertEquals(inv_create, actual.get(0));

	}

	@Test
	void output_list_stores_commands_in_order_of_entry() {
		String invalid_input = "create savings 1234567 50";
		mainStorage.store_invalid_command(invalid_input);
		mainStorage.store_invalid_command("created checking");
		List<String> actual = mainStorage.get_invalid_commands();
		assertEquals(invalid_input, actual.get(0));
	}

	@Test
	void output_list_stores_account_status_and_transaction_history_before_invalid_commands_regardless_of_if_the_invalid_command_was_input_first() {
		mainStorage.store_invalid_command("withdrew 12345678 50");
		commandProcessor.process("create savings 12345678 1");
		commandProcessor.process("deposit 12345678 100");
		List<String> actual = mainStorage.get_stored_output_list();
		assertEquals("Savings 12345678 100.00 1.00", actual.get(0));
		assertEquals("withdrew 12345678 50", actual.get(2));
	}
}
