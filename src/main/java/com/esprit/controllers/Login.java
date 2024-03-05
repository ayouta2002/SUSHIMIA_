package com.esprit.controllers;

import com.esprit.utils.DataSource;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class Login {

    public TextField captchaInput;
    public Label captchaLabel;
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
    private String captchaChallenge;

    @FXML
    public void initialize() {
        captchaChallenge = generateCaptcha();
        captchaLabel.setText(captchaChallenge);
    }

    // Method to generate a simple CAPTCHA challenge
    private String generateCaptcha() {
        int length = 6; // Length of the CAPTCHA challenge
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captcha.toString();
    }
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
                String userInput = captchaInput.getText().trim();
                if (userInput.equals(captchaChallenge)) {

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
                    } else if (role.equals("Livreur")) {
                        Parent page1 = FXMLLoader.load(getClass().getResource("/AcceuilLivreur.fxml"));
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
                } else {
                    showAlert("Login Failed", "Invalid CAPTCHA. Please try again.");
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
    public void OnForget(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgetPassword.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
