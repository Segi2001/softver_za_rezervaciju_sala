/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.SoftverZaRezervacijuSalaApplicationTests;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Osoba;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.OsobaRepository;

/**
 *
 * @author Korisnik
 */
public class OsobaImplTest extends SoftverZaRezervacijuSalaApplicationTests {
    
   OsobaImpl osobaImpl;
   
   @Autowired
   ApplicationContext applicationContext;
    
   
    
    @BeforeEach
    public void setUp() {
        osobaImpl = new OsobaImpl(applicationContext.getBean(OsobaRepository.class));
    }
    
    @AfterEach
    public void tearDown() {
        osobaImpl = null;
    }

  
    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindOsobaByEmail() {

        Osoba osoba = osobaImpl.findOsobaByEmail("bogdanovic@gmail.com");
        assertEquals(7, osoba.getId());

        assertEquals("bogdanovic@gmail.com", osoba.getEmail());
    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindOsobaByEmailNePostoji() {

        Osoba osoba = osobaImpl.findOsobaByEmail("boban@gmail.com");
        assertNull(osoba);
    }
    
}
