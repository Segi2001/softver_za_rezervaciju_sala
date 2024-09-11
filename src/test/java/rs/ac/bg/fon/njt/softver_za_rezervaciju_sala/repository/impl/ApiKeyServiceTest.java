/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.SoftverZaRezervacijuSalaApplicationTests;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ApiKey;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.ApiKeyRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Korisnik
 */
public class ApiKeyServiceTest extends SoftverZaRezervacijuSalaApplicationTests{
    
    ApiKeyService apiKeyService;
    
    @Autowired
    ApplicationContext applicationContext;
    
    
    
    
  
    @BeforeEach
    public void setUp() {
        apiKeyService = new ApiKeyService(applicationContext.getBean(ApiKeyRepository.class),applicationContext.getBean(EncryptionService.class));
    }
    
    @AfterEach
    public void tearDown() {
        apiKeyService = null;
    }

    
    @Test
    @Sql(scripts = "/delete_all_data.sql",config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSaveApiKey() {
        
        apiKeyService.saveApiKey("jabuka");
        ApiKeyRepository apiKeyRepository = applicationContext.getBean(ApiKeyRepository.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean exist = false;
         Iterable<ApiKey> apiKeys = apiKeyRepository.findAll();
        for (ApiKey apiKey : apiKeys) {
            if (passwordEncoder.matches("jabuka", apiKey.getValue())) {
               exist=true;
               break;
            }
        }
        assertTrue(exist);
        
        
      
        
        
    }
    
    
    @Test
    @Sql(scripts = "/insert_api_key.sql",config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql",config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testIsValidApiKey(){
        assertTrue(apiKeyService.isValidApiKey("jabuka"));
    }
  
    
}
