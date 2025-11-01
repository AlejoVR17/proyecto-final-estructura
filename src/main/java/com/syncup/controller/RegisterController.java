package com.syncup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.syncup.utils.SceneSwitcher;

public class RegisterController {

    @FXML private TextField txtName;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

    public void onRegister(ActionEvent event) {
        if (txtName.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            lblMessage.setText("❌ Todos los campos son obligatorios");
            lblMessage.setStyle("-fx-text-fill: red;");
        } else {
            lblMessage.setText("✅ Usuario registrado correctamente");
            lblMessage.setStyle("-fx-text-fill: green;");
        }
    }

    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
