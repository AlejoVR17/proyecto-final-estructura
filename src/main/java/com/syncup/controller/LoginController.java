package com.syncup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.syncup.utils.SceneSwitcher;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    public void onLogin(ActionEvent event) {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.equals("admin") && pass.equals("admin")) {
            SceneSwitcher.switchScene(event, "/com/syncup/view/admin_dashboard.fxml", "SyncUp - Administrador");
        } else if (user.equals("user") && pass.equals("123")) {
            SceneSwitcher.switchScene(event, "/com/syncup/view/user_dashboard.fxml", "SyncUp - Usuario");
        } else {
            lblMessage.setText("❌ Usuario o contraseña incorrectos");
        }
    }

    public void onRegister(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/register.fxml", "SyncUp - Registro de Usuario");
    }
}
