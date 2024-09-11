/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.SoftverZaRezervacijuSalaApplicationTests;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.KorisnikRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.dao.DataIntegrityViolationException;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Osoba;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Student;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.OsobaRepository;

/**
 *
 * @author Korisnik
 */
public class KorisnikImplTest extends SoftverZaRezervacijuSalaApplicationTests{
    
    KorisnikImpl korisnikImpl;
    
    @Autowired
    ApplicationContext applicationContext;
    
   
    @BeforeEach
    public void setUp() {
        korisnikImpl = new KorisnikImpl(applicationContext.getBean(KorisnikRepository.class));
    }
    
    @AfterEach
    public void tearDown() {
        korisnikImpl = null;
    }

   @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaPoId() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaPoId(2);
       assertEquals(2, korisnik.getId());
       
    }

    
       @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaPoIdNePostoji() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaPoId(99);
       assertNull(korisnik);
       
    }
    
    
        @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaSaZadatimUsername() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername("bogdanovic@gmail.com");
            assertEquals(3, korisnik.getId());
       
    }
    
         @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaSaZadatimUsernameNePostojiOsoba() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername("zika@gmail.com");
             assertNull(korisnik);
       
    }
    
        @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaSaZadatimUsernameNePostojiKorisnik() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername("siljkovic@gmail.com");
             assertNull(korisnik);
       
    }
   
    
    
       @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaPoOsobaId() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaPoOsobaId(7);
           assertEquals(3, korisnik.getId());
       
    }
    
    
      @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaPoOsobaIdNePostojiOsoba() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaPoOsobaId(100);
          assertNull(korisnik);
       
    }
    
    
     @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiKorisnikaPoOsobaIdNePostojiKorisnik() {
      Korisnik korisnik = korisnikImpl.vratiKorisnikaPoOsobaId(5);
          assertNull(korisnik);
       
    }
    
    
    
       @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteKorisnik() {
        
         KorisnikRepository korisnikRepository = applicationContext.getBean(KorisnikRepository.class);
        Optional<Korisnik> optKor = korisnikRepository.findById(7);
        if (optKor.isPresent()) {
            Korisnik kor = optKor.get();
            assertNotNull(kor);
            korisnikImpl.deleteKorisnik(kor);

            Optional<Korisnik> optObrisanKor = korisnikRepository.findById(7);
            assertFalse(optObrisanKor.isPresent());
        } else {
            fail("Korisnik sa ID-jem 7 ne postoji.");
        }
       
    }
    
    
          @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteKorisnikException() {
        
         KorisnikRepository korisnikRepository = applicationContext.getBean(KorisnikRepository.class);
        Optional<Korisnik> optKor = korisnikRepository.findById(2);
        if (optKor.isPresent()) {
            Korisnik kor = optKor.get();
            assertNotNull(kor);
            assertThrows(DataIntegrityViolationException.class, ()->korisnikImpl.deleteKorisnik(kor));

            
        } else {
            fail("Korisnik sa ID-jem 2 ne postoji.");
        }
       
    }
    
    
       @Test
    @Sql(scripts = "/insert_korisnik.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdate() {
        
          KorisnikRepository korisnikRepository = applicationContext.getBean(KorisnikRepository.class);
          OsobaRepository osobaRepository = applicationContext.getBean(OsobaRepository.class);
          Optional<Osoba> optOsoba = osobaRepository.findById(Long.valueOf(7));
          if(optOsoba.isPresent()){
              Osoba o = optOsoba.get();
              o.setEmail("pera@gmail.com");
          
          List<Korisnik> korisnici = korisnikRepository.findAll();
          Korisnik k = null;
           for (Korisnik korisnik : korisnici) {
               if(korisnik.getOsoba().getId()==7){
                   k = korisnik;
                   break;
               }
           }
           assertNotNull(k);
           k.setOsoba(o);
           k.setSifra("jabuka");
           korisnikImpl.update(k);
           Optional<Korisnik> optKorisnik = korisnikRepository.findById(k.getId());
              assertTrue(optKorisnik.isPresent());
              Korisnik bazaKorisnik = optKorisnik.get();
              assertEquals(bazaKorisnik, k);
           
           
          }
          else{
              fail("Osoba sa id-7 ne postoji");
          }
       
            
           
         
       
    }
    
    
    
    
    
    
    

    
}
