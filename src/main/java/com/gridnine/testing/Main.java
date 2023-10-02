package com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        FilterOfFlightsImpl filter = new FilterOfFlightsImpl();
        List<Flight> flights = FlightBuilder.createFlights();

        // Вывод в консоль списка всех перелетов
        System.out.println("Список всех перелетов: " + "\n" + flights);

        // Вывод в консоль списка перелетов, у которых время вылета позже текущего времени
        List<Flight> filteredFlights = filter.findFlightsWhenDepartureTimeIsAfterTheCurrentTime(flights);
        System.out.println("Список всех перелетов, у которых время вылета позже текущего времени: " + "\n" + filteredFlights);

        // Вывод в консоль списка перелетов, даты прилета которых позже даты вылета
        List<Flight> filteredFlights2 = filter.findFlightsWhenSegmentsHaveTheArrivalDateAfterTheDepartureDate(flights);
        System.out.println("Список всех перелетов, даты прилета которых позже даты вылета: " + "\n" + filteredFlights2);

        // Выведение в консоль списка перелетов, время нахождения на земле между которыми не более 2 часов

        List<Flight> filteredFlights3 = filter.findFlightsWhenTheTotalTimeSpentOnEarthAreLessThenTwoHours(flights);
        System.out.println("Список всех перелетов, общее время нахождения на земле между которыми не более 2 часов: " + "\n" + filteredFlights3);
    }
}