package org.weathertrack.api.service.forecast.openmeteo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OpenMeteoForecastApiServiceTests {
	private AutoCloseable closeable;
	@InjectMocks
	private OpenMeteoForecastApiService sut;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
		sut = new OpenMeteoForecastApiService();
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();

	}
}
