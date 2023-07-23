package org.weathertrack.validation.model;

public interface InputValidator {
	InputValidationResult validateCityNameInput(String userCityInput);

	InputValidationResult validateMenuEntryInput(int userMenuEntry);
}
