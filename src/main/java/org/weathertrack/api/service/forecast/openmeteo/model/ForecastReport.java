package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.Map;

public record ForecastReport(double latitude,
                             double longitude,
                             double generationtime_ms,
                             int utc_offset_seconds,
                             String timezone,
                             String timezone_abbreviation,
                             double elevation,
                             Map<String, String> hourly_units,
                             Hourly hourly,
                             Map<String, String> daily_units,
                             Daily daily) {
}
