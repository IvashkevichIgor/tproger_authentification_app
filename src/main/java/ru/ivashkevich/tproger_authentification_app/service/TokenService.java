package ru.ivashkevich.tproger_authentification_app.service;

public interface TokenService {
    String generateToken(String clientId);
}
