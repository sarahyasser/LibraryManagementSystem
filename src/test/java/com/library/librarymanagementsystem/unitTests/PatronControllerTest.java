package com.library.librarymanagementsystem.unitTests;


import com.library.librarymanagementsystem.controllers.PatronController;
import com.library.librarymanagementsystem.customExceptions.PatronNotFoundException;
import com.library.librarymanagementsystem.dtos.PatronDTO;
import com.library.librarymanagementsystem.services.PatronService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatronControllerTest {

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testGetAllPatrons_Success() {
        PatronDTO patron1 = new PatronDTO();
        patron1.setName("Patron 1");
        PatronDTO patron2 = new PatronDTO();
        patron2.setName("Patron 2");
        List<PatronDTO> patronList = Arrays.asList(patron1, patron2);

        when(patronService.getAllPatrons()).thenReturn(patronList);

        ResponseEntity<List<PatronDTO>> response = patronController.getAllPatrons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patronList, response.getBody());
    }
    

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testGetPatronById_Success() {
        PatronDTO patron = new PatronDTO();
        patron.setName("Patron 1");
        when(patronService.getPatronById(1L)).thenReturn(patron);

        ResponseEntity<PatronDTO> response = patronController.getPatronById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patron, response.getBody());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testUpdatePatron_Success() {
        PatronDTO patronDTO = new PatronDTO();
        patronDTO.setName("Updated Patron");
        when(patronService.updatePatron(1L, patronDTO)).thenReturn(patronDTO);

        ResponseEntity<PatronDTO> response = patronController.updatePatron(1L, patronDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patronDTO, response.getBody());
    }

    @Test
    @WithMockUser(authorities = "ROLE_LIBRARIAN")
    void testDeletePatron_Success() {
        ResponseEntity<Void> response = patronController.deletePatron(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(patronService, Mockito.times(1)).deletePatron(1L);
    }




}

