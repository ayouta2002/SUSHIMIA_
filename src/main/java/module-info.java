module tn.esprit.zizo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires android.json;
    requires org.apache.pdfbox;

    exports tn.esprit.controllers;
    opens tn.esprit.controllers;
    opens tn.esprit.zizo to javafx.fxml;
    exports tn.esprit.zizo;
}