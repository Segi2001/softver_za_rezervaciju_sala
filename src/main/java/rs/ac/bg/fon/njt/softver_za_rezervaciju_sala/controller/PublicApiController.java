/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Amfiteatar;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Kombinovana;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RCSala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalaDTO;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalaZaSastanke;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.exception.SalaNotFoundException;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.SalaImpl;

/**
 *
 * @author Korisnik
 */
@Controller
@RequestMapping("/api/public")
public class PublicApiController {

    @Autowired
    public PublicApiController(SalaImpl salaImpl) {
        this.salaImpl = salaImpl;
    }
    private SalaImpl salaImpl;

    @GetMapping("/getAllHalls")
    public ResponseEntity<List<Sala>> getPublicData() {
        List<Sala> sale = salaImpl.vratiAktivneSale();
        return ResponseEntity.ok(sale);
    }

    @GetMapping("/getHall")
    public ResponseEntity<Sala> getHall(@RequestParam("id") int id) {

        Sala sala = salaImpl.findSalaById(Long.valueOf(id));
        if (sala == null) {
            throw new SalaNotFoundException("Ne postoji sala za datim id-jem!!!");
        }
        System.out.println("USAO SI U  GETHALL");
        return ResponseEntity.ok(sala);
    }

    @PostMapping("/saveHall")
    public ResponseEntity<String> saveHall(@RequestBody SalaDTO salaDTO) {

        if (salaDTO.getTipSale() == null || !List.of("Kombinovana", "RC sala", "Ucionica", "Sala za sastanke", "Amfiteatar").contains(salaDTO.getTipSale())) {
            throw new IllegalArgumentException("Tip sale mora biti jedan od tipova : Kombinovana, Ucionica, Sala za sastanke,"
                    + " RC sala ili Amfiteatar");
        }
        if (salaDTO.getNapomena().matches("\\d+")) {
            throw new IllegalArgumentException("Napomena ne sme da se sastoji samo od cifara!!");
        }

        if (salaDTO.getBrojMesta() <= 0 || salaDTO.getNapomena() == null || salaDTO.getNaziv() == null) {
            throw new IllegalArgumentException("Nisu popunjena sva polja!!!");
        }
        if ((salaDTO.getTipSale().equals("RC sala") || salaDTO.getTipSale().equals("Kombinovana"))
                && salaDTO.getBrojRacunara() <= 0) {
            throw new IllegalArgumentException("Polje za broj racunara mora biti vece od nule ukoliko je sala Kombinovana"
                    + " ili je Racunarski centar!!!");
        }

        if (salaDTO.getId() != 0) {
            Sala s = salaImpl.findSalaById(Long.valueOf(salaDTO.getId()));
            if (s == null) {
                throw new SalaNotFoundException("Trazena sala sa prosledjenim id-jem ne postoji u bazi!!!");
            }
            s.setNaziv(salaDTO.getNaziv());
            s.setStatus(salaDTO.isStatus());
            s.setNapomena(salaDTO.getNapomena());
            s.setBrojMesta(salaDTO.getBrojMesta());
            if (s instanceof Kombinovana) {

                ((Kombinovana) s).setBrojRacunara(salaDTO.getBrojRacunara());
                salaImpl.updateSale(s);
                return ResponseEntity.ok("Uspesno ste azurirali salu tipa: Kombinovana");

            }
            if (s instanceof RCSala) {

                ((RCSala) s).setBrojRacunara(salaDTO.getBrojRacunara());
                salaImpl.updateSale(s);
                return ResponseEntity.ok("Uspesno ste azurirali salu tipa: Racunarski centar");

            }
            salaImpl.updateSale(s);
            return ResponseEntity.ok("Uspesno ste azurirali salu : "+salaDTO.getTipSale());

        } else {
            switch (salaDTO.getTipSale()) {
                case "Kombinovana": {

                    Kombinovana s = new Kombinovana();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    s.setBrojRacunara(salaDTO.getBrojRacunara());
                    salaImpl.updateSale(s);
                    return ResponseEntity.ok("Uspesno ste sacuvali salu tipa: Kombinovana");
                }
                case "RC sala": {
                    RCSala s = new RCSala();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    s.setBrojRacunara(salaDTO.getBrojRacunara());
                    salaImpl.updateSale(s);
                    return ResponseEntity.ok("Uspesno ste sacuvali salu tipa: Racunarski centar");
                }

                case "Amfiteatar": {

                    Amfiteatar s = new Amfiteatar();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return ResponseEntity.ok("Uspesno ste sacuvali salu tipa: Amfiteatar");
                }

                case "Sala za sastanke": {
                    SalaZaSastanke s = new SalaZaSastanke();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return ResponseEntity.ok("Uspesno ste sacuvali salu tipa: Sala za sastanke");
                }

                case "Ucionica": {
                    Amfiteatar s = new Amfiteatar();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return ResponseEntity.ok("Uspesno ste sacuvali salu tipa: Ucionica");

                }
                default: {
                    throw new IllegalArgumentException("");
                }

            }

        }

    }

    @PostMapping("/deleteHall")
    public ResponseEntity<String> deleteHall(@RequestParam("id") int id) {

        Sala sala = salaImpl.findSalaById(Long.valueOf(id));
        if (sala == null) {
            throw new SalaNotFoundException("Ne postoji sala za datim id-jem!!!");
        }
        salaImpl.obrisiSalu(sala);
        System.out.println("Id je: " + id);
        System.out.println("Obrisana sala");
        return ResponseEntity.ok("Sala je obrisana");

    }

}
