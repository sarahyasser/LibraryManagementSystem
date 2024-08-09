package com.library.librarymanagementsystem.controllers;

import com.library.librarymanagementsystem.dtos.PatronDTO;
import com.library.librarymanagementsystem.services.PatronService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patrons")
public class PatronController {
    @Autowired
    private PatronService patronService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_PATRON')")
    public ResponseEntity<List<PatronDTO>> getAllPatrons() {
        List<PatronDTO> patrons = patronService.getAllPatrons();
        return new ResponseEntity<>(patrons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_PATRON')")
    public ResponseEntity<PatronDTO> getPatronById(
            @PathVariable Long id) {
        PatronDTO patron = patronService.getPatronById(id);
        return new ResponseEntity<>(patron, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_PATRON')")
    public ResponseEntity<PatronDTO> updatePatron(
            @PathVariable @Positive(message = "ID must be positive") Long id,
            @Valid @RequestBody PatronDTO patronDTO) {
        PatronDTO updatedPatron = patronService.updatePatron(id, patronDTO);
        return new ResponseEntity<>(updatedPatron, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_LIBRARIAN','ROLE_PATRON')")
    public ResponseEntity<Void> deletePatron(
            @PathVariable Long id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
