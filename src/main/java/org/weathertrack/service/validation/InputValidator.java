package org.weathertrack.service.validation;

import org.weathertrack.service.validation.model.InputValidationResult;

public interface InputValidator {
	InputValidationResult validateCityNameInput(String userCityInput);

	InputValidationResult validateMenuEntryInput(int userMenuEntry);
}
