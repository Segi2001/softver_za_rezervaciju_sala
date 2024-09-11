/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ApiKey;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.ApiKeyRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class ApiKeyService {

       
    private EncryptionService encryptionService;
    private ApiKeyRepository apiKeyRepository;
    
    @Autowired
    public ApiKeyService(ApiKeyRepository apiKeyRepository,EncryptionService encryptionService){
        this.apiKeyRepository = apiKeyRepository;
        this.encryptionService = encryptionService;
    }

 

    public void saveApiKey(String rawKey) {
        String encryptedKey = encryptionService.encrypt(rawKey);
        ApiKey apiKey = new ApiKey();
        apiKey.setValue(encryptedKey);
        
        apiKeyRepository.save(apiKey);
    }

    public boolean isValidApiKey(String rawKey) {
        Iterable<ApiKey> apiKeys = apiKeyRepository.findAll();
        for (ApiKey apiKey : apiKeys) {
            if (encryptionService.matches(rawKey, apiKey.getValue())) {
                return true;
            }
        }
        return false;
    }
}