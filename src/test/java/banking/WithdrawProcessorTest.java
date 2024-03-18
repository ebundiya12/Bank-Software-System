package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {

	public static final String withdraw_input = "withdraw 12345678 200";
	public static final String ID_1 = "12345678";
	public static final String APR = "1.0";
	public static final String ZERO = "0";

	Bank bank;
	CommandProcessor commandProcessor;
	Checking checking;
	Savings savings;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(ID_1, APR, ZERO);
		savings = new Savings(ID_1, APR, ZERO);
	}

	@Test
	void withdraw_command_updates_account_balance_with_existing_money_for_checking() {
		checking.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 300");
		commandProcessor.process(withdraw_input);
		assertEquals(100, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void withdraw_amount_equal_to_checking_account_balance_updates_account_balance_to_zero() {
		checking.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 200");
		commandProcessor.process(withdraw_input);
		assertEquals(0, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void withdraw_more_than_checking_accounts_balance_updates_the_balance_to_zero() {
		checking.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 100");
		commandProcessor.process(withdraw_input);
		assertEquals(0, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void withdraw_command_updates_account_balance_with_existing_money_for_savings() {
		savings.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 300");
		commandProcessor.process(withdraw_input);
		assertEquals(100, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void withdraw_amount_equal_to_savings_account_balance_updates_account_balance_to_zero() {
		savings.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 200");
		commandProcessor.process(withdraw_input);
		assertEquals(0, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void withdraw_more_than_savings_accounts_balance_updates_the_balance_to_zero() {
		savings.create(bank, ID_1, APR, ZERO);
		commandProcessor.process("deposit 12345678 100");
		commandProcessor.process(withdraw_input);
		assertEquals(0, bank.getAccounts().get(ID_1).getBalance());
	}

}
