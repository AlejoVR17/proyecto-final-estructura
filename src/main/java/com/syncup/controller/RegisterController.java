package com.syncup.controller;

import com.syncup.data.UserRepository;
import com.syncup.model.User;
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
        String name = txtName.getText().trim();
        String username = txtUsername.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("❌ Todos los campos son obligatorios");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        User newUser = new User(username, password, name);
        if (UserRepository.addUser(newUser)) {
            lblMessage.setText("✅ Usuario registrado correctamente");
            lblMessage.setStyle("-fx-text-fill: green;");
        } else {
            lblMessage.setText("⚠️ El usuario ya existe");
            lblMessage.setStyle("-fx-text-fill: orange;");
        }
    }

    public void onBack(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
