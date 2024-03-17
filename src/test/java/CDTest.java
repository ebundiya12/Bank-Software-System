import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {

	public static final String ID = "12345678";
	public static final String APR = "1.0";
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
		assertEquals(APR, Double.toString(cd.getApr()));
	}

	@Test
	void successfully_create_cd_account() {
		cd.create(bank, ID, APR, BALANCE);
		assertEquals(ID, bank.getAccounts().get(ID).getId());
	}
}
