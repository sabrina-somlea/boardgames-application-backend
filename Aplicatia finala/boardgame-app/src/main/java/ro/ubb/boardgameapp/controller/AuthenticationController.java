package ro.ubb.boardgameapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.boardgameapp.config.AuthenticationRequest;
import ro.ubb.boardgameapp.config.AuthenticationResponse;
import ro.ubb.boardgameapp.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins ="*")
public class AuthenticationController {

    private final AuthenticationService service;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register (
//         @RequestBody RegisterRequest request
//    ) {
//    return ResponseEntity.ok(service.register(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
