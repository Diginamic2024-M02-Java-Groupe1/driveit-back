//package com.driveit.driveit._auth;
//
//import com.driveit.driveit._exceptions.AppException;
//import com.driveit.driveit._jwt.JwtResponseDtoOld;
//import com.driveit.driveit._jwt.JwtServiceOld;
//import com.driveit.driveit.collaborator.Collaborator;
//import com.driveit.driveit.collaborator.CollaboratorDto;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthControllerOld {
//
//    private final JwtServiceOld jwtService;
//
//    private final AuthServiceOld authService;
//
//    public AuthControllerOld(JwtServiceOld jwtService, AuthServiceOld authService) {
//        this.jwtService = jwtService;
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<JwtResponseDtoOld> authenticationToken(@RequestBody LoginUserDto loginUserDto) {
//        Collaborator authenticatedUser = authService.authenticate(loginUserDto);
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//
//        JwtResponseDtoOld jwtResponseDto = new JwtResponseDtoOld(jwtToken, jwtService.getExpirationTime());
//
//        return ResponseEntity.ok(jwtResponseDto);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<CollaboratorDto> register(@RequestBody RegisterUserDto registerRequestDto) throws AppException {
//        CollaboratorDto registeredUser = authService.register(registerRequestDto);
//        return ResponseEntity.ok(registeredUser);
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<String> verify(@RequestBody VerifyUserDto verifyUserDto) {
//        try{
//            authService.verifyUser(verifyUserDto);
//            return ResponseEntity.ok("Account verified successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/resend-verification")
//    public ResponseEntity<String> resendVerification(@RequestBody String email) {
//        try {
//            authService.resendVerificationCode(email);
//            return ResponseEntity.ok("Verification email sent successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping("/refresh-token")
//    public ResponseEntity<JwtResponseDtoOld> refreshToken(@RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//        String username;
//        try {
//            username = jwtService.extractUsername(token);
//        } catch (ExpiredJwtException e) {
//            // Handle the case where the token is expired
//            username = e.getClaims().getSubject();
//        }
//        UserDetails userDetails = authService.loadUserByUsername(username);
//
//        String newToken = jwtService.refreshToken(token, userDetails);
//        JwtResponseDtoOld jwtResponseDto = new JwtResponseDtoOld(newToken, jwtService.getExpirationTime());
//
//        return ResponseEntity.ok(jwtResponseDto);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//        jwtService.disableToken(token); // Implement this method in JwtServiceOld
//        return ResponseEntity.ok("Logged out successfully");
//    }
//}
