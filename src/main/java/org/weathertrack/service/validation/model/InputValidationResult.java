package org.weathertrack.service.validation.model;

public class InputValidationResult {
	private final boolean valid;
	private final String validationMessage;

	public InputValidationResult(boolean valid, String validationMessage) {
		this.valid = valid;
		this.validationMessage = validationMessage;
	}

	public InputValidationResult(boolean valid) {
		this.valid = valid;
		this.validationMessage = null;
	}

	public boolean isValid() {
		return valid;
	}

	public String getValidationMessage() {
		return validationMessage;
	}
}
