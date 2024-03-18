package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {

	public static final String MAIN_PASS = "Pass 1";

	CommandValidator commandValidator;
	PassTimeValidator passTimeValidator;
	Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		passTimeValidator = new PassTimeValidator(bank);
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void pass_time_is_case_insensitive() {
		boolean actual = commandValidator.validate(MAIN_PASS);
		assertTrue(actual);
	}

	@Test
	void typo_in_pass_time_command_is_invalid() {
		boolean actual = passTimeValidator.validate("Pas 2");
		assertFalse(actual);
	}

	@Test
	void missing_parameter_in_pass_time_is_invalid() {
		boolean actual = commandValidator.validate("pass");
		assertFalse(actual);
	}

	@Test
	void pass_time_of_zero_is_invalid() {
		boolean actual = commandValidator.validate("pass 0.1");
		assertFalse(actual);
	}

	@Test
	void pass_negative_time_is_invalid() {
		boolean actual = commandValidator.validate("pass -3");
		assertFalse(actual);
	}

	@Test
	void pass_time_between_1_and_60_is_valid() {
		boolean actual = commandValidator.validate("pass 60");
		assertTrue(actual);
	}

	@Test
	void pass_time_more_than_60_is_invalid() {
		boolean actual = commandValidator.validate("pass 70");
		assertFalse(actual);
	}

	@Test
	void pass_time_that_is_not_an_integer_is_invalid() {
		boolean actual = commandValidator.validate("pass 50.0");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_with_spaces_in_front_is_invalid() {
		boolean actual = commandValidator.validate("  pass 1");
		assertFalse(actual);
	}

	@Test
	void pass_time_command_with_more_than_one_space_in_between_words_is_invalid() {
		boolean actual = commandValidator.validate("  pass 40");
		assertFalse(actual);
	}
}
