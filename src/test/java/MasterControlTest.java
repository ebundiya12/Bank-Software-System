import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {
	public static final String INVALID_CREATE = "creat checking 12345678 1.0";
	public static final String INVALID_DEPOSIT = "depositt 12345678 100";
	public static final String VALID_CREATE = "create checking 12345678 1.0";

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

		assertSingleCommand(VALID_CREATE, actual);
	}

}
