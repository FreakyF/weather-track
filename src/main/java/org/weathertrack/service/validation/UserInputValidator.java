package org.weathertrack.service.validation;

import org.weathertrack.service.validation.model.InputValidationResult;
import org.weathertrack.service.validation.util.ValidationMessage;

import java.util.regex.Pattern;

public class UserInputValidator implements InputValidator {
	private static final int MAXIMUM_CITY_NAME_LENGTH = 170;

	private static final Pattern CITY_NAME_PATTERN_NUMBERS = Pattern.compile(".*\\d+.*");
	private static final Pattern CITY_NAME_PATTERN_SPECIAL_CHARACTERS = Pattern.compile(".*[^\\p{L}-'\\d\\s]+.*");

	@Override
	public InputValidationResult validateCityNameInput(String userCityInput) {
		if (userCityInput == null) {
			throw new IllegalArgumentException(InputValidationExceptionMessage.USER_INPUT_IS_NULL);
		}
		if (userCityInput.isBlank()) {
			return new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_EMPTY + ValidationMessage.PLEASE_TRY_AGAIN);

		}
		if (userCityInput.length() > MAXIMUM_CITY_NAME_LENGTH) {
			return new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_TOO_LONG + ValidationMessage.PLEASE_TRY_AGAIN);
		}
		if (CITY_NAME_PATTERN_NUMBERS.matcher(userCityInput).matches()) {
			return new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_CONTAINS_NUMBERS + ValidationMessage.PLEASE_TRY_AGAIN);
		}
		if (CITY_NAME_PATTERN_SPECIAL_CHARACTERS.matcher(userCityInput).matches()) {
			return new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS + ValidationMessage.PLEASE_TRY_AGAIN);
		}

		return new InputValidationResult(true, null);
	}

	@Override
	public InputValidationResult validateMenuEntryInput(int userMenuEntry) {
		if (userMenuEntry <= 0) {
			return new InputValidationResult(
					false,
					ValidationMessage.MENU_ENTRY_IS_ZERO + ValidationMessage.PLEASE_TRY_AGAIN);
		}
		return new InputValidationResult(true, null);
	}
}
