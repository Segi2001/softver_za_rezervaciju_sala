/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Registracija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.RegistracijaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class RegistracijaImpl {
    
    private RegistracijaRepository registracijaRepository;

    public RegistracijaImpl() {
    }
 @Autowired
    public RegistracijaImpl(RegistracijaRepository registracijaRepository) {
        this.registracijaRepository = registracijaRepository;
    }
    
     @Transactional
    public void sacuvajRegistraciju(Registracija registracija){
        registracijaRepository.save(registracija);
    }
     @Transactional
    public Registracija pronadjiRegistracijuPoTokenu(String token){
        return registracijaRepository.vratiRegistracijuPoTokenu(token);
    }
 @Transactional
    public void obrisi(Registracija r) {
       registracijaRepository.delete(r);
    }
}
