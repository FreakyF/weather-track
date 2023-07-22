package org.weathertrack.service.validation;

import org.weathertrack.service.validation.model.InputValidationResult;
import org.weathertrack.service.validation.util.ValidationMessage;

import java.util.regex.Pattern;

public class UserInputValidator implements InputValidator {
	private static final int MAXIMUM_CITY_NAME_LENGTH = 170;
	InputValidationResult inputValidationResult;

	@Override
	public InputValidationResult validateCityNameInput(String userCityInput) {
		// check for null
		if (userCityInput == null) {
			throw new IllegalStateException(InputValidationExceptionMessage.USER_INPUT_IS_NULL);
		}
		// check if it's empty or not trimmed
		if (userCityInput.isBlank()) {
			inputValidationResult = new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_EMPTY + ValidationMessage.PLEASE_TRY_AGAIN);
			return inputValidationResult;
		}
		// check maximum length
		if (userCityInput.length() > MAXIMUM_CITY_NAME_LENGTH) {
			inputValidationResult = new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_TOO_LONG + ValidationMessage.PLEASE_TRY_AGAIN);
			return inputValidationResult;
		}
		// check for valid characters
		Pattern pattern = Pattern.compile("[\\p{L}'\\- ]+");
		if (!pattern.matcher(userCityInput).matches()) {
			inputValidationResult = new InputValidationResult(
					false,
					ValidationMessage.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS + ValidationMessage.PLEASE_TRY_AGAIN);
			return inputValidationResult;
		}

		inputValidationResult = new InputValidationResult(true, null);
		return inputValidationResult;
	}

	@Override
	public InputValidationResult validateMenuEntryInput(int userMenuEntry) {
		if (userMenuEntry > 0) {
			inputValidationResult = new InputValidationResult(
					true,
					ValidationMessage.MENU_ENTRY_IS_ZERO + ValidationMessage.PLEASE_TRY_AGAIN);
			return inputValidationResult;
		}
		inputValidationResult = new InputValidationResult(true, null);
		return inputValidationResult;
	}
}
