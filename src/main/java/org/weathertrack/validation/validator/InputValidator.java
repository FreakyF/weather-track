package org.weathertrack.validation.validator;

import org.weathertrack.validation.model.InputValidationResult;

public interface InputValidator {
	InputValidationResult validateCityNameInput(String userCityInput);

	InputValidationResult validateMenuEntryInput(int userMenuEntry);
}
