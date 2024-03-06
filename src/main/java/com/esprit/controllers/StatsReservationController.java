package com.esprit.controllers;

import com.esprit.models.Reservation;
import com.esprit.services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class StatsReservationController {

    @FXML
    private BarChart<String, Number> reservationChart;

    @FXML
    private Label totalReservationsLabel;

    @FXML
    private Label averageReservationsLabel;

    @FXML
    private Label busiestDayLabel;

    private ReservationService reservationService;

    public void initialize() {
        reservationService = new ReservationService();

        // Load reservation data and update statistics
        loadReservationData();
    }

    private void loadReservationData() {
        // Get all reservations from the service
        List<Reservation> reservations = reservationService.afficher();

        // Calculate total number of reservations
        int totalReservations = reservations.size();
        totalReservationsLabel.setText(String.valueOf(totalReservations));

        // Calculate average reservations per client
        int totalClients = reservations.stream().map(Reservation::getId_C).distinct().toArray().length;
        double averageReservations = (double) totalReservations / totalClients;
        averageReservationsLabel.setText(String.format("%.2f", averageReservations));

        // Calculate busiest reservation day
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        int[] reservationsByDayOfWeek = new int[7];

        for (Reservation reservation : reservations) {
            LocalDate date = reservation.getDateR().toLocalDate();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            reservationsByDayOfWeek[dayOfWeek.getValue() - 1]++;
        }

        int maxReservations = 0;
        int busiestDayIndex = 0;
        for (int i = 0; i < reservationsByDayOfWeek.length; i++) {
            if (reservationsByDayOfWeek[i] > maxReservations) {
                maxReservations = reservationsByDayOfWeek[i];
                busiestDayIndex = i;
            }
        }

        String busiestDay = daysOfWeek.get(busiestDayIndex);
        busiestDayLabel.setText(busiestDay);

        // Display reservation data on the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < 7; i++) {
            series.getData().add(new XYChart.Data<>(daysOfWeek.get(i), reservationsByDayOfWeek[i]));
        }

        reservationChart.getData().add(series);
    }
}
