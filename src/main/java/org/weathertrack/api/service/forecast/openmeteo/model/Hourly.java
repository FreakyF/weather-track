package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public record Hourly(List<String> time,
                     List<Double> temperature_2m,
                     List<Integer> relativehumidity_2m,
                     List<Double> precipitation,
                     List<Integer> weathercode,
                     List<Double> surface_pressure,
                     List<Double> windspeed_10m) {
}
