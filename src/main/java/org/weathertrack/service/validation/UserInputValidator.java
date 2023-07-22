package org.weathertrack.service.validation;

import org.weathertrack.service.validation.model.InputValidationResult;
import org.weathertrack.service.validation.util.ValidationMessage;

import java.util.regex.Pattern;

public class UserInputValidator implements InputValidator {
	private static final int MAXIMUM_CITY_NAME_LENGTH = 170;

	private static final Pattern NUMBERS_REGEX_PATTERN = Pattern.compile(".*\\d+.*");

	// Match any string that contains at least one character that is not a letter, hyphen, apostrophe, digit, or whitespace.
	private static final Pattern SPECIAL_CHARACTERS_REGEX_PATTERN = Pattern.compile(".*[^\\p{L}-'\\d\\s]+.*");

	@Override
	public InputValidationResult validateCityNameInput(String userCityInput) {
		if (userCityInput == null) {
			throw new IllegalArgumentException(InputValidationExceptionMessage.USER_INPUT_IS_NULL);
		}
		if (userCityInput.isBlank()) {
			return new InputValidationResult(false, ValidationMessage.CITY_INPUT_BLANK);

		}
		if (userCityInput.length() > MAXIMUM_CITY_NAME_LENGTH) {
			return new InputValidationResult(false, ValidationMessage.CITY_INPUT_TOO_LONG);
		}
		if (NUMBERS_REGEX_PATTERN.matcher(userCityInput).matches()) {
			return new InputValidationResult(
					false, ValidationMessage.CITY_INPUT_CONTAINS_NUMBERS);
		}
		if (SPECIAL_CHARACTERS_REGEX_PATTERN.matcher(userCityInput).matches()) {
			return new InputValidationResult(false, ValidationMessage.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS);
		}

		return new InputValidationResult(true, null);
	}

	@Override
	public InputValidationResult validateMenuEntryInput(int userMenuEntry) {
		if (userMenuEntry <= 0) {
			return new InputValidationResult(false, ValidationMessage.MENU_ENTRY_IS_ZERO);
		}
		return new InputValidationResult(true, null);
	}
}
