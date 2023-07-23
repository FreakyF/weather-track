package org.weathertrack.input.validation;

import org.weathertrack.input.validation.model.InputValidationResult;

public interface InputValidator {
	InputValidationResult validateCityNameInput(String userCityInput);

	InputValidationResult validateMenuEntryInput(int userMenuEntry);
}
