/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Zaposleni;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.ZaposleniRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class ZaposleniImpl {
     public ZaposleniImpl() {

    }
    private ZaposleniRepository zaposleniRepository;

    @Autowired
    public ZaposleniImpl(ZaposleniRepository zaposleniRepository) {
        this.zaposleniRepository = zaposleniRepository;
    }
    
    @Transactional
    public List<Zaposleni> vratiSveZaposlene(){
        return zaposleniRepository.vratiSveZaoposlene();
    }
    
     @Transactional
    public Zaposleni vratiZaposlenogSaDatimEmailom(String email){
        return zaposleniRepository.vratiZaposlenogSaDatimEmail(email);
    }
    
    @Transactional
    public void obrisiZaposlenog(Zaposleni zaposleni){
        zaposleniRepository.delete(zaposleni);
    }
    
    @Transactional
    public void updateZaposleni(Zaposleni zaposleni){
        zaposleniRepository.save(zaposleni);
    }
}
