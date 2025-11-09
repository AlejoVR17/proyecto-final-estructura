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
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblMessage.setText("⚠️ Ingresa usuario y contraseña");
            return;
        }

        try {
            if (username.equals("admin") && password.equals("admin")) {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/syncup/view/admin_dashboard.fxml"));
                javafx.scene.Parent root = loader.load();

                javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                javafx.scene.Scene scene = new javafx.scene.Scene(root);
                scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                stage.setTitle("SyncUp - Administrador");
                stage.setScene(scene);
                stage.show();
            }
            else if (username.equals("user") && password.equals("123")) {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/syncup/view/user_dashboard.fxml"));
                javafx.scene.Parent root = loader.load();

                com.syncup.controller.UserDashboardController controller = loader.getController();
                controller.setCurrentUser(username);

                javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                javafx.scene.Scene scene = new javafx.scene.Scene(root);
                scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                stage.setTitle("SyncUp - Usuario");
                stage.setScene(scene);
                stage.show();
            }
            else {
                lblMessage.setText("❌ Usuario o contraseña incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("⚠️ Error al cargar la interfaz.");
        }
    }


    public void onRegister(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/register.fxml", "SyncUp - Registro de Usuario");
    }
}
