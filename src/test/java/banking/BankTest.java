package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final String ID = "12345678";
	public static final String APR = "1%";
	public static final String BALANCE = "0";
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
	void add_three_accounts_to_bank() {
		savings.create(bank, ID, APR, BALANCE);
		checking.create(bank, ID + "1", APR, BALANCE);
		cd.create(bank, ID + "2", APR, BALANCE);
		assertEquals(ID + "2", bank.getAccounts().get(ID + "2").getId());
	}

	@Test
	void can_deposit_to_accounts_through_the_bank() {
		savings.create(bank, ID, APR, BALANCE);
		bank.account_deposit(ID, 100);
		assertEquals("100.0", bank.getAccounts().get(ID).getBalance());

	}

	@Test
	void can_withdraw_from_accounts_through_bank() {
		checking.create(bank, ID, APR, BALANCE);
		bank.account_deposit(ID, 100);
		bank.account_withdrawal(ID, 50);
		assertEquals("50.0", bank.getAccounts().get(ID).getBalance());
	}

}
