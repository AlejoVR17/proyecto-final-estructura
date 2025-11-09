package com.syncup.controller;

import com.syncup.data.CancionRepository;
import com.syncup.data.UserRepository;
import com.syncup.model.Cancion;
import com.syncup.model.User;
import com.syncup.utils.TrieInitializer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogController {

    @FXML private ListView<Cancion> lstCatalog;
    @FXML private Label lblMessage;
    @FXML private TextField txtBuscar;
    @FXML private ListView<String> lstSugerencias;

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

    @FXML
    public void onBuscarPorPrefijo() {
        String prefijo = txtBuscar.getText().trim();
        if (prefijo.isEmpty()) {
            lstSugerencias.getItems().clear();
            return;
        }
        List<String> resultados = TrieInitializer.getTrie().autocomplete(prefijo);
        lstSugerencias.getItems().setAll(resultados);
    }

    @FXML
    public void initialize() {
        TrieInitializer.cargarCanciones();
        lstSugerencias.setOnMouseClicked(event -> {
            String seleccionado = lstSugerencias.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                List<Cancion> coincidencias = com.syncup.data.CancionRepository.obtenerTodas().stream()
                        .filter(c -> c.getTitulo().equalsIgnoreCase(seleccionado))
                        .collect(Collectors.toList());
                lstCatalog.getItems().setAll(coincidencias);
                lblMessage.setText("🎵 Mostrando resultado: " + seleccionado);
            }
        });
    }
}
