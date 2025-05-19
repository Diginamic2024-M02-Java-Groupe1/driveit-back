package com.driveit.driveit._auth;


import com.driveit.driveit._auth.dto.ForgotPasswordRequest;
import com.driveit.driveit._auth.dto.LoginRequest;
import com.driveit.driveit._auth.dto.ResetPasswordRequest;
import com.driveit.driveit._auth.dto.TokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.login(loginRequest);

        addAuthCookies(response, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.requestPasswordReset(request);
        return ResponseEntity.ok("Si ces entrées correspondent à un utilisateur, " +
                "un email de réinitialisation a été envoyé");
    }

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

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPasswordWithToken(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Mot de passe  réinitialisé avec succès");
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractCookieValue(request, "refresh_token");
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);
        addAuthCookies(response, tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractCookieValue(request, "refresh_token");
        if (refreshToken != null) {
            authService.logout(refreshToken);
        }
        deleteCookies(response);
        return ResponseEntity.ok("Déconnecté avec succès");
    }

    private void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        response.addHeader("Set-Cookie", "access_token=" + accessToken
                + "; Path=/; Max-Age=3600; HttpOnly; SameSite=None; Secure");
        response.addHeader("Set-Cookie", "refresh_token=" + refreshToken
                + "; Path=/; Max-Age=604800; HttpOnly; SameSite=None; Secure");
    }

    private void deleteCookies(HttpServletResponse response) {
        response.addHeader("Set-Cookie", "access_token=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax");
        response.addHeader("Set-Cookie", "refresh_token=; Path=/; Max-Age=0; HttpOnly; SameSite=Lax");
    }

    private String extractCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
