package com.library.librarymanagementsystem.unitTests;

import com.library.librarymanagementsystem.controllers.AuthController;
import com.library.librarymanagementsystem.dtos.AuthResponseDTO;
import com.library.librarymanagementsystem.dtos.LoginDTO;
import com.library.librarymanagementsystem.dtos.RegisterDTO;
import com.library.librarymanagementsystem.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void testRegister_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("John Doe");
        registerDTO.setEmail("john.doe@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setRole("user");
        registerDTO.setContactNumber("1234567890");

        when(authService.register(any(RegisterDTO.class))).thenReturn("User registered successfully");

        ResponseEntity<String> response = authController.register(registerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    void testRegister_Failure() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("John Doe");
        registerDTO.setEmail("john.doe@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setRole("ROLE_LIBRARIAN");
        registerDTO.setContactNumber("1234567890");

        when(authService.register(any(RegisterDTO.class))).thenThrow(new RuntimeException("User already exists"));

        ResponseEntity<String> response = authController.register(registerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User already exists", response.getBody());
    }
    @Test
    void testRegister_Failure_Wrong_Role() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setName("John Doe");
        registerDTO.setEmail("john.doe@example.com");
        registerDTO.setPassword("password123");
        registerDTO.setRole("user");
        registerDTO.setContactNumber("1234567890");

        when(authService.register(any(RegisterDTO.class))).thenThrow(new RuntimeException("User already exists"));

        ResponseEntity<String> response = authController.register(registerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid role specified!", response.getBody());
    }

    @Test
    void testLogin_Success() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@example.com");
        loginDTO.setPassword("password123");

        AuthResponseDTO authResponse = new AuthResponseDTO("token");

        when(authService.login(any(LoginDTO.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponseDTO> response = authController.login(loginDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
    }

    @Test
    void testLogin_Failure() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("john.doe@example.com");
        loginDTO.setPassword("wrong-password");

        when(authService.login(any(LoginDTO.class))).thenThrow(new RuntimeException("Invalid credentials"));

        ResponseEntity<AuthResponseDTO> response = authController.login(loginDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}