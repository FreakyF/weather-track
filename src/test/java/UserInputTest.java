import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.weathertrack.service.input.CommandLineUserInputService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserInputTest {
	private final InputStream systemIn = System.in;
	private CommandLineUserInputService userInputService;
	private Scanner mockScanner;

	@BeforeEach
	void setUp() {
		mockScanner = mock(Scanner.class);
		userInputService = new CommandLineUserInputService(mockScanner);
	}

	@AfterEach
	void teardown() {
		System.setIn(systemIn);
	}

	@Test
	void getUserInput_ShouldReturnUserInput() {
		// When
		var expectedInput = "Hello, World!";
		when(mockScanner.nextLine()).thenReturn(expectedInput);

		// Given
		var result = userInputService.getUserInput("Message");

		// Then
		assertEquals(expectedInput, result);
	}

	@Test
	void getUserInput_ShouldPromptMessage() {
		// When
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));

		var expectedMessage = "Hello";
		when(mockScanner.nextLine()).thenReturn("abc");
		// Given
		userInputService.getUserInput(expectedMessage);

		// Then
		assertEquals(expectedMessage, outputStreamCaptor.toString().trim());
	}
}
