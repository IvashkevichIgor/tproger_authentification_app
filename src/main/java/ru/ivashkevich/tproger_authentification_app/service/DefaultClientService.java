package ru.ivashkevich.tproger_authentification_app.service;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.ivashkevich.tproger_authentification_app.dao.ClientEntity;
import ru.ivashkevich.tproger_authentification_app.dao.ClientRepository;
import ru.ivashkevich.tproger_authentification_app.exception.LoginException;
import ru.ivashkevich.tproger_authentification_app.exception.RegistrationException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientService{

    private final ClientRepository clientRepository;

    @Override
    public void register(String clientId, String clientSecret) {
        if(clientRepository.findById(clientId).isPresent()){
            throw new RegistrationException("Client with ID: " + clientId + "already registered");
        }

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        clientRepository.save(new ClientEntity(clientId, hash));
    }

    @Override
    public void checkCredentials(String clientId, String clientSecret) {
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);

        if (optionalClientEntity.isEmpty()){
            throw new LoginException("Client with ID: " + clientId + " not found");
        }

        ClientEntity clientEntity = optionalClientEntity.get();

        if (!BCrypt.checkpw(clientSecret, clientEntity.getHash())){
            throw new LoginException("Password is incorrect");
        }
    }
}
