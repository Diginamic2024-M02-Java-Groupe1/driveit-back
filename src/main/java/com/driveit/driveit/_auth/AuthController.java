package com.driveit.driveit._auth;

import com.driveit.driveit._jwt.JwtResponseDto;
import com.driveit.driveit._jwt.JwtService;
import com.driveit.driveit.collaborator.Collaborator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticationToken(@RequestBody LoginUserDto loginUserDto) {
        Collaborator authenticatedUser = authService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        JwtResponseDto jwtResponseDto = new JwtResponseDto(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterUserDto registerRequestDto) {
        authService.register(registerRequestDto);
    }
}
