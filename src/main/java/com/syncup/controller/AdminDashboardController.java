package com.syncup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import com.syncup.utils.SceneSwitcher;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;

    public void onGestionarCanciones(ActionEvent event) {
        Label lbl = new Label("🎵 Aquí el administrador puede agregar, editar o eliminar canciones.");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onGestionarUsuarios(ActionEvent event) {
        Label lbl = new Label("👥 Aquí puedes ver y eliminar usuarios del sistema.");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onCargarArchivo(ActionEvent event) {
        Label lbl = new Label("📂 Carga masiva de canciones desde un archivo .txt (pendiente de implementación).");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onCerrarSesion(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
