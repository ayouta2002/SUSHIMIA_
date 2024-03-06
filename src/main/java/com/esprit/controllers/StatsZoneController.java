package com.esprit.controllers;

import com.esprit.models.Zones;
import com.esprit.services.ZonesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class StatsZoneController {

    @FXML
    private BarChart<String, Number> zoneChart;

    private final ZonesService zonesService = new ZonesService();

    @FXML
    public void initialize() {
        // Populate the chart with zone statistics
        loadZoneData();
    }


    private void loadZoneData() {
        // Retrieve zone data from the database
        List<Zones> zonesList = zonesService.afficher();

        // Create an ObservableList to hold the series
        ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();

        // Add data to the series
        for (Zones zone : zonesList) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(zone.getNom());
            series.getData().add(new XYChart.Data<>("Capacity", zone.getCapacity()));
            seriesList.add(series);
        }

        zoneChart.setData(seriesList);
    }
}
