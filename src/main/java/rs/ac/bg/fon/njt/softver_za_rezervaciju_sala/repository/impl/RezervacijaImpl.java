/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.RezervacijaRepository;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;

/**
 *
 * @author BaNbO01
 */
@Service
public class RezervacijaImpl {

    public RezervacijaImpl() {

    }
    private SalaRepository salaRepository;
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    public RezervacijaImpl(RezervacijaRepository rezervacijaRepository, SalaRepository salaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public List<Rezervacija> vratiSvePotvrdjeneRezervacije() {
        return rezervacijaRepository.vratiPotvrdjeneRezervacije();
    }

    @Transactional
    public void sacuvajRezervaciju(Rezervacija rezervacija) {

        Integer brojZauzetihTermina = rezervacijaRepository.brojRezervacija(rezervacija);
        System.out.println("Broj zauzetih termina je :" + brojZauzetihTermina);
        System.out.println(rezervacija.getVremePocetka());
        System.out.println(rezervacija.getVremeZavrsetka());

        if (rezervacija.getStatusRezervacije().equals("ODBIJENA")) {
            rezervacijaRepository.save(rezervacija);
            return;
        }
        if (rezervacija.getVremePocetka().before(new Date()) || rezervacija.getVremeZavrsetka().before(new Date())) {

            throw new RuntimeException("Nije moguce rezervisati termin u proslosti, pokusajte ponovo");
        }

        if (rezervacija.getVremeZavrsetka().before(rezervacija.getVremePocetka())) {
            throw new RuntimeException("Vreme zavrsetka ne moze biti pre vremena pocetka!!!");
        }

        if (rezervacija.getRezervacijaID().getId() != 0) {
            System.out.println("Izmena rezervacije");
            rezervacijaRepository.save(rezervacija);
            return;
        }

        if (brojZauzetihTermina == null || brojZauzetihTermina == 0) {
            rezervacijaRepository.save(rezervacija);
        } else {
            throw new RuntimeException("Ne moze se rezervisati sala u ovom terminu zato sto je zauzeta, molimo Vas pokusajte rezervaciju nekog drugog termina");
        }
    }

    @Transactional
    public List<Object[]> vratiSlobodneTermineZaSaluZaKonkretniDatum(Date datum,int salaid,int sati,int minuti){
        return rezervacijaRepository.vratiSalePoSatu(salaid, datum, sati, minuti);
    }
    
    @Transactional
    public List<SalePoDanu> vratiZauzetostiZaDan(Date datum) {
        List<SalePoDanu> lista = new ArrayList<>();
        List<Sala> sveSale = salaRepository.vratiSaleKojeSuAktivne();
        for (Sala sala : sveSale) {
            SalePoDanu salePoDanu = new SalePoDanu();
            salePoDanu.setDatum(datum);
            salePoDanu.setSala(sala);
            List<Integer> sati = new ArrayList<>();
            List<Boolean> zauzetost = new ArrayList<>();
            List<Long> minuti = new ArrayList<>();
            List<SvrhaRezervacije> svrhaRezervacija = new ArrayList<>();
            List<Date> vremenaPocetka = new ArrayList<>();
            List<Date> vremenaZavrsetka = new ArrayList<>();
            for (int i = 8; i < 23; i++) {
                for (int j = 0; j < 60; j += 15) {

                    List<Object[]> tupleVrednosti = rezervacijaRepository.vratiSalePoSatu(sala.getId(), datum, i, j);
                    SvrhaRezervacije svrhaRezervacije = rezervacijaRepository.vratiSvhruRezervacije(sala.getId(), datum, i, j);
                    svrhaRezervacija.add(svrhaRezervacije);
                    sati.add(i);
                    for (Object[] result : tupleVrednosti) {
                        Boolean exists = (Boolean) result[0];
                        Date vremePocetka = (Date) result[1];
                        Date vremeZavrsetka = (Date) result[2];
                        String statusRezervacije = (String)result[3];
                        if(sala.getNaziv().equals("Amfiteatar 3") && exists){
                            System.out.println("Vreme pocetka: " + vremePocetka);
                            System.out.println("Vreme zavrsetka: " + vremeZavrsetka);
                            System.out.println("");
                        }
                        vremenaPocetka.add(vremePocetka);
                        vremenaZavrsetka.add(vremeZavrsetka);
                        if(statusRezervacije!=null && statusRezervacije.equals("ODBIJENA")){
                            zauzetost.add(false);
                        }
                        else{
                               zauzetost.add(exists);
                        }
                     
                    }
                    

                    LocalDateTime novoVreme = datum.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    novoVreme = novoVreme.withHour(i);
                    novoVreme = novoVreme.plusMinutes(j);

                    Date currentDate = Date.from(novoVreme.atZone(ZoneId.systemDefault()).toInstant());
                    Long minutes = rezervacijaRepository.findMinutesToNextReservation(currentDate, sala.getId());
                    minuti.add(minutes);

                }
            }

            List<Object[]> tupleVrednosti = rezervacijaRepository.vratiSalePoSatu(sala.getId(), datum, 23, 0);
            SvrhaRezervacije svrhaRezervacije = rezervacijaRepository.vratiSvhruRezervacije(sala.getId(), datum, 23, 0);
            svrhaRezervacija.add(svrhaRezervacije);

            LocalDateTime novoVreme = datum.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            novoVreme = novoVreme.withHour(23);
            novoVreme = novoVreme.plusMinutes(0);

            Date currentDate = Date.from(novoVreme.atZone(ZoneId.systemDefault()).toInstant());
            Long minutes = rezervacijaRepository.findMinutesToNextReservation(currentDate, sala.getId());
            minuti.add(minutes);
            for (Object[] result : tupleVrednosti) {
                        Boolean exists = (Boolean) result[0];
                        Date vremePocetka = (Date) result[1];
                        Date vremeZavrsetka = (Date) result[2];
                         String statusRezervacije = (String)result[3];
                        vremenaPocetka.add(vremePocetka);
                        vremenaZavrsetka.add(vremeZavrsetka);
                        
                        if(statusRezervacije!=null && statusRezervacije.equals("ODBIJENA")){
                            zauzetost.add(false);
                        }
                        else{
                               zauzetost.add(exists);
                        }
                     
                    }
            
            salePoDanu.setSvrhaRezervacije(svrhaRezervacija);
            salePoDanu.setMinuti(minuti);
            salePoDanu.setZauzetost(zauzetost);
            salePoDanu.setVremePocetka(vremenaPocetka);
            salePoDanu.setVremeZavrsetka(vremenaZavrsetka);
            lista.add(salePoDanu);

        }

        return lista;

    }

    @Transactional
    public Rezervacija findRezervacijaById(RezervacijaID rezervacijaID) {
        return rezervacijaRepository.findRezervacijaById(rezervacijaID);
    }

    @Transactional
    public void deleteRezervacijaById(RezervacijaID rezervacijaID) {
        rezervacijaRepository.deleteById(rezervacijaID);
    }

    @Transactional
    public List<Rezervacija> findAllRezervacije() {
        return rezervacijaRepository.findAllRezervacija();
    }

    @Transactional
    public List<Rezervacija> findRezervacijaByUser(Integer id) {
        return rezervacijaRepository.findRezervacijeByUser(id);
    }

    @Transactional
    public Long numberOfMinutesDifference(LocalDateTime localDateTime,int salaId,int id,int korisnikId){
        RezervacijaID rezervacijaID = new RezervacijaID(id,salaId,korisnikId);
         Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return rezervacijaRepository.findMinutesToNextReservation(currentDate, rezervacijaID,salaId);
    }
    
    
    @Transactional
    public Long numberOfMinutesDifference(LocalDateTime localDateTime, int salaId) {
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return rezervacijaRepository.findMinutesToNextReservation(currentDate, salaId);
    }

    
    
    @Transactional
    public List<Object[]> vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(Date datum,int sati,int minuti,int salaid,int id,int korisnikId){
        RezervacijaID rezervacijaID = new RezervacijaID(id,salaid,korisnikId);
        return rezervacijaRepository.vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(datum,sati,minuti,rezervacijaID,salaid);
    }
}
