package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {

	public static final String ID_1 = "12345678";
	public static final String ID_2 = "98765432";
	public static final String APR = "1";
	public static final String BALANCE = "0";
	public static final String MIXED_TRANSFER_STRING = "TranSfer 12345678 98765432 200";
	public static final String MISSING_TRANSFER_STRING = "TranSfer 12345678 200";

	CommandValidator commandValidator;
	TransferValidator transferValidator;
	CommandProcessor commandProcessor;
	Bank bank;
	Savings savings;
	Checking checking;
	Cd cd;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		transferValidator = new TransferValidator(bank);
		commandProcessor = new CommandProcessor(bank);
		savings = new Savings(ID_1, APR, BALANCE);
		checking = new Checking(ID_2, APR, BALANCE);
		cd = new Cd(ID_2, APR, BALANCE);
	}

	@Test
	void transfer_string_is_case_insensitive() {
		savings.create(bank, ID_1, APR, BALANCE);
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertTrue(actual);
	}

	@Test
	void transfer_string_with_missing_argument_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MISSING_TRANSFER_STRING);
		assertFalse(actual);
	}

	@Test
	void typo_in_transfer_command_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate("tranfer 12345678 98765432 200");
		assertFalse(actual);
	}

	@Test
	void transfer_to_account_that_does_not_exist_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertFalse(actual);
	}

	@Test
	void transfer_from_account_that_does_not_exist_is_invalid() {
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertFalse(actual);
	}

	@Test
	void transfer_from_cd_account_is_invalid() {
		cd.create(bank, ID_1, APR, BALANCE);
		savings.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertFalse(actual);
	}

	@Test
	void transfer_to_cd_account_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		cd.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertFalse(actual);
	}

	@Test
	void cd_account_type_cannot_be_involved_in_at_all_in_a_transfer() {
		cd.create(bank, ID_1, APR, BALANCE);
		boolean actual = transferValidator.validate_transfer_account_type(ID_1);
		assertFalse(actual);
	}

	@Test
	void savings_account_can_be_involved_in_a_transfer() {
		savings.create(bank, ID_1, APR, BALANCE);
		boolean actual = transferValidator.validate_transfer_account_type(ID_1);
		assertTrue(actual);
	}

	@Test
	void transfer_to_the_same_account_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		boolean actual = commandValidator.validate("transfer 12345678 12345678 400");
		assertFalse(actual);
	}

	@Test
	void transfer_to_or_from_same_account_type_is_valid() {
		savings.create(bank, ID_1, APR, BALANCE);
		savings.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertTrue(actual);
	}

	@Test
	void transfer_more_than_from_accounts_withdraw_maximum_is_invalid() {
		savings.create(bank, ID_1, APR, BALANCE);
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate("TranSfer 12345678 98765432 10000");
		assertFalse(actual);
	}

	@Test
	void transfer_more_than_to_accounts_deposit_maximum_is_invalid() {
		checking.create(bank, ID_1, APR, BALANCE);
		checking.create(bank, ID_2, APR, BALANCE);
		boolean actual = commandValidator.validate("TranSfer 12345678 98765432 10000");
		assertFalse(actual);
	}

	@Test
	void transfer_from_a_savings_account_twice_in_a_month_is_invalid_and_counts_as_a_withdrawal() {
		savings.create(bank, ID_1, APR, "600");
		checking.create(bank, ID_2, APR, BALANCE);
		commandProcessor.process(MIXED_TRANSFER_STRING);
		boolean actual = commandValidator.validate(MIXED_TRANSFER_STRING);
		assertFalse(actual);

	}
}
