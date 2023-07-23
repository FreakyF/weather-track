package org.weathertrack.validation;

import org.weathertrack.validation.model.InputValidationResult;

public interface InputValidator {
	InputValidationResult validateCityNameInput(String userCityInput);

	InputValidationResult validateMenuEntryInput(int userMenuEntry);
}
