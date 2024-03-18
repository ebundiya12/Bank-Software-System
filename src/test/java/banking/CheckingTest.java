package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final String ID = "12345678";
	public static final String APR = "1%";
	public static final String BALANCE = "0.0";
	public static final String TYPE = "checking";
	Checking checking;
	Bank bank;

	@BeforeEach
	void setUp() {
		checking = new Checking(ID, APR, BALANCE);
		bank = new Bank();
	}

	@Test
	void checking_account_has_type() {
		assertEquals(TYPE, checking.type());
	}

	@Test
	void checking_account_has_apr() {
		assertEquals(APR, checking.getApr());
	}

	@Test
	void savings_account_has_id() {
		assertEquals(ID, checking.getId());
	}

	@Test
	void checking_account_has_balance() {
		assertEquals(BALANCE, checking.getBalance());
	}

	@Test
	void created_checking_account_is_stored_in_the_bank() {
		checking.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}

	@Test
	void checking_deposit_deposits_correct_amount_to_account() {
		checking.deposit(100);
		assertEquals("100.0", checking.getBalance());

	}

	@Test
	void checking_withdraw_is_correct_when_there_is_enough_money_in_account() {
		checking.deposit(100);
		checking.withdraw(50);
		assertEquals("50.0", checking.getBalance());

	}

	@Test
	void checking_cannot_withdraw_more_than_amount_in_account() {
		checking.withdraw(50);
		assertEquals(BALANCE, checking.getBalance());
	}

}
