import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
