package gui;

import entities.Evenement;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import services.ServiceEvenement;
import services.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class listReservationController implements Initializable {

    @FXML
    private TableColumn<Reservation, Date> DateCell;

    @FXML
    private TableColumn<Reservation, String> DescriptionCell;

    @FXML
    private TableColumn<Reservation, Integer> EvenementCell;

    @FXML
    private Button bntAddReservation;

    @FXML
    private Button btnDeleteReservation;

    @FXML
    private Button btnLoadReservation;

    @FXML
    private ComboBox<String> comboBoxTri;

    @FXML
    private AnchorPane listReservationtPane;

    @FXML
    private TableView<Reservation> tableReservation;

    @FXML
    private TextField txtSearchReservation;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AfficherReservation();
    }


    @FXML
    void open_addReservation(ActionEvent event)throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("addReservation.fxml"));
        listReservationtPane.getChildren().removeAll();
        listReservationtPane.getChildren().setAll(fxml);
    }

    ObservableList<Reservation> data = FXCollections.observableArrayList();

    public void AfficherReservation()
    {
        ServiceReservation ps = new ServiceReservation();
        ps.Show().stream().forEach((c) -> {
            data.add(c);
        });

        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));
        DescriptionCell.setCellFactory(TextFieldTableCell.forTableColumn());
        DescriptionCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, String> event) {
                Reservation p = event.getRowValue();
                p.setDescription(event.getNewValue());
                ServiceReservation ps = new ServiceReservation();
                ps.modifier(p);
            }
        });
        DateCell.setCellValueFactory(new PropertyValueFactory<>("date"));
        DateCell.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
            private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public String toString(Date object) {
                return dateFormat.format(object);
            }

            @Override
            public Date fromString(String string) {
                try {
                    // Parse the string into a Date object using the defined format
                    return dateFormat.parse(string);
                } catch (ParseException e) {
                    e.printStackTrace();
                    // If the string can't be parsed, return null
                    return null;
                }
            }
        }));
        DateCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, Date>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, Date> event) {
                Reservation p = event.getRowValue();
                p.setDate(event.getNewValue());
                ServiceReservation ps = new ServiceReservation();
                ps.modifier(p);
            }
        });
        EvenementCell.setCellValueFactory(new PropertyValueFactory<>("id_evenement"));
        EvenementCell.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        EvenementCell.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Reservation, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Reservation, Integer> event) {
                Reservation p = event.getRowValue();
                p.setId_evenement(event.getNewValue());
                ServiceReservation ps = new ServiceReservation();
                ps.modifier(p);
            }
        });
        tableReservation.setItems(data);
        comboBoxTri.getItems().addAll("Trier Selon",  "Description");
        try {
            searchReservation();
        } catch (SQLException ex) {
            Logger.getLogger(listReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void TriChoice(ActionEvent event) throws IOException {
        if (comboBoxTri.getValue().equals("Description")) {
            TriDescription();
        }
    }

    private void TriDescription() {
        ServiceReservation se = new ServiceReservation();
        List<Reservation> a = se.triDescription();
        DescriptionCell.setCellValueFactory(new PropertyValueFactory<>("description"));
        DateCell.setCellValueFactory(new PropertyValueFactory<>("date"));
        EvenementCell.setCellValueFactory(new PropertyValueFactory<>("id_evenement"));

        tableReservation.setItems(FXCollections.observableList(a));
    }

    @FXML
    void supprimerReservation(ActionEvent event) throws SQLException {
        ServiceReservation ps = new ServiceReservation();

        if (tableReservation.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Veuillez sélectionner la Reservation à supprimer");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette Reservation ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Récupérer l'ID de la Reservation sélectionnée dans la vue de la table
            int id = tableReservation.getSelectionModel().getSelectedItem().getId();

            // Supprimer la Reservation de la base de données
            ps.supprimer(id);
            // Rafraîchir la liste de données
            data.clear();
            AfficherReservation();
            // Rafraîchir la vue de la table
            tableReservation.refresh();
        }
    }

    public ServiceReservation sr = new ServiceReservation();

    public void searchReservation() throws SQLException {
        FilteredList<Reservation> filteredData = new FilteredList<>(FXCollections.observableArrayList(sr.Show()), p -> true);
        txtSearchReservation.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(res -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String description = String.valueOf(res.getDescription());
                String date = String.valueOf(res.getDate());
                String lowerCaseFilter = newValue.toLowerCase();

                if (description.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (date.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableReservation.comparatorProperty());
        tableReservation.setItems(sortedData);
    }
}
