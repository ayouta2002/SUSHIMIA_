package com.esprit.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController {

    private WebEngine webengine ;

    @FXML
    private WebView webview;

    public void initialize() {
        webengine = webview.getEngine();
        webengine.load(String.valueOf(getClass().getResource("/map/index.html")));
    }
    private class JavaApp {

        public void exit() {
            Platform.exit();
        }

    }
}
