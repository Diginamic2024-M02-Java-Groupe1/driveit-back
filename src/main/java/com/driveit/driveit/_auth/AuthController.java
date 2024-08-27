package com.driveit.driveit._auth;

import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._jwt.JwtResponseDto;
import com.driveit.driveit._jwt.JwtService;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
        Collaborator authenticatedUser = authService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        JwtResponseDto jwtResponseDto = new JwtResponseDto(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<CollaboratorDto> register(@RequestBody RegisterUserDto registerRequestDto) throws AppException {
        CollaboratorDto registeredUser = authService.register(registerRequestDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyUserDto verifyUserDto) {
        try{
            authService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(@RequestBody String email) {
        try {
            authService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<JwtResponseDto> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            // Handle the case where the token is expired
            username = e.getClaims().getSubject();
        }
        UserDetails userDetails = authService.loadUserByUsername(username);

        String newToken = jwtService.refreshToken(token, userDetails);
        JwtResponseDto jwtResponseDto = new JwtResponseDto(newToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtResponseDto);
    }
}
