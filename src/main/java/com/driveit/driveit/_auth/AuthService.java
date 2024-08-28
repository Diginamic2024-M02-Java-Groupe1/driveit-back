package com.driveit.driveit._auth;

import com.driveit.driveit._email.EmailService;
import com.driveit.driveit._exceptions.AppException;
import com.driveit.driveit._utils.Mapper;
import com.driveit.driveit.collaborator.Collaborator;
import com.driveit.driveit.collaborator.CollaboratorDto;
import com.driveit.driveit.collaborator.CollaboratorRepository;
import com.driveit.driveit.collaborator.CollaboratorService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
    public final CollaboratorRepository collaboratorRepository;
    public final CollaboratorService collaboratorService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(
            CollaboratorRepository collaboratorRepository,
            CollaboratorService collaboratorService,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorService = collaboratorService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public CollaboratorDto register(RegisterUserDto registerUserDto) throws AppException {
        Collaborator user = new Collaborator(
                registerUserDto.email(),
                passwordEncoder.encode(registerUserDto.password()),
                registerUserDto.firstName(),
                registerUserDto.lastName()
        );
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpirationDate(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);
        return Mapper.collaboratorToDto(collaboratorRepository.save(user));
    }

    public Collaborator authenticate(LoginUserDto loginUserDto) {
        Collaborator user = collaboratorRepository.findByEmail(loginUserDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(!user.isEnabled()) {
            throw new RuntimeException("Account not verified. Please verify your account");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.email(),
                        loginUserDto.password()
                )
        );

        return user;
    }

    public void verifyUser(VerifyUserDto verifyUserDto) throws AppException {
        Optional<Collaborator> optionalUser = collaboratorRepository.findByEmail(verifyUserDto.email());
        if(optionalUser.isPresent()){
            Collaborator user = optionalUser.get();
            if(user.getVerificationCodeExpirationDate().isBefore(LocalDateTime.now())) {
                throw new AppException("Verification code expired");
            }
            if (user.getVerificationCode().equals(verifyUserDto.verificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpirationDate(null);
                collaboratorRepository.save(user);
            } else {
                throw new AppException("Invalid verification code");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void resendVerificationCode(String email) throws AppException {
        System.out.println(email);
        Optional<Collaborator> optionalUser = collaboratorRepository.findByEmail(email);
        System.out.println(optionalUser);
        if(optionalUser.isPresent()){
            Collaborator user = optionalUser.get();
            if(user.isEnabled()) {
                throw new AppException("Account already verified");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpirationDate(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(user);
            collaboratorRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void sendVerificationEmail(Collaborator user) throws AppException {
        String subject = "DriveIt - Account Verification";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
        + "<body style=\"font-family: Arial, sans-serif; text-align: center;\">"
        + "<h2 style=\"color: #4CAF50;\">Welcome to DriveIt!</h2>"
        + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
        + "<p style=\"font-size: 24px; font-weight: bold; color: #333;\">" + verificationCode + "</p>"
        + "<p style=\"font-size: 14px; color: #777;\">This code will expire in 15 minutes.</p>"
        + "</body>"
        + "</html>";

        try{
            emailService.sendEmail(user.getEmail(), subject, htmlMessage);
        } catch (Exception e) {
            throw new AppException("Failed to send verification email");
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public Collaborator loadUserByUsername(String email) {
        return collaboratorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
