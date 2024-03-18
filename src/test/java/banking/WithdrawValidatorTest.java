package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {

	public static final String ID = "12345678";
	public static final String APR = "1";
	public static final String BALANCE = "0";
	public static final String MIXED_WITHDRAW_STRING = "WithdRaw 12345678 200";
	public static final String MISSING_WITHDRAW_STRING = "Withdraw 12345678";

	WithdrawValidator withdrawValidator;
	CommandValidator commandValidator;
	CommandProcessor commandProcessor;
	Bank bank;
	Savings savings;
	Checking checking;
	Cd cd;

	@BeforeEach
	void setup() {
		bank = new Bank();

		commandValidator = new CommandValidator(bank);
		withdrawValidator = new WithdrawValidator(bank);
		commandProcessor = new CommandProcessor(bank);
		savings = new Savings(ID, APR, BALANCE);
		checking = new Checking(ID, APR, BALANCE);
		cd = new Cd(ID, APR, BALANCE);
	}

	@Test
	void withdraw_string_is_case_insensitive() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertTrue(actual);
	}

	@Test
	void typo_in_withdraw_command_is_invalid() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdrw 12345678 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_command_with_missing_argument_is_invalid() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate(MISSING_WITHDRAW_STRING);
		assertFalse(actual);
	}

	@Test
	void withdraw_zero_amount_is_valid() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 0");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_less_than_zero_is_invalid() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 -5");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_more_than_account_balance_is_valid_for_checking() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_equal_to_account_balance_is_valid_for_checking() {
		checking.create(bank, ID, APR, "200");
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_more_than_account_balance_is_valid_for_savings() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_equal_to_account_balance_is_valid_for_savings() {
		savings.create(bank, ID, APR, "200");
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_less_than_withdraw_limit_is_invalid_for_savings() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 -10");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_less_than_withdraw_limit_is_invalid_for_checking() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 -10");
		assertFalse(actual);
	}

	@Test
	void withdraw_zero_is_valid_for_savings() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = withdrawValidator.validate_withdraw_amount(ID, "0");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_within_withdraw_limit_is_valid_for_savings() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 1000");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_within_withdraw_limit_is_valid_for_checking() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 400");
		assertTrue(actual);
	}

	@Test
	void withdraw_amount_more_than_withdraw_limit_is_invalid_for_savings() {
		savings.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 1001");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_more_than_withdraw_limit_is_invalid_for_checking() {
		checking.create(bank, ID, APR, BALANCE);
		boolean actual = commandValidator.validate("withdraw 12345678 401");
		assertFalse(actual);
	}

	@Test
	void withdraw_less_than_account_balance_is_invalid_for_cd() {
		cd.create(bank, ID, APR, "500");
		boolean actual = commandValidator.validate("withdraw 12345678 100");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_equal_to_account_balance_is_invalid_for_cd_before_12_months_pass() {
		cd.create(bank, ID, APR, "500");
		boolean actual = commandValidator.validate("withdraw 12345678 500");
		assertFalse(actual);
	}

	@Test
	void withdraw_amount_less_than_account_balance_is_invalid_for_cd_after_12_months_pass() {
		cd.create(bank, ID, APR, "500");
		commandProcessor.process("pass 12");
		boolean actual = commandValidator.validate("withdraw 12345678 400");
		assertFalse(actual);
	}

	@Test
	void withdraw_from_cd_account_on_the_12th_month_is_valid() {
		cd.create(bank, ID, APR, "500");
		commandProcessor.process("pass 12");
		boolean actual = commandValidator.validate("withdraw 12345678 520.31");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_cd_account_after_12_months_is_valid() {
		cd.create(bank, ID, APR, "500");
		commandProcessor.process("pass 13");
		boolean actual = commandValidator.validate("withdraw 12345678 522.04");
		assertTrue(actual);
	}

	@Test
	void withdraw_from_account_that_does_not_exist_is_invalid() {
		boolean actual = commandValidator.validate(MIXED_WITHDRAW_STRING);
		assertFalse(actual);
	}

	@Test
	void withdraw_from_savings_account_more_than_once_a_month_is_invalid() {
		savings.create(bank, ID, APR, "500");
		commandProcessor.process("withdraw 12345678 100");
		boolean actual_2 = commandValidator.validate("withdraw 12345678 50");
		assertFalse(actual_2);
	}

	@Test
	void withdraw_from_savings_account_twice_is_valid_but_in_different_months() {
		savings.create(bank, ID, APR, "500");
		commandProcessor.process("withdraw 12345678 100");
		commandProcessor.process("pass 1");
		boolean actual_2 = commandValidator.validate("withdraw 12345678 50");
		assertTrue(actual_2);
	}

}
