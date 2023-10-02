package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the logic of filtering the list of flights in accordance with by some criteria
 */
public class FilterOfFlightsImpl implements FilterOfFlights {

    /**
     * A method for filtering the list of flights that finds flights whose departure time is later than the current time
     *
     * @return a filtered list of flights
     */

    private static final int MAX_TOTAL_EARTH_HOURS = 2;

    @Override
    public List<Flight> findFlightsWhenDepartureTimeIsAfterTheCurrentTime(List<Flight> flights) {

        return flights.stream()
                .filter(flight -> flight.getSegments()
                        .stream().allMatch(segment -> segment.getDepartureDate()
                                .isAfter(LocalDateTime.now())))
                .collect(Collectors.toList());
    }

    /**
     * A method for filtering the list of flights that finds flights whose arrival dates are later than the departure date
     *
     * @return a filtered list of flights
     */
    @Override
    public List<Flight> findFlightsWhenSegmentsHaveTheArrivalDateAfterTheDepartureDate(List<Flight> flights) {

        return flights.stream()
                .filter(flight -> flight.getSegments()
                        .stream().allMatch(segment -> segment.getArrivalDate()
                                .isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());
    }

    /**
     * A method for filtering the list of flights, which finds flights with less than 2 hours on the ground between them
     *
     * @return a filtered list of flights
     */
    @Override
    public List<Flight> findFlightsWhenTheTotalTimeSpentOnEarthAreLessThenTwoHours(List<Flight> flights) {

        return flights.stream()
                .filter(flight -> {
                    if (flight.getSegments().size() > 1) {
                        return isTotalEarthTimeLessThenTwoHours(flight.getSegments());
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    private boolean isTotalEarthTimeLessThenTwoHours(List<Segment> segments) {

        Duration totalEarthTime = Duration.ZERO;

        for (int i = 0; i < segments.size() - 1; i++) {
            Duration earthTime = Duration.between(segments.get(i).getArrivalDate(),
                    segments.get(i + 1).getDepartureDate());

            if (earthTime.isNegative()) {
                return false;
            }
            totalEarthTime = totalEarthTime.plus(earthTime);
        }
        return totalEarthTime.toHours() <= MAX_TOTAL_EARTH_HOURS;
    }
}