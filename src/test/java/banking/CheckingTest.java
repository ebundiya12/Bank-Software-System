package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	public static final String ID = "12345678";
	public static final String APR = "1.0";
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
		assertEquals(APR, Double.toString(checking.getApr()));
	}

	@Test
	void savings_account_has_id() {
		assertEquals(ID, checking.getId());
	}

	@Test
	void checking_account_has_balance() {
		assertEquals(BALANCE, Double.toString(checking.getBalance()));
	}

	@Test
	void successfully_create_checking_account() {
		checking.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}

}
