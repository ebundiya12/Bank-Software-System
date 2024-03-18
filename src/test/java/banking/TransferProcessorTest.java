package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {

	public static final String transfer_input = "transfer 12345678 98765432 50";
	public static final String deposit_input = "deposit 12345678 100";
	public static final String ID_1 = "12345678";
	public static final String ID_2 = "98765432";
	public static final String APR = "1.0";
	public static final String ZERO = "0";

	Bank bank;
	CommandProcessor commandProcessor;
	Checking checking;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(ID_1, APR, ZERO);
	}

	@Test
	void transfer_to_an_account_updates_the_from_accounts_balance() {
		checking.create(bank, ID_1, APR, ZERO);
		checking.create(bank, ID_2, APR, ZERO);
		commandProcessor.process(deposit_input);
		commandProcessor.process(transfer_input);
		assertEquals(50, bank.getAccounts().get(ID_1).getBalance());
	}

	@Test
	void transfer_to_an_account_updates_the_receiving_accounts_balance() {
		checking.create(bank, ID_1, APR, ZERO);
		checking.create(bank, ID_2, APR, ZERO);
		commandProcessor.process(deposit_input);
		commandProcessor.process(transfer_input);
		assertEquals(50, bank.getAccounts().get(ID_2).getBalance());
	}

	@Test
	void transfer_more_than_to_accounts_balance_only_deposits_actual_money_withdrawn_to_receiving_account() {
		checking.create(bank, ID_1, APR, ZERO);
		checking.create(bank, ID_2, APR, ZERO);
		commandProcessor.process(deposit_input);
		commandProcessor.process("transfer 12345678 98765432 150");
		assertEquals(100, bank.getAccounts().get(ID_2).getBalance());
	}
}
