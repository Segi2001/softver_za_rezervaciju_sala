/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.SoftverZaRezervacijuSalaApplicationTests;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Zaposleni;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniUNastavi;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.ZaposleniRepository;

/**
 *
 * @author Korisnik
 */
public class ZaposleniImplTest extends SoftverZaRezervacijuSalaApplicationTests {

    ZaposleniImpl zaposleniImpl;
    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    public void setUp() {
        zaposleniImpl = new ZaposleniImpl(applicationContext.getBean(ZaposleniRepository.class));
    }

    @AfterEach
    public void tearDown() {
        zaposleniImpl = null;
    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiSveZaposlene() {
        List<Zaposleni> listaZaposleni = zaposleniImpl.vratiSveZaposlene();
        assertEquals(listaZaposleni.size(), 2);
        assertEquals(6, listaZaposleni.get(0).getId());
        assertEquals(7, listaZaposleni.get(1).getId());

    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiZaposlenogSaDatimEmailom() {

        Zaposleni zaposleni = zaposleniImpl.vratiZaposlenogSaDatimEmailom("bogdanovic@gmail.com");
        assertEquals(7, zaposleni.getId());

        assertEquals("bogdanovic@gmail.com", zaposleni.getEmail());
    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiZaposlenogSaDatimEmailomNePostoji() {

        Zaposleni zaposleni = zaposleniImpl.vratiZaposlenogSaDatimEmailom("boban@gmail.com");
        assertNull(zaposleni);
    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testObrisiZaposlenog() {

        ZaposleniRepository zaposleniRepository = applicationContext.getBean(ZaposleniRepository.class);
        Optional<Zaposleni> optZap = zaposleniRepository.findById(7);
        if (optZap.isPresent()) {
            Zaposleni zap = optZap.get();
            assertNotNull(zap);
            zaposleniImpl.obrisiZaposlenog(zap);

            Optional<Zaposleni> optObrisanZap = zaposleniRepository.findById(7);
            assertFalse(optObrisanZap.isPresent());
        } else {
            fail("Zaposleni sa ID-jem 7 ne postoji.");
        }
    }
    
    @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testObrisiZaposlenogException() {

        ZaposleniRepository zaposleniRepository = applicationContext.getBean(ZaposleniRepository.class);
        Optional<Zaposleni> optZap = zaposleniRepository.findById(7);
        if (optZap.isPresent()) {
            Zaposleni zap = optZap.get();
            assertNotNull(zap);
            assertThrows(DataIntegrityViolationException.class, ()->zaposleniImpl.obrisiZaposlenog(zap));

           
        } else {
            fail("Zaposleni sa ID-jem 7 ne postoji.");
        }
    }
    
    

    @Test

    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateZaposleniCuvanje() {
        ZaposleniUNastavi zn = new ZaposleniUNastavi("Profesor", "SI", "Bogdan", "Bogdanovic", "bogdanbogdanovic7@gmail.com", "Redovni profesor");
        zaposleniImpl.updateZaposleni(zn);

        ZaposleniRepository zaposleniRepository = applicationContext.getBean(ZaposleniRepository.class);
        List<Zaposleni> zaposleni = zaposleniRepository.findAll();
        assertEquals(1, zaposleni.size());
        Zaposleni bazaZaposleni = zaposleni.get(0);
        zn.setId(bazaZaposleni.getId());
        assertEquals(bazaZaposleni, zn);
    }

    @Test
    @Sql(scripts = "/insert_zaposleni.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateZaposleniAzuriranje() {

        ZaposleniRepository zaposleniRepository = applicationContext.getBean(ZaposleniRepository.class);
        Optional<Zaposleni> optZaposleni = zaposleniRepository.findById(7);
        if (optZaposleni.isPresent()) {
            Zaposleni zap = optZaposleni.get();
            zap.setEmail("novi@gmial.com");
            zap.setIme("Perica");
            zap.setPrezime("Peric");
            zaposleniImpl.updateZaposleni(zap);
            optZaposleni = zaposleniRepository.findById(7);
            if (optZaposleni.isPresent()) {
                    
                Zaposleni bazaZaposleni = optZaposleni.get();
                assertEquals(zap, bazaZaposleni);
                
            } else {
                fail("Zaposleni ne postoji sa id-7");
            }

        } else {
            fail("Zaposleni ne postoji sa id-7");

        }

    }

}
