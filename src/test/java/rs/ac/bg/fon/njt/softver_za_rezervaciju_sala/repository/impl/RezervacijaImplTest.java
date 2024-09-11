/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.RezervacijaRepository;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;
import static org.junit.jupiter.api.Assertions.*;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.KorisnikRepository;

/**
 *
 * @author Korisnik
 */
public class RezervacijaImplTest extends SoftverZaRezervacijuSalaApplicationTests {

    RezervacijaImpl rezervacijaImpl;

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    public void setUp() {
        rezervacijaImpl = new RezervacijaImpl(applicationContext.getBean(RezervacijaRepository.class), applicationContext.getBean(SalaRepository.class));
    }

    @AfterEach
    public void tearDown() {
        rezervacijaImpl = null;
    }

    @Test
    @Sql(scripts = "/halls_per_day.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiZauzetostiZaDan() {
        LocalDate localDate = LocalDate.of(2024, 6, 27);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<SalePoDanu> salePoDanus = rezervacijaImpl.vratiZauzetostiZaDan(date);
        assertEquals(1, salePoDanus.size());
        for (SalePoDanu salePoDanu : salePoDanus) {
            for (int i = 0; i < salePoDanu.getZauzetost().size(); i++) {
                boolean expected = (i >= 10 && i <= 15) || (i >= 28 && i <= 31) || (i >= 53 && i <= 60);
                assertEquals(expected, salePoDanu.getZauzetost().get(i));
            }

        }

    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiSvePotvrdjeneRezervacije() {
        List<Rezervacija> listaRezervacija = rezervacijaImpl.vratiSvePotvrdjeneRezervacije();
        assertEquals(1, listaRezervacija.size());
        RezervacijaID id = new RezervacijaID(5, 5, 3);
        assertEquals(id, listaRezervacija.get(0).getRezervacijaID());
    }

    @Test
    @Sql(scripts = "/insert_bez_potvrdjenih_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiSvePotvrdjeneRezervacijeNijePronadjen() {
        List<Rezervacija> listaRezervacija = rezervacijaImpl.vratiSvePotvrdjeneRezervacije();
        assertEquals(0, listaRezervacija.size());
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindRezervacijaById() {

        RezervacijaID id = new RezervacijaID(5, 5, 3);
        Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(id);
        assertNotNull(rezervacija);
        assertEquals(id, rezervacija.getRezervacijaID());
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindRezervacijaByIdNePostojiUBazi() {

        RezervacijaID id = new RezervacijaID(6, 5, 3);
        Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(id);
        assertNull(rezervacija);
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAllRezervacije() {

        List<Rezervacija> listaRezervacija = rezervacijaImpl.findAllRezervacije();
        assertEquals(3, listaRezervacija.size());
        assertEquals(new RezervacijaID(5, 5, 3), listaRezervacija.get(0).getRezervacijaID());
        assertEquals(new RezervacijaID(14, 3, 2), listaRezervacija.get(1).getRezervacijaID());
        assertEquals(new RezervacijaID(6, 10, 2), listaRezervacija.get(2).getRezervacijaID());
    }

    @Test
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAllRezervacijeNePostojeUBazi() {

        List<Rezervacija> listaRezervacija = rezervacijaImpl.findAllRezervacije();
        assertEquals(0, listaRezervacija.size());
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteRezervacijaById() {

        RezervacijaRepository rezervacijaRepository = applicationContext.getBean(RezervacijaRepository.class);
        Rezervacija optRez = rezervacijaRepository.findRezervacijaById(new RezervacijaID(5, 5, 3));
        if (optRez != null) {
            Rezervacija rez = optRez;
            rezervacijaImpl.deleteRezervacijaById(rez.getRezervacijaID());
            Rezervacija optObrisanRez = rezervacijaRepository.findRezervacijaById(new RezervacijaID(5, 5, 3));
            assertNull(optObrisanRez);
        } else {
            fail("Rezervacija sa datim ID-jem ne postoji.");
        }
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindRezervacijaByUser() {
        List<Rezervacija> listaRezervacija = rezervacijaImpl.findRezervacijaByUser(2);
        assertEquals(2, listaRezervacija.size());
        assertEquals(new RezervacijaID(14, 3, 2), listaRezervacija.get(0).getRezervacijaID());
        assertEquals(new RezervacijaID(6, 10, 2), listaRezervacija.get(1).getRezervacijaID());
    }

    @Test
    @Sql(scripts = "/insert_potvrdjene_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindRezervacijaByUserNePostojiUBazi() {
        List<Rezervacija> listaRezervacija = rezervacijaImpl.findRezervacijaByUser(1);
        assertEquals(0, listaRezervacija.size());
    }

    @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije() {
        LocalDate localDate = LocalDate.of(2024, 7, 24);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (int i = 8; i < 23; i++) {
            for (int j = 0; j < 60; j += 15) {
                List<Object[]> nizObjekata = rezervacijaImpl.vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(date, i, j, 2, 36, 2);
                for (int k = 0; k < nizObjekata.size(); k++) {
                    Object[] objects = nizObjekata.get(k);
                    Boolean zauzeta = (Boolean) objects[0];
                    if ((i == 21 && j == 45) || (i == 22)) {
                        assertEquals(true, zauzeta);
                    } else {
                        assertEquals(false, zauzeta);
                    }
                }
            }
        }

        List<Object[]> nizObjekata = rezervacijaImpl.vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(date, 23, 0, 2, 36, 2);
        for (int k = 0; k < nizObjekata.size(); k++) {
            Object[] objects = nizObjekata.get(k);
            Boolean zauzeta = (Boolean) objects[0];
            assertEquals(false, zauzeta);
        }
    }

    @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testNumberOfMinutesDifferenceSamoSalaId() {

        LocalDate localDate = LocalDate.of(2024, 7, 24);
        for (int i = 8; i < 23; i++) {
            for (int j = 0; j < 60; j += 15) {
                LocalDateTime novoVreme = localDate.atTime(i, j);

                Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, 2);

                if (i == 16 || (i == 21 && j != 45) || (i == 20 && j == 45)) {
                    assertTrue(minutes <= 60);
                } else {
                    assertTrue(minutes == null || minutes > 60);
                }

            }
        }

    }

    @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testNumberOfMinutesDifference() {

        LocalDate localDate = LocalDate.of(2024, 7, 24);
        for (int i = 8; i < 23; i++) {
            for (int j = 0; j < 60; j += 15) {
                LocalDateTime novoVreme = localDate.atTime(i, j);

                Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, 2, 36, 2);

                if ((i == 21 && j != 45) || (i == 20 && j == 45)) {
                    assertTrue(minutes <= 60);
                } else {
                    assertTrue(minutes == null || minutes > 60);
                }

            }
        }

    }

    @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testVratiSlobodneTermineZaSaluZaKonkretniDatum() {
        LocalDate localDate = LocalDate.of(2024, 7, 24);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        for (int i = 8; i < 23; i++) {
            for (int j = 0; j < 60; j += 15) {
                LocalDateTime novoVreme = localDate.atTime(i, j);

                List<Object[]> lista = rezervacijaImpl.vratiSlobodneTermineZaSaluZaKonkretniDatum(date, 2, i, j);
                for (int k = 0; k < lista.size(); k++) {
                    Object[] objects = lista.get(k);
                    Boolean zauzeta = (Boolean) objects[0];
                    if (i == 17 || (i == 18 && j < 45) || (i == 21 && j == 45) || i == 22) {
                        assertEquals(true, zauzeta);
                    } else {
                        assertEquals(false, zauzeta);
                    }
                }

            }
        }
    }

    @Test
    @Sql(scripts = "/insert_svhra_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuOdbijena() {
        Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(2);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
        LocalDate localDateVremePocetka = LocalDate.of(2024, 10, 25);
        LocalDateTime localDateTimeVremePocetka = localDateVremePocetka.atTime(8, 0);
         Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
          LocalDate localDateVremeZavrsetka = LocalDate.of(2024, 10, 25);
        LocalDateTime localDateTimeVremeZavrsetka = localDateVremeZavrsetka.atTime(12, 0);
         Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("ODBIJENA");
         rezervacijaImpl.sacuvajRezervaciju(r);
         List<Rezervacija> lista = applicationContext.getBean(RezervacijaRepository.class).findAll();
         assertEquals(1, lista.size());
         Rezervacija baznaRazervacija = lista.get(0);
         assertEquals(r.getKorisnik(), baznaRazervacija.getKorisnik());
         assertEquals(r.getSala(), baznaRazervacija.getSala());
         assertEquals(r.getRezervacijaID().getKorisnik_id(), baznaRazervacija.getRezervacijaID().getKorisnik_id());
         assertEquals(r.getRezervacijaID().getSala_id(), baznaRazervacija.getRezervacijaID().getSala_id());
         assertEquals(r.getVremePocetka(), baznaRazervacija.getVremePocetka());
         assertEquals(r.getVremeZavrsetka(), baznaRazervacija.getVremeZavrsetka());
         assertEquals(r.getStatusRezervacije(), baznaRazervacija.getStatusRezervacije());
         assertEquals(r.getSvrhaRezervacije().getOpis(), baznaRazervacija.getSvrhaRezervacije().getOpis());
         assertEquals(r.getSvrhaRezervacije().getOrganizator(), baznaRazervacija.getSvrhaRezervacije().getOrganizator());
         assertEquals(r.getSvrhaRezervacije().getSvrha(), baznaRazervacija.getSvrhaRezervacije().getSvrha());
    }
    
    
    
    
     @Test
    @Sql(scripts = "/insert_svhra_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuVremePocetkaUProslosti() {
        Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(2);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
        
        LocalDateTime localDateTimeVremePocetka = LocalDateTime.now();
        localDateTimeVremePocetka = localDateTimeVremePocetka.minusHours(1);
        Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
         
        LocalDateTime localDateTimeVremeZavrsetka = LocalDateTime.now();
        localDateTimeVremeZavrsetka.plusHours(1);
        Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("U CEKANJU");
         assertThrows(java.lang.RuntimeException.class, ()->rezervacijaImpl.sacuvajRezervaciju(r),"Nije moguce rezervisati termin u proslosti, pokusajte ponovo");
         
    }
    
    
     @Test
    @Sql(scripts = "/insert_svhra_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuVremeZavrsetkaUProslosti() {
        Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(2);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
        
        LocalDateTime localDateTimeVremePocetka = LocalDateTime.now();
        localDateTimeVremePocetka = localDateTimeVremePocetka.plusHours(1);
        Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
         
        LocalDateTime localDateTimeVremeZavrsetka = LocalDateTime.now();
        localDateTimeVremeZavrsetka.minusHours(1);
        Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("U CEKANJU");
         assertThrows(java.lang.RuntimeException.class, ()->rezervacijaImpl.sacuvajRezervaciju(r),"Nije moguce rezervisati termin u proslosti, pokusajte ponovo");
         
    }
    
    
       @Test
    @Sql(scripts = "/insert_svhra_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuVremeZavrsetkaPreVremenaPocetka() {
        Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(2);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
        
        LocalDateTime localDateTimeVremePocetka = LocalDateTime.now();
        localDateTimeVremePocetka = localDateTimeVremePocetka.plusHours(2);
        Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
         
        LocalDateTime localDateTimeVremeZavrsetka = LocalDateTime.now();
        localDateTimeVremeZavrsetka.plusHours(1);
        Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("U CEKANJU");
         assertThrows(java.lang.RuntimeException.class, ()->rezervacijaImpl.sacuvajRezervaciju(r),"Vreme zavrsetka ne moze biti pre vremena pocetka!!!");
         
    }
    
    
    
    
    
    
     @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuIzmena() {
        Optional<Rezervacija> rezervacija = applicationContext.getBean(RezervacijaRepository.class).findById(new RezervacijaID(14,3,2));
        
        Rezervacija r = rezervacija.get();
        r.setStatusRezervacije("POTVRDJENA");
        Date vremePocetka = r.getVremePocetka();
        LocalDateTime localDateTime = vremePocetka.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
        localDateTime.plusMinutes(15);
        vremePocetka = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        r.setVremePocetka(vremePocetka);
         rezervacijaImpl.sacuvajRezervaciju(r);
         rezervacija = applicationContext.getBean(RezervacijaRepository.class).findById(new RezervacijaID(14,3,2));
         Rezervacija baznaRezervacija = rezervacija.get();
         assertEquals(r, baznaRezervacija);
    }
    
    
    
     @Test
    @Sql(scripts = "/insert_rezervacija.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuBrojTerminaVeciOd0() {
       Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(3);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
        
     
         LocalDate localDateVremePocetka = LocalDate.of(2025, 7, 26);
        LocalDateTime localDateTimeVremePocetka = localDateVremePocetka.atTime(10, 45);
         Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
          LocalDate localDateVremeZavrsetka = LocalDate.of(2025, 7, 26);
        LocalDateTime localDateTimeVremeZavrsetka = localDateVremeZavrsetka.atTime(11, 45);
         Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         System.out.println(vremePocetka);
         System.out.println(vremeZavrsetka);
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("U CEKANJU");
         assertThrows(java.lang.RuntimeException.class, ()->rezervacijaImpl.sacuvajRezervaciju(r),"Ne moze se rezervisati sala u ovom terminu zato sto je zauzeta, molimo Vas pokusajte rezervaciju nekog drugog termina");
         
         
    }
    
    
     @Test
    @Sql(scripts = "/insert_bez_potvrdjenih_rezervacije.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = "/delete_all_data.sql", config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED), executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSacuvajRezervacijuBrojTerminaNePostoji() {
       Rezervacija r = new Rezervacija();
        RezervacijaID id = new RezervacijaID();
        Optional<Sala> sala = applicationContext.getBean(SalaRepository.class).findById(2);
        Sala s = sala.get();
        r.setSala(s);
         Optional<Korisnik> korisnik = applicationContext.getBean(KorisnikRepository.class).findById(2);
        Korisnik k = korisnik.get();
        r.setKorisnik(k);
        id.setKorisnik_id(k.getId());
        id.setSala_id(s.getId());
        r.setRezervacijaID(id);
         LocalDate localDateVremePocetka = LocalDate.of(2025, 7, 26);
        LocalDateTime localDateTimeVremePocetka = localDateVremePocetka.atTime(10, 45);
         Date vremePocetka = Date.from(localDateTimeVremePocetka.atZone(ZoneId.systemDefault()).toInstant());
          LocalDate localDateVremeZavrsetka = LocalDate.of(2025, 7, 26);
        LocalDateTime localDateTimeVremeZavrsetka = localDateVremeZavrsetka.atTime(11, 45);
         Date vremeZavrsetka = Date.from(localDateTimeVremeZavrsetka.atZone(ZoneId.systemDefault()).toInstant());
         System.out.println(vremePocetka);
         System.out.println(vremeZavrsetka);
         r.setVremePocetka(vremePocetka);
         r.setVremeZavrsetka(vremeZavrsetka);
         SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije("Polaganje ispita","Ispit iz Projektovanja Softvera","Ilija Antovic");
         r.setSvrhaRezervacije(svrhaRezervacije);
         r.setStatusRezervacije("POTVRDJENA");
         rezervacijaImpl.sacuvajRezervaciju(r);
         List<Rezervacija> lista = applicationContext.getBean(RezervacijaRepository.class).findAll();
         r.setRezervacijaID(lista.get(2).getRezervacijaID());
         r.getSvrhaRezervacije().setId(lista.get(2).getSvrhaRezervacije().getId());
         assertEquals(r, lista.get(2));
    }
    
    
    
    

}
