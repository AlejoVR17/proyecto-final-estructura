package com.syncup.controller;

import com.syncup.utils.TrieInitializer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import com.syncup.utils.SceneSwitcher;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;

    public void onGestionarCanciones(ActionEvent event) {
        Label lbl = new Label("🎵 Aquí el administrador puede agregar, editar o eliminar canciones.");
        lbl.setStyle("-fx-font-size: 18px;");
        contentArea.getChildren().setAll(lbl);
    }

    public void onGestionarUsuarios(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/syncup/view/manage_users.fxml"));
            javafx.scene.Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
            Label lbl = new Label("❌ Error al cargar la gestión de usuarios.");
            contentArea.getChildren().setAll(lbl);
        }
    }

    public void onCargarArchivo(ActionEvent event) {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        fileChooser.setTitle("Seleccionar archivo de canciones");

        java.io.File file = fileChooser.showOpenDialog(null);
        if (file == null) return;

        int count = 0;
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split(";");
                if (parts.length != 6) continue;

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String titulo = parts[1].trim();
                    String artista = parts[2].trim();
                    String genero = parts[3].trim();
                    int anio = Integer.parseInt(parts[4].trim());
                    double duracion = Double.parseDouble(parts[5].trim());

                    com.syncup.model.Cancion c = new com.syncup.model.Cancion(id, titulo, artista, genero, anio, duracion);
                    if (com.syncup.data.CancionRepository.agregarCancion(c)) {
                        // Actualizar Trie también
                        TrieInitializer.getTrie().insert(titulo);
                        count++;
                    }
                } catch (Exception ex) {
                    System.err.println("Error en línea: " + line);
                }
            }

            javafx.scene.control.Label lbl = new javafx.scene.control.Label("✅ Se cargaron " + count + " canciones correctamente.");
            lbl.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
            contentArea.getChildren().setAll(lbl);

        } catch (Exception e) {
            e.printStackTrace();
            javafx.scene.control.Label lbl = new javafx.scene.control.Label("❌ Error al leer el archivo.");
            lbl.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            contentArea.getChildren().setAll(lbl);
        }
    }


    public void onCerrarSesion(ActionEvent event) {
        SceneSwitcher.switchScene(event, "/com/syncup/view/login.fxml", "SyncUp - Inicio de Sesión");
    }
}
