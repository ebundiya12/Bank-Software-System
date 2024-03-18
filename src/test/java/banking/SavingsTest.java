package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
	public static final String ID = "12345678";
	public static final String APR = "1%";
	public static final String BALANCE = "0.0";
	public static final String TYPE = "savings";
	Savings savings;
	Bank bank;

	@BeforeEach
	void setUp() {
		savings = new Savings(ID, APR, BALANCE);
		bank = new Bank();
	}

	@Test
	void savings_account_has_type() {
		assertEquals(TYPE, savings.type());
	}

	@Test
	void savings_account_has_apr() {
		assertEquals(APR, savings.getApr());
	}

	@Test
	void savings_account_has_id() {
		assertEquals(ID, savings.getId());
	}

	@Test
	void savings_account_has_balance() {
		assertEquals(BALANCE, savings.getBalance());
	}

	@Test
	void created_savings_account_is_stored_in_the_bank() {
		savings.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}

	@Test
	void savings_deposit_adds_accurate_amount_to_account_balance() {
		savings.deposit(100);
		assertEquals("100.0", savings.getBalance());

	}

	@Test
	void savings_withdraw_is_accurate_when_there_is_enough_money_in_account() {
		savings.deposit(100);
		savings.withdraw(50);
		assertEquals("50.0", savings.getBalance());

	}

	@Test
	void savings_cannot_withdraw_more_than_amount_in_account() {
		savings.withdraw(50);
		assertEquals(BALANCE, savings.getBalance());
	}

}
