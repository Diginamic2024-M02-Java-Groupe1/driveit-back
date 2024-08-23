package com.driveit.driveit._auth;

import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.collaborator.CollaboratorService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public final CollaboratorRepository collaboratorRepository;
    public final CollaboratorService collaboratorService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            CollaboratorRepository collaboratorRepository,
            CollaboratorService collaboratorService,
            AuthenticationManager authenticationManager
    ) {
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorService = collaboratorService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterUserDto registerUserDto) {
        collaboratorService.saveCollaborator(registerUserDto);
    }

    public Collaborator login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.email(),
                        loginUserDto.password()
                )
        );

        return collaboratorRepository.findByEmail(loginUserDto.email()).orElseThrow(
                () -> new RuntimeException("Collaborator not found")
        );
    }
}
