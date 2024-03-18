package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {

	public static final String time_input = "pass 1";
	public static final String ID_1 = "12345678";
	public static final String ID_2 = "98765432";
	public static final String APR = "1.0";
	public static final String ZERO = "0";

	Bank bank;
	CommandProcessor commandProcessor;
	Checking checking;
	Savings savings;
	Cd cd;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(ID_1, APR, ZERO);
		savings = new Savings(ID_1, APR, ZERO);
		cd = new Cd(ID_2, APR, ZERO);
	}

	@Test
	void pass_time_command_adds_apr_to_accounts_with_balance_above_0() {
		savings.create(bank, ID_1, "0.6", ZERO);
		commandProcessor.process("deposit 12345678 500");
		commandProcessor.process(time_input);
		assertEquals(500.25, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void pass_time_removes_accounts_with_zero_balance() {
		checking.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("pass 1");
		boolean actual = bank.accountExistsById(ID_1);
		assertFalse(actual);
	}

	@Test
	void pass_time_does_not_remove_accounts_with_zero_balance_just_after_the_fee_has_been_collected() {
		savings.create(bank, ID_1, APR, "25");
		commandProcessor.process("pass 1");
		boolean actual = bank.accountExistsById(ID_1);
		assertTrue(actual);
	}

	@Test
	void pass_time_deducts_a_25_fee_from_accounts_with_balance_less_than_100() {
		savings.create(bank, ID_1, "0.6", ZERO);
		commandProcessor.process("deposit 12345678 60");
		commandProcessor.process(time_input);
		assertEquals(35.01, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void pass_time_updates_cd_account_apr_differently_than_other_account_types() {
		savings.create(bank, ID_1, "0.6", ZERO);
		cd.create(bank, ID_2, "0.6", "500");
		commandProcessor.process("deposit 12345678 500");
		commandProcessor.process(time_input);
		assertNotEquals(bank.getAccounts().get(ID_1).getBalance(), bank.getAccounts().get(ID_2).getBalance());
	}

}
