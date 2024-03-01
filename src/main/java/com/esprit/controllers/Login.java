package com.esprit.controllers;

import com.esprit.utils.DataSource;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ftemail;

   @FXML
    private Button login;

    @FXML
    private PasswordField ftmot_de_passe;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private Label ShownPassword;
   @FXML
    void passwordFieldKeyTyped(KeyEvent event) {
        ShownPassword.textProperty().bind(Bindings.concat(ftmot_de_passe.getText()));

    }

    @FXML
    void onLoginButtonClick(MouseEvent event) {
        String email = ftemail.getText();
        String mot_de_passe = ftmot_de_passe.getText();

        Connection connection = DataSource.getInstance().getCnx();  // Get connection

        try {
            String query = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";  // Query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, mot_de_passe);

            ResultSet resultSet = preparedStatement.executeQuery();  // Execute query

            if (resultSet.next()) {  // If user exists
                String role = resultSet.getString("role");  // Get role from database

                try {  // Handle FXML loading exceptions
                    if (role.equals("Admin")) {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/AcceuilAdmin.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } else if (role.equals("Client")) {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/Acceuil.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        System.out.println("Unknown user role: " + role);  // Handle unknown roles
                    }
                } catch (IOException e) {  // Catch FXML loading exceptions
                    System.out.println("Error loading FXML: " + e.getMessage());
                }
            } else {  // User not found
                System.out.println("Invalid credentials.");  // Display error message
                // ... handle incorrect credentials (e.g., display error message to user)
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @FXML
    void toggleButton(ActionEvent event) {
        if(toggleButton.isSelected()){
            ShownPassword.setVisible(true);
            ShownPassword.textProperty().bind(Bindings.concat(ftmot_de_passe.getText()));
            toggleButton.setText("Hide");
        }else {
            ShownPassword.setVisible(false);
            toggleButton.setText("Show");
        }
    }
    @FXML
    void opt(MouseEvent event) {

    }

}
