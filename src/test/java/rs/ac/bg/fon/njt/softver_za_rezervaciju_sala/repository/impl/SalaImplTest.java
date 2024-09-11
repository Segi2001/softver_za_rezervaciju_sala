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
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.SoftverZaRezervacijuSalaApplicationTests;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Amfiteatar;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Kombinovana;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RCSala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalaZaSastanke;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Ucionica;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;


public class SalaImplTest extends SoftverZaRezervacijuSalaApplicationTests {
    
       SalaImpl salaImpl;
    
     @Autowired
        ApplicationContext applicationContext;
    
 
    
    @BeforeEach
    public void setUp() {
        salaImpl = new SalaImpl(applicationContext.getBean(SalaRepository.class));
    }
    
    @AfterEach
    public void tearDown() {
        salaImpl = null;
    }

    @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSale() {
      List<Sala> lista = salaImpl.vratiAktivneSale();
        assertEquals(11, lista.size());
        for (Sala sala : lista) {
            assertTrue(sala.isStatus());
        }
        
    }
    
    
     @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleNemaSala() {
      List<Sala> lista = salaImpl.vratiAktivneSale();
        assertEquals(0, lista.size());
    }
    
     @Test
    @Sql(scripts = "/insert_neaktivne_sale.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleNisuPronadjene() {
     List<Sala> lista = salaImpl.vratiAktivneSale();
        assertEquals(0, lista.size());
        
    }
    

    @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaRacunarima() {
       List<Sala> lista = salaImpl.vratiAktivneSaleSaRacunarima();
        assertEquals(4, lista.size());
        for(Sala sala:lista){
            assertTrue((sala instanceof RCSala) || (sala instanceof Kombinovana));
            assertTrue(sala.isStatus());
        }
        
    }
    
    
        @Test
    @Sql(scripts = "/insert_neaktivne_sale.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaRacunarimaNisuAktivne() {
       List<Sala> lista = salaImpl.vratiAktivneSaleSaRacunarima();
          assertEquals(0, lista.size());
        
    }
    
         @Test
    @Sql(scripts = "/insert_aktivne_bez_racunara.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaRacunarimaNisuAktivneAktivneBezRacunara() {
       List<Sala> lista = salaImpl.vratiAktivneSaleSaRacunarima();
          assertEquals(0, lista.size());
        
    }
    
    
    

     @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaMinimalnimBrojemMesta() {
       Sala s = new Ucionica();
       s.setBrojMesta(120);
       List<Sala> lista = salaImpl.vratiAktivneSaleSaMinimalnimBrojemMesta(s);
       assertEquals(4, lista.size());
       for(Sala sala:lista){
           assertTrue(sala.isStatus());
       }
    }
    
      @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaMinimalnimBrojemMestaNePostojiTakvaSala() {
       Sala s = new Ucionica();
       s.setBrojMesta(300);
       List<Sala> lista = salaImpl.vratiAktivneSaleSaMinimalnimBrojemMesta(s);
       assertEquals(0, lista.size());
    }
    
        @Test
    @Sql(scripts = "/insert_neaktivne_sale.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiAktivneSaleSaMinimalnimBrojemMestaNemaAktivnihSala() {
       Sala s = new Ucionica();
       s.setBrojMesta(120);
       List<Sala> lista = salaImpl.vratiAktivneSaleSaMinimalnimBrojemMesta(s);
        assertEquals(0, lista.size());
    }
    
    
    

     @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testObrisiSalu() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(7);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            assertNotNull(sala);
            salaImpl.obrisiSalu(sala);

            Optional<Sala> optObrisanaSala = salaRepository.findById(7);
            assertFalse(optObrisanaSala.isPresent());
        } else {
            fail("Sala sa ID-jem 7 ne postoji.");
        }
    }
    
    
    
    
           @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testObrisiSaluException() {
        
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(2);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            assertNotNull(sala);
            assertThrows(DataIntegrityViolationException.class, ()->salaImpl.obrisiSalu(sala));

            
        } else {
            fail("Sala sa ID-jem 2 ne postoji.");
        }
       
    }

    @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleNovaUcionica() {
        Sala sala = new Ucionica("016", "Nalazi se na prvom spratu", 50, true);
        salaImpl.updateSale(sala);
        List<Sala> lista = applicationContext.getBean(SalaRepository.class).findAll();
          assertEquals(1, lista.size());
          sala.setId(lista.get(0).getId());
          assertEquals(sala, lista.get(0));
    }
    
    
      @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleNoviAmfiteatar() {
        Sala sala = new Amfiteatar("AMFITEATAR 1", "Nalazi se na prvom spratu", 150, true);
        salaImpl.updateSale(sala);
        List<Sala> lista = applicationContext.getBean(SalaRepository.class).findAll();
          assertEquals(1, lista.size());
          sala.setId(lista.get(0).getId());
          assertEquals(sala, lista.get(0));
    }
    
       @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleNovaSalaZaSastanke() {
        Sala sala = new SalaZaSastanke("215", "Nalazi se na cetvrtom spratu", 30, true);
        salaImpl.updateSale(sala);
        List<Sala> lista = applicationContext.getBean(SalaRepository.class).findAll();
          assertEquals(1, lista.size());
          sala.setId(lista.get(0).getId());
          assertEquals(sala, lista.get(0));
    }
    
       @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleNovaKombinovana() {
        Sala sala = new Kombinovana(40,"118", "Nalazi se na prvom spratu", 60, true);
        salaImpl.updateSale(sala);
        List<Sala> lista = applicationContext.getBean(SalaRepository.class).findAll();
          assertEquals(1, lista.size());
          sala.setId(lista.get(0).getId());
          assertEquals(sala, lista.get(0));
    }

    
       @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleNoviRacunarskiCentar() {
        Sala sala = new RCSala(40,"118", "Nalazi se na prvom spratu", 60, true);
        salaImpl.updateSale(sala);
        List<Sala> lista = applicationContext.getBean(SalaRepository.class).findAll();
          assertEquals(1, lista.size());
          sala.setId(lista.get(0).getId());
          assertEquals(sala, lista.get(0));
    }
    
    
     @Test
     @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleUcionica() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(6);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            sala.setNaziv("225");
            sala.setBrojMesta(35);
            salaImpl.updateSale(sala);
            optSala = salaRepository.findById(6);
            if (optSala.isPresent()) {
                    
                Sala bazaSala = optSala.get();
                assertEquals(sala, bazaSala);
                
            } else {
                fail("Sala ne postoji sa id-6");
            }

        } else {
            fail("Sala ne postoji sa id-6");

        }
    }
    
        @Test
     @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleAmfiteatar() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(2);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            sala.setNaziv("Amfiteatar 3");
            sala.setBrojMesta(175);
              salaImpl.updateSale(sala);
            optSala = salaRepository.findById(2);
            if (optSala.isPresent()) {
                    
                Sala bazaSala = optSala.get();
                assertEquals(sala, bazaSala);
                
            } else {
                fail("Sala ne postoji sa id-2");
            }

        } else {
            fail("Sala ne postoji sa id-2");

        }
    }
    
    
    
        @Test
     @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleSalaZaSastanke() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(9);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            sala.setNaziv("Sala za sastanke 1");
            sala.setBrojMesta(30);
            sala.setNapomena("Nalazi se na drugom spratu");
              salaImpl.updateSale(sala);
            optSala = salaRepository.findById(9);
            if (optSala.isPresent()) {
                    
                Sala bazaSala = optSala.get();
                assertEquals(sala, bazaSala);
                
            } else {
                fail("Sala ne postoji sa id-9");
            }

        } else {
            fail("Sala ne postoji sa id-9");

        }
    }
    
    
        @Test
     @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleKombinovana() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(10);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            sala.setNaziv("121");
            sala.setBrojMesta(70);
            Kombinovana k =(Kombinovana)sala;
            k.setBrojRacunara(35);
            sala = k;
              salaImpl.updateSale(sala);
            optSala = salaRepository.findById(10);
            if (optSala.isPresent()) {
                    
                Sala bazaSala = optSala.get();
                assertEquals(sala, bazaSala);
                
            } else {
                fail("Sala ne postoji sa id-10");
            }

        } else {
            fail("Sala ne postoji sa id-10");

        }
    }
    
    
    
    
        @Test
     @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateSaleRacunarskiCentar() {
         SalaRepository salaRepository = applicationContext.getBean(SalaRepository.class);
        Optional<Sala> optSala = salaRepository.findById(4);
        if (optSala.isPresent()) {
            Sala sala = optSala.get();
            sala.setNaziv("RC sala 2");
            sala.setBrojMesta(80);
            RCSala rc = (RCSala)sala;
            rc.setBrojRacunara(45);
            sala = rc;
              salaImpl.updateSale(sala);
            optSala = salaRepository.findById(4);
            if (optSala.isPresent()) {
                    
                Sala bazaSala = optSala.get();
                assertEquals(sala, bazaSala);
                
            } else {
                fail("Sala ne postoji sa id-4");
            }

        } else {
            fail("Sala ne postoji sa id-4");

        }
    }
    
    
     @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindSalaById() {
        Sala s = salaImpl.findSalaById(1L);
         assertEquals(1, s.getId());
    }
    
    
       @Test
    @Sql(scripts = "/insert_sala.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindSalaByIdNePostoji() {
        Sala s = salaImpl.findSalaById(100L);
           assertNull(s);
    }
    
    
    
    
}
