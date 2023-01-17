package ru.ivashkevich.tproger_authentification_app.model;

import lombok.Value;

@Value
public class Client {
    String clientId;
    String clientSecret;
}
