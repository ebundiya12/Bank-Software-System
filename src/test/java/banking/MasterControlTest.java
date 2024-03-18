package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	public static final String INVALID_CREATE = "creat checking 12345678 1.0";
	public static final String INVALID_CREATE_2 = "create savings 12345678 1.0 50";
	public static final String INVALID_DEPOSIT = "depositt 12345678 100";
	public static final String VALID_CREATE = "create checking 12345678 1.0";
	public static final String INVALID_CD = "create cd 12345678 1.0";

	MasterControl masterControl;
	private List<String> input;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(bank, new CommandValidator(bank), new CommandProcessor(bank),
				new MainStorage(bank));
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {

		input.add(INVALID_CREATE);

		List<String> actual = masterControl.start(input);

		assertSingleCommand(INVALID_CREATE, actual);
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		input.add(INVALID_DEPOSIT);

		List<String> actual = masterControl.start(input);

		assertSingleCommand(INVALID_DEPOSIT, actual);
	}

	@Test
	void typo_in_withdraw_command_is_invalid() {
		String invalid = "withdrawal 98765432 70";
		input.add(invalid);

		List<String> actual = masterControl.start(input);

		assertSingleCommand(invalid, actual);
	}

	@Test
	void typo_in_transfer_command_is_invalid() {
		String invalid = "transer 12345678 98765432 70";
		input.add(invalid);

		List<String> actual = masterControl.start(input);

		assertSingleCommand(invalid, actual);
	}

	@Test
	void typo_in_pass_time_command_is_invalid() {
		String invalid = "Pas 2";
		input.add(invalid);
		List<String> actual = masterControl.start(input);

		assertSingleCommand(invalid, actual);
	}

	@Test
	void two_typo_commands_are_both_invalid() {
		input.add(INVALID_CREATE);
		input.add(INVALID_DEPOSIT);

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals(INVALID_CREATE, actual.get(0));
		assertEquals(INVALID_DEPOSIT, actual.get(1));
	}

	@Test
	void invalid_to_create_accounts_with_same_ID() {
		input.add(VALID_CREATE);
		input.add(VALID_CREATE);

		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
	}

	@Test
	void creating_cd_account_without_balance_is_invalid() {
		input.add(INVALID_CD);
		List<String> actual = masterControl.start(input);

		assertSingleCommand(INVALID_CD, actual);
	}

	@Test
	void creating_savings_or_checking_with_opening_balance_is_invalid() {
		input.add(INVALID_CREATE_2);
		List<String> actual = masterControl.start(input);

		assertSingleCommand(INVALID_CREATE_2, actual);
	}

	@Test
	void created_account_status_is_output() {
		input.add("create savings 12345678 0.6");
		List<String> actual = masterControl.start(input);

		assertSingleCommand("Savings 12345678 0.00 0.60", actual);
	}

	@Test
	void invalid_inputs_are_output_last() {
		input.add("create 1234567 0.6");
		input.add("create cd 12345678 1.2 1000");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Cd 12345678 1000.00 1.20", actual.get(0));
		assertEquals("create 1234567 0.6", actual.get(1));
	}

	@Test
	void accounts_are_output_in_the_order_of_creation() {
		input.add("Create checking 12345678 0.6");
		input.add("create Savings 93456789 0.5");
		input.add("Create cd 12345679 0.6 2000");
		input.add("Create savings 93456782 0.6");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Checking 12345678 0.00 0.60", actual.get(0));
		assertEquals("Savings 93456789 0.00 0.50", actual.get(1));
		assertEquals("Cd 12345679 2000.00 0.60", actual.get(2));
		assertEquals("Savings 93456782 0.00 0.60", actual.get(3));
	}

	@Test
	void a_transaction_involving_two_accounts_will_be_in_the_transaction_history_of_both_accounts() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Create checking 98765432 0.6");
		input.add("transfer 12345678 98765432 200");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 500.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("transfer 12345678 98765432 200", actual.get(2));
		assertEquals("Checking 98765432 200.00 0.60", actual.get(3));
		assertEquals("transfer 12345678 98765432 200", actual.get(4));
	}

	@Test
	void account_status_and_transaction_history_are_output() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("wiThdraw 12345678 200");
		input.add("pass 1");
		input.add("withdraw 12345678 800");

		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("wiThdraw 12345678 200", actual.get(2));
		assertEquals("withdraw 12345678 800", actual.get(3));

	}

	@Test
	void invalid_commands_are_output_last_regardless_of_if_they_were_input_first() {
		input.add("deposit 12345678 100");
		input.add("Create cd 12345678 0.6 2000");
		input.add("Deposit 12345678 700");
		input.add("wiThdraw 12345678 2000");
		input.add("pass 13");
		input.add("withdraw 12345678 2052.57");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Cd 12345678 0.00 0.60", actual.get(0));
		assertEquals("withdraw 12345678 2052.57", actual.get(1));
		assertEquals("deposit 12345678 100", actual.get(2));
		assertEquals("Deposit 12345678 700", actual.get(3));
		assertEquals("wiThdraw 12345678 2000", actual.get(4));
	}

	@Test
	void account_status_is_unchanged_if_all_attempted_transactions_on_the_account_are_invalid() {
		input.add("Create cd 12345678 0.6 2000");
		input.add("create checking 12345679 0.6");
		input.add("transfer 12345678 12345679 100");
		input.add("withdraw 12345678 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Cd 12345678 2000.00 0.60", actual.get(0));
		assertEquals("Checking 12345679 0.00 0.60", actual.get(1));
		assertEquals("transfer 12345678 12345679 100", actual.get(2));
		assertEquals("withdraw 12345678 2000", actual.get(3));
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

}
