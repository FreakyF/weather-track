package org.weathertrack.forecast;

import com.google.inject.AbstractModule;
import org.weathertrack.forecast.api.ApiForecastProvider;

public class ForecastModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(ForecastProvider.class).to(ApiForecastProvider.class);
    }
}
