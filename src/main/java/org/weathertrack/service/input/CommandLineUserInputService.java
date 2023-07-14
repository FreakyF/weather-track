package org.weathertrack.service.input;

import java.util.Scanner;

@SuppressWarnings("java:S106")
public class CommandLineUserInputService implements UserInputService {
	private final Scanner scanner;

	public CommandLineUserInputService() {
		scanner = new Scanner(System.in);
	}

	@Override
	public String getUserInput(String message) {
		System.out.print(message);
		return scanner.nextLine();
	}
}
