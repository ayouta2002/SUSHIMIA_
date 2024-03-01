package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Commande;
import tn.esprit.models.Resto;
import tn.esprit.services.RestoService;
import tn.esprit.zizo.HelloApplication;

import java.io.IOException;
import java.util.List;

public class CommandeFrontController {

    @FXML
    private ComboBox<Resto> restoComboBox;

    @FXML
    private TextField adresseField;

    @FXML
    private TextField numField;

    @FXML
    private TextField panierField;

    @FXML
    private CheckBox pizzaCheckBox;

    @FXML
    private CheckBox burgerCheckBox;

    @FXML
    private CheckBox sushiCheckBox;

    @FXML
    private CheckBox pastaCheckBox;

    @FXML
    private CheckBox saladCheckBox;

    @FXML
    private CheckBox tacosCheckBox;

    @FXML
    private CheckBox sandwichCheckBox;

    @FXML
    private CheckBox friedChickenCheckBox;

    @FXML
    private CheckBox steakCheckBox;
    private RestoService restoService = new RestoService();
    @FXML
    public void initialize() {
        List<Resto> restos = restoService.afficher();
        ObservableList<Resto> restoList = FXCollections.observableArrayList(restos);
        restoComboBox.setItems(restoList);
    }

    @FXML
    public void handleCheckBoxAction() {
        StringBuilder selectedItems = new StringBuilder();
        appendIfSelected(pizzaCheckBox, selectedItems);
        appendIfSelected(burgerCheckBox, selectedItems);
        appendIfSelected(sushiCheckBox, selectedItems);
        appendIfSelected(pastaCheckBox, selectedItems);
        appendIfSelected(saladCheckBox, selectedItems);
        appendIfSelected(tacosCheckBox, selectedItems);
        appendIfSelected(sandwichCheckBox, selectedItems);
        appendIfSelected(friedChickenCheckBox, selectedItems);
        appendIfSelected(steakCheckBox, selectedItems);

        panierField.setText(selectedItems.toString());
    }

    private void appendIfSelected(CheckBox checkBox, StringBuilder selectedItems) {
        if (checkBox.isSelected()) {
            selectedItems.append(checkBox.getText()).append("\n");
        }
    }

    public void createCommande(ActionEvent actionEvent) throws IOException {
        String adresse = adresseField.getText();
        String num = numField.getText();
        String panier = panierField.getText();
        double price = calculatePrice();

        Commande newCommande = new Commande();
        newCommande.setAdresseC(adresse);
        newCommande.setNumC(num);
        newCommande.setPanier(panier);
        newCommande.setPrice(price);
        newCommande.setIdResto(restoComboBox.getValue().getId_resto());

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("OrderCheckOut.fxml"));
        Parent root = loader.load();
        OrderCheckOutController controller = loader.getController();
        controller.setData(newCommande);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private double calculatePrice() {
        return this.panierField.getText().length()*10;
    }


    public void cancelCommande(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("CommandeGestionMenu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) friedChickenCheckBox.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
