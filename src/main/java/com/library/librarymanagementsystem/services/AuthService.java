package com.library.librarymanagementsystem.services;

import com.library.librarymanagementsystem.dtos.AuthResponseDTO;
import com.library.librarymanagementsystem.dtos.LoginDTO;
import com.library.librarymanagementsystem.dtos.RegisterDTO;
import com.library.librarymanagementsystem.models.Librarian;
import com.library.librarymanagementsystem.models.Patron;
import com.library.librarymanagementsystem.models.Role;
import com.library.librarymanagementsystem.models.User;
import com.library.librarymanagementsystem.repositories.UserRepository;
import com.library.librarymanagementsystem.security.JwtService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public String register(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        User user;
        try {
            Role roleType = Role.valueOf(registerDTO.getRole().toUpperCase());
            if (roleType == Role.ROLE_PATRON) {
                user = new Patron();
                modelMapper.map(registerDTO, user);
                user.setRole(Role.ROLE_PATRON);
                user.setPassword(encoder.encode(registerDTO.getPassword()));
            } else if (roleType == Role.ROLE_LIBRARIAN) {
                user = new Librarian();
                modelMapper.map(registerDTO, user);
                user.setRole(Role.ROLE_LIBRARIAN);
                user.setPassword(encoder.encode(registerDTO.getPassword()));
            } else {
                throw new RuntimeException("Invalid role specified!");
            }
            user.setRole(roleType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role specified!");
        }

//        user.setEmail(registerDTO.getEmail());
//        user.setPassword(encoder.encode(registerDTO.getPassword()));

        userRepository.save(user);
        return "User registered successfully";
    }


    public AuthResponseDTO login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication.getName());
        return new AuthResponseDTO(token);
    }
}
