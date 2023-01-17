package ru.ivashkevich.tproger_authentification_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ivashkevich.tproger_authentification_app.exception.LoginException;
import ru.ivashkevich.tproger_authentification_app.exception.RegistrationException;
import ru.ivashkevich.tproger_authentification_app.model.Client;
import ru.ivashkevich.tproger_authentification_app.model.ErrorResponse;
import ru.ivashkevich.tproger_authentification_app.model.TokenResponse;
import ru.ivashkevich.tproger_authentification_app.service.ClientService;
import ru.ivashkevich.tproger_authentification_app.service.TokenService;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Client client){
        clientService.register(client.getClientId(), client.getClientSecret());
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody Client client){
        clientService.checkCredentials(client.getClientId(), client.getClientSecret());
        return new TokenResponse(tokenService.generateToken(client.getClientId()));
    }

    @ExceptionHandler({RegistrationException.class, LoginException.class})
    public ResponseEntity<ErrorResponse> handleClientRegistrationException(RuntimeException ex){
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}
