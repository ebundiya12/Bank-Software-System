package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ID = "12345678";
	public static final String APR = "1.0";
	public static final String BALANCE = "0.0";
	Bank bank;
	Savings savings;
	Cd cd;
	Checking checking;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		checking = new Checking(ID, APR, BALANCE);
		savings = new Savings(ID, APR, BALANCE);
		cd = new Cd(ID, APR, BALANCE);
	}

	@Test
	void Bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void add_an_account_to_bank() {
		checking.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}

	@Test
	void add_two_savings_accounts_to_bank() {
		savings.create(bank, ID, APR, BALANCE);
		savings.create(bank, ID + "1", APR, BALANCE);
		assertEquals(ID + "1", bank.getAccounts().get(ID + "1").getId());
	}

	@Test
	void add_three_different_accounts_to_bank() {
		savings.create(bank, ID, APR, BALANCE);
		checking.create(bank, ID + "1", APR, BALANCE);
		cd.create(bank, ID + "2", APR, BALANCE);
		assertEquals(ID + "2", bank.getAccounts().get(ID + "2").getId());
	}

	@Test
	void can_deposit_into_an_account_through_bank() {
		checking.create(bank, ID, APR, BALANCE);
		bank.accountDeposit(ID, "400");
		assertEquals(400.00, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void can_withdraw_from_an_account_through_bank() {
		checking.create(bank, ID, APR, "400");
		bank.accountWithdrawal(ID, "100");
		assertEquals(300.00, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void can_confirm_if_an_account_allows_transfers_through_bank() {
		cd.create(bank, ID, APR, "500");
		boolean actual = bank.accountTypeTransfers(ID);
		assertFalse(actual);
	}

	@Test
	void can_transfer_between_accounts_through_bank() {
		String ID_2 = "98765432";
		savings.create(bank, ID_2, APR, "500");
		checking.create(bank, ID, APR, BALANCE);
		bank.accountTransfer(ID_2, ID, "400");
		assertEquals(400.00, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void can_pass_time_of_accounts_through_bank() {
		checking.create(bank, ID, APR, "400");
		bank.passAccountsTime(1);
		assertEquals(400.33, bank.getAccounts().get(ID).getBalance());
	}

}
