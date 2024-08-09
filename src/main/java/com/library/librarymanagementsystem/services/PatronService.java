package com.library.librarymanagementsystem.services;

import com.library.librarymanagementsystem.customExceptions.PatronNotFoundException;
import com.library.librarymanagementsystem.dtos.PatronDTO;
import com.library.librarymanagementsystem.models.Patron;
import com.library.librarymanagementsystem.repositories.PatronRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(patron -> modelMapper.map(patron, PatronDTO.class))
                .collect(Collectors.toList());
    }

    @Cacheable(value="patrons", key="#id")
    public PatronDTO getPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found"));
        return modelMapper.map(patron, PatronDTO.class);
    }

    @Transactional
    public PatronDTO addPatron(PatronDTO patronDTO) {
        Patron patron = modelMapper.map(patronDTO, Patron.class);
        Patron savedPatron = patronRepository.save(patron);
        return modelMapper.map(savedPatron, PatronDTO.class);
    }

    @Transactional
    public PatronDTO updatePatron(Long id, PatronDTO patronDTO) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found"));

        modelMapper.map(patronDTO, existingPatron);
        // If a new password is provided, encode it
        if (patronDTO.getPassword() != null && !patronDTO.getPassword().isEmpty()) {
            existingPatron.setPassword(encoder.encode(patronDTO.getPassword()));
        }
        Patron updatedPatron = patronRepository.save(existingPatron);
        return modelMapper.map(updatedPatron, PatronDTO.class);
    }

    @Transactional
    public void deletePatron(Long id) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found"));

        patronRepository.delete(existingPatron);
    }
}