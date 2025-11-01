package com.syncup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import com.syncup.utils.SceneSwitcher;

public class UserDashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label lblInfo;

    // Métodos de botones
    public void onBuscar(ActionEvent event) {
        Label lbl = new Label("🔎 Aquí podrás buscar canciones...");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onFavoritos(ActionEvent event) {
        Label lbl = new Label("⭐ Tus canciones favoritas aparecerán aquí...");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onDescubrimiento(ActionEvent event) {
        Label lbl = new Label("🎶 Tu descubrimiento semanal se está generando...");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onCerrarSesion(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
