package org.weathertrack.api.service.forecast.openmeteo.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.weathertrack.TestData;
import org.weathertrack.api.service.exception.ApiServiceExceptionMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ForecastDataConverterTests {
	private AutoCloseable closeable;

	@BeforeEach
	void beforeEach() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void afterEach() throws Exception {
		closeable.close();
	}

	@Test
	void forecastReportToForecastData_WhenForecastReportIsNull_ShouldThrowNullPointerException() {
		// Given
		var thrown = assertThrows(
				Exception.class,
				() -> ForecastDataConverter.forecastReportToForecastData(null),
				"Expected forecastReportToForecastData to throw Exception, but it didn't"
		);

		// Then
		assertEquals(NullPointerException.class, thrown.getClass());
		assertEquals(ApiServiceExceptionMessage.FORECAST_REPORT_DATA_IS_NULL, thrown.getMessage());
	}

	@Test
	void forecastReportToForecastData_WhenForecastReportIsValid_ShouldReturnForecastData() {
		// When
		var expectedResult = TestData.Provider.createForecastData();
		var mockedForecastReport = TestData.Provider.createForecastReport();

		// Given
		var result = ForecastDataConverter.forecastReportToForecastData(mockedForecastReport);

		// Then
		assertEquals(expectedResult, result);
	}

	// TODO: Implement more unit tests for various cases when something is missing in the forecast report.
}
