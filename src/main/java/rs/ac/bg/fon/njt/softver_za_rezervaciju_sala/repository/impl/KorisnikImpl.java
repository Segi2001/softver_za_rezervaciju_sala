/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniUNastavi;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.ZaposleniVanNastave;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.KorisnikRepository;

/**
 *
 * @author Stefan
 */
@Service
public class KorisnikImpl {

    public KorisnikImpl() {

    }
    private KorisnikRepository korisnikRepository;

    @Autowired
    public KorisnikImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Transactional
    public void save(Korisnik korisnik) {

        korisnikRepository.save(korisnik);
    }

    @Transactional
    public void update(Korisnik korisnik) {

        Korisnik k = korisnikRepository.vratiKorisnikaPoOsobaId(korisnik.getOsoba().getId());
        k.setOsoba(korisnik.getOsoba());
        k.setSifra(korisnik.getSifra());

        korisnikRepository.save(k);

    }

    public void deleteKorisnik(Korisnik korisnik) {

        korisnikRepository.delete(korisnik);

    }

    public Korisnik vratiKorisnikaPoOsobaId(int id) {
        return korisnikRepository.vratiKorisnikaPoOsobaId(id);
    }

    public Korisnik vratiKorisnikaSaZadatimUsername(String email) {
        return korisnikRepository.vratiKorisnikaPoUsername(email);
    }

    public Korisnik vratiKorisnikaPoId(Integer id) {
        return korisnikRepository.vratiKorisnikaPoId(id);
    }
}
