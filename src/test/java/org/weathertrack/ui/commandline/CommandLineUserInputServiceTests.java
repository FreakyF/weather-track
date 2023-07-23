package org.weathertrack.ui.commandline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandLineUserInputServiceTests {
	private CommandLineUserInputService userInputService;
	private Scanner mockScanner;

	private static final String EXPECTED_USER_MESSAGE = "User message";
	private static final String EXPECTED_PROMPT_MESSAGE = "Prompt message";

	@BeforeEach
	void setUp() {
		mockScanner = mock(Scanner.class);
		userInputService = new CommandLineUserInputService(mockScanner);
	}

	@Test
	void getUserInput_ShouldReturnUserInput() {
		// When
		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		var result = userInputService.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_USER_MESSAGE, result);
	}

	@Test
	void getUserInput_ShouldPromptMessage() {
		// When
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));

		when(mockScanner.nextLine()).thenReturn(EXPECTED_USER_MESSAGE);

		// Given
		userInputService.getUserInput(EXPECTED_PROMPT_MESSAGE);

		// Then
		assertEquals(EXPECTED_PROMPT_MESSAGE, outputStreamCaptor.toString().trim());
	}
}
