package ro.ubb.boardgameapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.ubb.boardgameapp.controller.AuthenticationRequest;
import ro.ubb.boardgameapp.controller.AuthenticationResponse;
import ro.ubb.boardgameapp.controller.RegisterRequest;
import ro.ubb.boardgameapp.model.User;
import ro.ubb.boardgameapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
//    public AuthenticationResponse register(RegisterRequest request) {
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .gender(request.getGender())
//                .birthday(request.getBirthday())
//                .email(request.getEmail())
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .build();
//    repository.save(user);
//    var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//        .token(jwtToken)
//                .build();
//    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findOptionalByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
