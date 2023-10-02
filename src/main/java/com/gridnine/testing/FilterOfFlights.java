package com.gridnine.testing;

import java.util.List;

/**
 * Filters the list of flights by some criteria
 *
 * @return filtered list of flights
 */
public interface FilterOfFlights {

    List<Flight> findFlightsWhenDepartureTimeIsAfterTheCurrentTime(List<Flight> flights);

    List<Flight> findFlightsWhenSegmentsHaveTheArrivalDateAfterTheDepartureDate(List<Flight> flights);

    List<Flight> findFlightsWhenTheTotalTimeSpentOnEarthAreLessThenTwoHours(List<Flight> flights);
}