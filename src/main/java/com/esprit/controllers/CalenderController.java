package com.esprit.controllers;

import javafx.fxml.FXML;
import com.calendarfx.view.CalendarView;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.esprit.services.ReservationService;
import com.esprit.models.Reservation;

import java.util.List;

public class CalenderController {

    @FXML
    private CalendarView calendarView;

    public void initialize() {
        // Initialize your controller here
        loadReservations();
    }

    private void loadReservations() {

        CalendarSource calendarSource = new CalendarSource("Reservations");

        ReservationService reservationService = new ReservationService();
        List<Reservation> reservations = reservationService.afficher();
        System.out.println(reservations);

        Calendar calendar = new Calendar("Reservations");

        for (Reservation reservation : reservations) {
            Entry<String> entry = new Entry<>(reservation.getZone());
            entry.changeStartDate(reservation.getDateR().toLocalDate());
            entry.changeEndDate(reservation.getDateR().toLocalDate());
            calendar.addEntry(entry);
        }
        calendarSource.getCalendars().addAll(calendar);
        calendarView.getCalendarSources().addAll(calendarSource);
    }
}
