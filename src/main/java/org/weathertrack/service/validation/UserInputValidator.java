package org.weathertrack.service.validation;

import org.weathertrack.service.validation.model.InputValidationResult;

public class UserInputValidator implements InputValidator {
	@Override
	public InputValidationResult validateCityNameInput(String userCityInput) {
		return null;
	}

	@Override
	public InputValidationResult validateMenuEntryInput(int userMenuEntry) {
		return null;
	}
}
