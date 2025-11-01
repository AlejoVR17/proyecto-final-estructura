package com.syncup.data;

import com.syncup.model.User;
import java.util.HashMap;

public class UserRepository {

    private static final HashMap<String, User> users = new HashMap<>();

    // Agrega un nuevo usuario solo si no existe
    public static boolean addUser(User user) {
        String username = user.getUsername().toLowerCase();
        if (users.containsKey(username)) {
            return false; // Ya existe
        }
        users.put(username, user);
        return true;
    }

    // Verifica si el usuario existe
    public static boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    // Obtiene el usuario por su nombre
    public static User getUser(String username) {
        return users.get(username.toLowerCase());
    }

    // Verifica si las credenciales son correctas
    public static boolean validateLogin(String username, String password) {
        User user = users.get(username.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }

    // Devuelve todos los usuarios registrados
    public static HashMap<String, User> getUsers() {
        return users;
    }
}
