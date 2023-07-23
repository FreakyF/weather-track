package org.weathertrack.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.weathertrack.input.validation.InputValidator;
import org.weathertrack.input.validation.UserInputValidator;
import org.weathertrack.input.validation.exception.InputExceptionMessage;
import org.weathertrack.input.validation.resource.ValidationMessageResource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserInputValidatorTests {
	private InputValidator sut;

	@BeforeEach
	void setUp() {
		sut = new UserInputValidator();
	}

	private static Stream<Arguments>
	validateCityNameInput_WhenUserCityInput_IsEmpty_ShouldReturnInvalidInputValidationResult() {
		return Stream.of(
				Arguments.of("", ValidationMessageResource.CITY_INPUT_BLANK),
				Arguments.of(" ", ValidationMessageResource.CITY_INPUT_BLANK)
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateCityNameInput_WhenUserCityInput_IsEmpty_ShouldReturnInvalidInputValidationResult(String cityNameValue, String inputValidationResultValue) {
		// When
		var actualResult = sut.validateCityNameInput(cityNameValue);

		// Then
		assertFalse(actualResult.isValid());
		assertEquals(inputValidationResultValue, actualResult.getValidationMessage());
	}

	static String generateCityName(int length) {
		return "x".repeat(Math.max(0, length));
	}

	private static Stream<Arguments>
	validateCityNameInput_WhenUserCityInput_IsTooLong_ShouldReturnInvalidInputValidationResult() {
		return Stream.of(
				Arguments.of(generateCityName(171), ValidationMessageResource.CITY_INPUT_TOO_LONG),
				Arguments.of(generateCityName(172), ValidationMessageResource.CITY_INPUT_TOO_LONG),
				Arguments.of(generateCityName(220), ValidationMessageResource.CITY_INPUT_TOO_LONG)
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateCityNameInput_WhenUserCityInput_IsTooLong_ShouldReturnInvalidInputValidationResult(String cityNameValue, String inputValidationResultValue) {
		// When
		var actualResult = sut.validateCityNameInput(cityNameValue);

		// Then
		assertFalse(actualResult.isValid());
		assertEquals(inputValidationResultValue, actualResult.getValidationMessage());
	}

	private static Stream<Arguments>
	validateCityNameInput_WhenUserCityInput_ContainsNumbers_ShouldReturnInvalidInputValidationResult() {
		return Stream.of(
				Arguments.of("0", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS),
				Arguments.of("123 San Francisco", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS),
				Arguments.of("San Francisco 345", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS),
				Arguments.of("San 678 Francisco", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS),
				Arguments.of("0123456789", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS),
				Arguments.of("0 1 2 3 4 5 6 7 8 9", ValidationMessageResource.CITY_INPUT_CONTAINS_NUMBERS)
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateCityNameInput_WhenUserCityInput_ContainsNumbers_ShouldReturnInvalidInputValidationResult(String cityNameValue, String inputValidationResultValue) {
		// When
		var actualResult = sut.validateCityNameInput(cityNameValue);

		// Then
		assertFalse(actualResult.isValid());
		assertEquals(inputValidationResultValue, actualResult.getValidationMessage());
	}

	private static Stream<Arguments>
	validateCityNameInput_WhenUserCityInput_ContainsSpecialCharacters_ShouldReturnInvalidInputValidationResult() {
		return Stream.of(
				Arguments.of("!@#$%^&*()_+=/*~<>?;:{}[]|\\", ValidationMessageResource.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS),
				Arguments.of("! San Francisco", ValidationMessageResource.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS),
				Arguments.of("San Francisco @", ValidationMessageResource.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS),
				Arguments.of("San # Francisco", ValidationMessageResource.CITY_INPUT_CONTAINS_SPECIAL_CHARACTERS)
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateCityNameInput_WhenUserCityInput_ContainsSpecialCharacters_ShouldReturnInvalidInputValidationResult(String cityNameValue, String inputValidationResultValue) {
		// When
		var actualResult = sut.validateCityNameInput(cityNameValue);

		// Then
		assertFalse(actualResult.isValid());
		assertEquals(inputValidationResultValue, actualResult.getValidationMessage());
	}

	@Test
	void validateCityNameInput_WhenUserCityInput_IsNull_ShouldThrowError() {
		// When
		var throwedException = assertThrows(IllegalArgumentException.class, () ->
				sut.validateCityNameInput(null));

		// Then
		assertEquals(InputExceptionMessage.USER_INPUT_IS_NULL, throwedException.getMessage());
	}

	private static Stream<Arguments>
	validateCityNameInput_WhenUserCityInput_IsGood_ShouldReturnValidInputValidationResult() {
		return Stream.of(
				Arguments.of("Kielce"),
				Arguments.of("Jastrzębie-Zdrój"),
				Arguments.of("San Francisco"),
				Arguments.of("D'Lo"),
				Arguments.of("Saint-Lô"),
				Arguments.of("Winston-Salem")
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateCityNameInput_WhenUserCityInput_IsGood_ShouldReturnValidInputValidationResult(String cityNameValue) {
		// Given
		var actualResult = sut.validateCityNameInput(cityNameValue);

		// Then
		assertTrue(actualResult.isValid());
		assertNull(actualResult.getValidationMessage());
	}

	@Test
	void validateMenuEntryInput_WhenUserMenuEntry_IsZero_ShouldReturnInvalidInputValidationResult() {
		// When
		int userCityInputValue = 0;

		// Given
		var actualResult = sut.validateMenuEntryInput(userCityInputValue);

		// Then
		assertFalse(actualResult.isValid());
		assertEquals(ValidationMessageResource.MENU_ENTRY_IS_ZERO, actualResult.getValidationMessage());
	}

	private static Stream<Arguments>
	validateMenuEntryInput_WhenUserMenuEntry_IsGood_ShouldReturnValidInputValidationResult() {
		return Stream.of(
				Arguments.of(1),
				Arguments.of(12),
				Arguments.of(123),
				Arguments.of(1234),
				Arguments.of(12345)
		);
	}

	@ParameterizedTest
	@MethodSource
	void validateMenuEntryInput_WhenUserMenuEntry_IsGood_ShouldReturnValidInputValidationResult(int userCityInputValue) {
		// Given
		var actualResult = sut.validateMenuEntryInput(userCityInputValue);

		// Then
		assertTrue(actualResult.isValid());
		assertNull(actualResult.getValidationMessage());
	}
}
