package com.syncup.controller;

import com.syncup.data.UserRepository;
import com.syncup.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageUsersController {

    @FXML
    private TableView<User> tblUsers;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colNombre;
    @FXML private Label lblMessage;

    @FXML
    public void initialize() {
        colUsername.setCellValueFactory(u -> new javafx.beans.property.SimpleStringProperty(u.getValue().getUsername()));
        colNombre.setCellValueFactory(u -> new javafx.beans.property.SimpleStringProperty(u.getValue().getNombre()));
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        tblUsers.setItems(FXCollections.observableArrayList(UserRepository.getUsers().values()));
    }

    @FXML
    public void onRefresh() {
        cargarUsuarios();
        lblMessage.setText("🔄 Lista actualizada");
    }

    @FXML
    public void onDeleteUser() {
        User sel = tblUsers.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblMessage.setText("⚠️ Seleccione un usuario para eliminar");
            return;
        }

        UserRepository.getUsers().remove(sel.getUsername().toLowerCase());
        cargarUsuarios();
        lblMessage.setText("✅ Usuario eliminado: " + sel.getUsername());
    }
}
