package com.syncup.test;

import com.syncup.data.UserRepository;
import com.syncup.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    @BeforeEach
    public void setup() { /* limpiar */ }

    @Test
    public void testAddAndExists() {
        User u = new User("pepe","123","Pepe");
        boolean added = UserRepository.addUser(u);
        assertTrue(added);
        assertTrue(UserRepository.userExists("pepe"));
    }

    @Test
    public void testValidateLogin() {
        User u = new User("ana","pwd","Ana");
        UserRepository.addUser(u);
        assertTrue(UserRepository.validateLogin("ana","pwd"));
        assertFalse(UserRepository.validateLogin("ana","bad"));
    }
}
