package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {

	public static final String ID = "12345678";
	public static final String APR = "1%";
	public static final String BALANCE = "0.0";
	public static final String TYPE = "cd";
	Cd cd;
	Bank bank;

	@BeforeEach
	void setUp() {
		cd = new Cd(ID, APR, BALANCE);
		bank = new Bank();
	}

	@Test
	void cd_account_has_type() {
		assertEquals(TYPE, cd.type());
	}

	@Test
	void savings_account_has_id() {
		assertEquals(ID, cd.getId());
	}

	@Test
	void cd_account_has_apr() {
		assertEquals(APR, cd.getApr());
	}

	@Test
	void cd_account_has_initial_balance() {
		assertEquals(BALANCE, cd.getBalance());
	}

	@Test
	void created_cd_account_is_stored_in_the_bank() {
		cd.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}

	@Test
	void cd_deposit_deposits_correct_amount_to_account() {
		cd.deposit(100);
		assertEquals("100.0", cd.getBalance());

	}

	@Test
	void cd_withdraw_is_correct_when_there_is_enough_money_in_account() {
		cd.deposit(100);
		cd.withdraw(50);
		assertEquals("50.0", cd.getBalance());

	}

	@Test
	void cd_cannot_withdraw_more_than_amount_in_account() {
		cd.withdraw(50);
		assertEquals(BALANCE, cd.getBalance());
	}
}
