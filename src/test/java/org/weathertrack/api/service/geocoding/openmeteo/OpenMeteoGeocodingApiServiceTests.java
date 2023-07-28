package org.weathertrack.api.service.geocoding.openmeteo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.weathertrack.api.service.geocoding.GeocodingApiService;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OpenMeteoGeocodingApiServiceTests {
    private GeocodingApiService sut;

    @BeforeEach
    void beforeEach(){
        sut = new OpenMeteoGeocodingApiService();
    }

    @Test
    void fetchCitiesForCityName_whenCityNameIsNull_shouldThrowException() {
        // when
        String cityName = null;
        var expectedExceptionMessage = "City name was null";

        // then
        var exception = assertThrows(NullPointerException.class, () -> sut.fetchCitiesForCityName(cityName));
        assert(exception.getMessage()).equals(expectedExceptionMessage);
    }
}
