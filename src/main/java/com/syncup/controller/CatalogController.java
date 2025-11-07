package com.syncup.controller;

import com.syncup.data.CancionRepository;
import com.syncup.data.UserRepository;
import com.syncup.model.Cancion;
import com.syncup.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CatalogController {

    @FXML private ListView<Cancion> lstCatalog;
    @FXML private Label lblMessage;

    private User currentUser;

    public void setCurrentUser(String username) {
        currentUser = UserRepository.getUser(username);
        lstCatalog.setItems(FXCollections.observableArrayList(CancionRepository.obtenerTodas()));
    }

    @FXML
    public void onAddFavorite() {
        Cancion sel = lstCatalog.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMessage.setText("Seleccione una canción"); return; }
        currentUser.agregarFavorito(sel);
        lblMessage.setText("✅ Agregado a favoritos");
    }
}
