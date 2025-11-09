package com.syncup.controller;

import com.syncup.data.CancionRepository;
import com.syncup.model.Cancion;
import com.syncup.utils.TrieInitializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ManageSongsController {

    @FXML private TableView<Cancion> tblSongs;
    @FXML private TableColumn<Cancion, String> colTitulo;
    @FXML private TableColumn<Cancion, String> colArtista;
    @FXML private TextField txtId, txtTitulo, txtArtista, txtGenero, txtAnio, txtDuracion;
    @FXML private Label lblMessage;

    public void initialize() {
        // inicializar columnas (si usas PropertyValueFactory, crear getters getTitulo, etc.)
        ObservableList<Cancion> data = FXCollections.observableArrayList(CancionRepository.obtenerTodas());
        tblSongs.setItems(data);
    }

    @FXML
    public void onAdd() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String titulo = txtTitulo.getText();
            String artista = txtArtista.getText();
            String genero = txtGenero.getText();
            int anio = Integer.parseInt(txtAnio.getText());
            double dur = Double.parseDouble(txtDuracion.getText());
            Cancion c = new Cancion(id,titulo,artista,genero,anio,dur);
            if (CancionRepository.agregarCancion(c)) {
                lblMessage.setText("✅ Cancion agregada");
                tblSongs.getItems().add(c);
                TrieInitializer.getTrie().insert(c.getTitulo());
            } else lblMessage.setText("❌ ID ya existe");
        } catch (Exception e) {
            lblMessage.setText("❌ Datos inválidos");
        }
    }

    @FXML
    public void onDelete() {
        Cancion sel = tblSongs.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMessage.setText("Seleccione una canción"); return; }
        if (CancionRepository.eliminarCancion(sel.getId())) {
            tblSongs.getItems().remove(sel);
            lblMessage.setText("✅ Eliminada");
        } else lblMessage.setText("❌ Error al eliminar");
    }
}

