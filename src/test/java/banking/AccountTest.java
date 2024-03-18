package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	public static final String ID = "12345678";
	public static final String APR = "1.0";
	public static final String BALANCE = "0.0";
	Cd cd;
	Savings savings;
	Checking checking;

	@BeforeEach
	void setUp() {
		cd = new Cd(ID, APR, BALANCE);
		savings = new Savings(ID, APR, BALANCE);
		checking = new Checking(ID, APR, BALANCE);
	}

	@Test
	void account_initially_has_no_money() {
		assertEquals(BALANCE, Double.toString(savings.getBalance()));
	}

	@Test
	void deposit_updates_balance() {
		savings.deposit(100);
		assertEquals(100.00, savings.getBalance());
	}

	@Test
	void withdrawal_updates_balance() {
		checking.deposit(100);
		checking.withdraw(50);
		assertEquals(50, checking.getBalance());
	}

	@Test
	void withdrawal_can_not_go_below_zero() {
		savings.deposit(100);
		savings.withdraw(200);
		assertEquals(0, savings.getBalance());
	}

	@Test
	void withdrawal_of_same_amount_in_account_sets_balance_to_zero() {
		savings.deposit(100);
		savings.withdraw(100);
		assertEquals(0, savings.getBalance());
	}

	@Test
	void account_apr_calculation_updates_account_balance() {
		savings.deposit(100);
		savings.calculate_apr();
		assertNotEquals(100, savings.getBalance());
	}

	@Test
	void account_fee_collection_updates_account_balance() {
		checking.deposit(100);
		checking.fee_collection();
		assertEquals(75, checking.getBalance());
	}

	@Test
	void account_fee_collection_cannot_make_balance_less_than_zero() {
		savings.deposit(25);
		savings.fee_collection();
		assertEquals(0, savings.getBalance());
	}

	@Test
	void account_with_less_than_25_would_still_have_zero_balance_after_fees() {
		checking.deposit(10);
		checking.fee_collection();
		assertEquals(0, checking.getBalance());
	}

}
