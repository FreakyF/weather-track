package org.weathertrack.api.service.forecast.openmeteo.model;

import java.util.List;

public record Daily(List<String> time,
                    List<Integer> weathercode,
                    List<Double> temperature_2m_max,
                    List<Integer> precipitation_probability_max,
                    List<Double> windspeed_10m_max) {

}
