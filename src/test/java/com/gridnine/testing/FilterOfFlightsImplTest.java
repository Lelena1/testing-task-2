package com.gridnine.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing flight filtering
 */
class FilterOfFlightsImplTest {
    private final FilterOfFlightsImpl filter = new FilterOfFlightsImpl();
    private final List<Flight> flights = new ArrayList<>();
    LocalDateTime dateTimeNow = LocalDateTime.now();

    @Test
    @DisplayName("Checking the departure filtering method up to the current time")
    void shouldFindFlightsWhenDepartureTimeIsAfterTheCurrentTime() {
        // Примеры перелетов для тестирования
        // Перелет 1: Дата вылета раньше текущего времени
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(dateTimeNow.minusHours(2), dateTimeNow.plusHours(1)));
        flights.add(new Flight(segments));

        // Перелет 2: Дата вылета позже текущего времени
        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(dateTimeNow.plusHours(1), LocalDateTime.now().plusHours(3)));
        flights.add(new Flight(segments2));

        // Применяем фильтр
        List<Flight> filteredFlights = filter.findFlightsWhenDepartureTimeIsAfterTheCurrentTime(flights);

        // Проверяем, что Перелет 2 содержится в отфильтрованном списке
        assertEquals(1, filteredFlights.size());
        assertEquals(flights.get(1), filteredFlights.get(0));

        //Проверяем, что дата вылета позже текущей даты
        for (Flight flight : filteredFlights) {
            for (Segment segment : flight.getSegments()) {
                assertTrue(segment.getDepartureDate().isAfter(dateTimeNow));
            }
        }
    }

    @Test
    @DisplayName("Checking the method of filtering segments with an arrival date earlier than the departure date")
    void shouldFindFlightsWhenSegmentsHaveTheArrivalDateAfterTheDepartureDate() {

        // Примеры перелетов для тестирования
        // Перелет 1: Дата вылета позже даты прилета
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(dateTimeNow.plusHours(1), dateTimeNow.minusHours(1)));
        flights.add(new Flight(segments));

        // Перелет 2: Дата вылета раньше даты прилета
        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(dateTimeNow, dateTimeNow.plusHours(3)));
        flights.add(new Flight(segments2));

        // Применяем фильтр
        List<Flight> filteredFlights = filter.findFlightsWhenSegmentsHaveTheArrivalDateAfterTheDepartureDate(flights);

        // Проверяем, что только один перелет содержится в отфильтрованном списке
        assertEquals(1, filteredFlights.size());
        assertEquals(flights.get(1), filteredFlights.get(0));

        //Проверяем, что дата прилета позже даты вылета
        for (Flight flight : filteredFlights) {
            for (Segment segment : flight.getSegments()) {
                assertTrue(segment.getArrivalDate().isAfter(segment.getDepartureDate()));
            }
        }
    }

    @Test
    @DisplayName("Checking the method of filtering the total time spent on earth")
    void shouldFindFlightsWhenTheTotalTimeSpentOnEarthAreLessThenTwoHours() {

        // Примеры перелетов для тестирования
        // Перелет 1: Общее время на земле больше 2 часов
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(dateTimeNow.minusHours(6), dateTimeNow.minusHours(4)));
        segments.add(new Segment(dateTimeNow.minusHours(1), dateTimeNow));
        segments.add(new Segment(dateTimeNow.plusHours(4), dateTimeNow.plusHours(6)));
        flights.add(new Flight(segments));

        // Перелет 2: Общее время на земле не более 2 часов
        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(dateTimeNow.minusHours(6), dateTimeNow.minusHours(4)));
        segments2.add(new Segment(dateTimeNow.minusHours(3), dateTimeNow));
        segments2.add(new Segment(dateTimeNow.plusHours(1), dateTimeNow.plusHours(6)));
        flights.add(new Flight(segments2));

        // Применяем фильтр
        List<Flight> filteredFlights = filter.findFlightsWhenTheTotalTimeSpentOnEarthAreLessThenTwoHours(flights);

        // Проверяем, что только один перелет содержится в отфильтрованном списке и это не первый перелет
        assertEquals(1, filteredFlights.size());
        assertEquals(flights.get(1), filteredFlights.get(0));
        assertNotEquals(flights.get(0),filteredFlights.get(0));
    }
}