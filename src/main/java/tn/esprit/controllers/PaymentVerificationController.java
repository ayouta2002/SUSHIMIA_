package tn.esprit.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentVerificationController {

    private OrderCheckOutController checkoutController = new OrderCheckOutController();
    private String payment_Id = "";
    @FXML
    private Button verifyBtn;

    public void setup(OrderCheckOutController controller, String paymentID) {
        this.checkoutController = controller;
        this.payment_Id = paymentID;
    }

    @FXML
    private void OnVerify(ActionEvent event) {
        try {
            URL url = new URL("https://developers.flouci.com/api/verify_payment/" + this.payment_Id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("apppublic", "b9e12676-1459-41a1-b608-48d7c2cedcb0");
            connection.setRequestProperty("appsecret", "9947737a-ee91-4119-b773-fc3a190d13af");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                String status = jsonResponse.getJSONObject("result").getString("status");
                switch (status) {
                    case "SUCCESS":
                        Alert alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
                        alertSuccess.setHeaderText("Payment successful, You can close this now !");
                        alertSuccess.showAndWait();
                        this.checkoutController.successfulPayment();
                        this.closeUI();
                        break;
                    default:
                        Alert alertFailed = new Alert(Alert.AlertType.ERROR);
                        alertFailed.setHeaderText("Payment denied, You can close this now !");
                        alertFailed.showAndWait();
                        this.closeUI();
                        break;
                }
            } catch (JSONException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No payments yet");
                alert.showAndWait();
            }
        } catch (IOException e) {
        }
    }
    private void closeUI(){
        Stage thisPage = (Stage) verifyBtn.getScene().getWindow();
        thisPage.close();
    }
}
