package com.syncup.controller;

import com.syncup.data.DataInitializer;
import com.syncup.data.PlaylistService;
import com.syncup.data.RadioService;
import com.syncup.data.UserRepository;
import com.syncup.graph.GrafoDeSimilitud;
import com.syncup.model.Cancion;
import com.syncup.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import com.syncup.utils.SceneSwitcher;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class UserDashboardController {

    @FXML
    private StackPane contentArea;

    private User currentUser;

    public void setCurrentUser(String username) {
        this.currentUser = UserRepository.getUser(username);
    }

    public void onBuscar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/syncup/view/catalog.fxml"));
            Parent catalogView = loader.load();

            CatalogController controller = loader.getController();
            controller.setCurrentUser(currentUser.getUsername());

            contentArea.getChildren().setAll(catalogView);
        } catch (IOException e) {
            e.printStackTrace();
            Label lbl = new Label("❌ Error al cargar el catálogo de canciones.");
            lbl.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            contentArea.getChildren().setAll(lbl);
        }
    }

    public void onFavoritos(ActionEvent event) {
        if (currentUser == null) {
            Label lbl = new Label("⚠️ Debes iniciar sesión para ver tus favoritos.");
            lbl.setStyle("-fx-font-size: 16px;");
            contentArea.getChildren().setAll(lbl);
            return;
        }

        if (currentUser.getListaFavoritos().isEmpty()) {
            Label lbl = new Label("⭐ No tienes canciones en favoritos aún.");
            lbl.setStyle("-fx-font-size: 16px;");
            contentArea.getChildren().setAll(lbl);
            return;
        }

        ListView<Cancion> lstFavoritos = new ListView<>();
        lstFavoritos.getItems().setAll(currentUser.getListaFavoritos());
        lstFavoritos.setPrefHeight(250);

        Button btnRadio = new Button("🎧 Iniciar Radio con selección");
        btnRadio.setStyle("-fx-font-size: 14px; -fx-background-color: #0078D7; -fx-text-fill: white;");
        Label lblResultado = new Label();
        lblResultado.setWrapText(true);
        lblResultado.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        btnRadio.setOnAction(e -> {
            com.syncup.model.Cancion seleccionada = lstFavoritos.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                lblResultado.setText("⚠️ Selecciona una canción primero.");
                return;
            }

            GrafoDeSimilitud grafo = DataInitializer.initSimilitud();
            RadioService radioService = new RadioService(grafo);
            List<com.syncup.model.Cancion> cola = radioService.generarRadio(seleccionada.getId());

            StringBuilder sb = new StringBuilder("🎶 Radio basada en: " + seleccionada.getTitulo() + "\n\n");
            if (cola.isEmpty()) {
                sb.append("No hay canciones similares registradas.");
            } else {
                cola.stream().limit(10).forEach(c ->
                        sb.append("- ").append(c.getTitulo())
                                .append(" - ").append(c.getArtista()).append("\n"));
            }
            lblResultado.setText(sb.toString());
        });

        VBox layout = new VBox(10,
                new Label("⭐ Canciones favoritas de " + currentUser.getNombre() + ":"),
                lstFavoritos,
                btnRadio,
                lblResultado
        );
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(20));

        contentArea.getChildren().setAll(layout);
    }

    public void onDescubrimiento(ActionEvent event) {
        if (currentUser == null) {
            Label lbl = new Label("⚠️ Debes iniciar sesión para generar tu descubrimiento semanal.");
            contentArea.getChildren().setAll(lbl);
            return;
        }

        GrafoDeSimilitud grafo = com.syncup.data.DataInitializer.initSimilitud();
        PlaylistService playlistService = new PlaylistService(grafo);
        List<Cancion> descubrimiento = playlistService.generarDescubrimientoSemanal(currentUser);

        StringBuilder sb = new StringBuilder("🎶 Descubrimiento Semanal de " + currentUser.getNombre() + ":\n\n");
        descubrimiento.forEach(c -> sb.append("- ").append(c.getTitulo()).append(" - ").append(c.getArtista()).append("\n"));

        Label lbl = new Label(sb.toString());
        lbl.setStyle("-fx-font-size: 16px;");
        lbl.setWrapText(true);
        contentArea.getChildren().setAll(lbl);
    }

    public void onConexiones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/syncup/view/friends.fxml"));
            Parent friendsView = loader.load();

            FriendsController controller = loader.getController();
            controller.setCurrentUser(currentUser);

            contentArea.getChildren().setAll(friendsView);
        } catch (IOException e) {
            e.printStackTrace();
            Label lbl = new Label("❌ Error al cargar la vista de conexiones.");
            contentArea.getChildren().setAll(lbl);
        }
    }

    public void onCerrarSesion(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
