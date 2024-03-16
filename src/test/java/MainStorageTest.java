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
}
