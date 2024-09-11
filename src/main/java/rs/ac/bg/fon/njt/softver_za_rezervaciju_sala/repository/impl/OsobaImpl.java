/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Osoba;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.OsobaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class OsobaImpl {

    private OsobaRepository osobaRepository;
    
    @Autowired
    public OsobaImpl(OsobaRepository osobaRepository) {
        this.osobaRepository = osobaRepository;
    }
    
    public Osoba findOsobaByEmail(String email){
        return osobaRepository.findOsobaByEmail(email);
    }
    
    
}
