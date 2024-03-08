package com.linkstart.api.service;

import com.linkstart.api.configuration.JwtService;
import com.linkstart.api.exception.NotFoundException;
import com.linkstart.api.model.dto.AuthResponseDto;
import com.linkstart.api.model.dto.ClientDto;
import com.linkstart.api.model.entity.Client;
import com.linkstart.api.model.entity.Role;
import com.linkstart.api.repo.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ClientRepo clientRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto register(ClientDto clientDto) {
        if (clientRepo.existsByClientName(clientDto.getClientName()))
            throw new RuntimeException("Username is taken");

        Client client = Client.builder()
                .clientName(clientDto.getClientName())
                .password(passwordEncoder.encode(clientDto.getPassword()))
                .role(Role.USER)
                .build();

        Client savedClient = clientRepo.save(client);

        String token = jwtService.generateToken(savedClient);
        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(ClientDto clientDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        clientDto.getClientName(),
                        clientDto.getPassword()
                )
        );

        Client client = clientRepo.findByClientName(clientDto.getClientName())
                .orElseThrow(() -> new NotFoundException(clientDto.getClientName(), Client.class));

        String token = jwtService.generateToken(client);
        return new AuthResponseDto(token);
    }
}
