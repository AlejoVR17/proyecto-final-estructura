package com.syncup.controller;

import com.syncup.data.DataInitializer;
import com.syncup.graph.GrafoSocial;
import com.syncup.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import com.syncup.data.UserRepository;
import javafx.scene.control.*;

import java.util.Set;

public class FriendsController {
    @FXML
    private TableView<User> tblUsuarios;
    @FXML private TableColumn<User, String> colUsuario;
    @FXML private TableColumn<User, String> colNombre;
    @FXML private TableColumn<User, String> colEstado;
    @FXML private Label lblResultado;

    private User currentUser;
    private GrafoSocial grafoSocial;

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.grafoSocial = DataInitializer.initSocial();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        colUsuario.setCellValueFactory(u -> new javafx.beans.property.SimpleStringProperty(u.getValue().getUsername()));
        colNombre.setCellValueFactory(u -> new javafx.beans.property.SimpleStringProperty(u.getValue().getNombre()));
        colEstado.setCellValueFactory(u -> new javafx.beans.property.SimpleStringProperty(
                grafoSocial.neighbors(currentUser.getUsername()).contains(u.getValue().getUsername()) ? "Siguiendo" : "No seguido"
        ));

        tblUsuarios.setItems(FXCollections.observableArrayList(UserRepository.getUsers().values()));
    }

    @FXML
    public void onSeguir() {
        User seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblResultado.setText("⚠️ Selecciona un usuario para seguir.");
            return;
        }
        if (seleccionado.equals(currentUser)) {
            lblResultado.setText("❌ No puedes seguirte a ti mismo.");
            return;
        }
        grafoSocial.addEdge(currentUser.getUsername(), seleccionado.getUsername());
        lblResultado.setText("✅ Ahora sigues a " + seleccionado.getNombre());
        cargarUsuarios();
    }

    @FXML
    public void onDejarSeguir() {
        User seleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblResultado.setText("⚠️ Selecciona un usuario para dejar de seguir.");
            return;
        }
        grafoSocial.removeEdge(currentUser.getUsername(), seleccionado.getUsername());
        lblResultado.setText("👋 Has dejado de seguir a " + seleccionado.getNombre());
        cargarUsuarios();
    }

    @FXML
    public void onSugerencias() {
        Set<String> sugerencias = grafoSocial.bfs(currentUser.getUsername(), 2);
        if (sugerencias.isEmpty()) {
            lblResultado.setText("💡 No hay sugerencias disponibles.");
        } else {
            lblResultado.setText("💡 Sugerencias para seguir:\n" + String.join(", ", sugerencias));
        }
    }
}
