/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Kombinovana;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RCSala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.SalaRepository;

/**
 *
 * @author Korisnik
 */
@Service
public class SalaImpl {
     public SalaImpl() {

    }
    private SalaRepository salaRepository;

    @Autowired
    public SalaImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
    
     @Transactional
    public List<Sala> vratiAktivneSale(){
        return salaRepository.vratiSaleKojeSuAktivne();
    }
    
    @Transactional
    public List<Sala> vratiAktivneSaleSaRacunarima(){
        return salaRepository.vratiSveSaleKojeImajuRacunare(RCSala.class, Kombinovana.class);
    }
    
    @Transactional
    public List<Sala> vratiAktivneSaleSaMinimalnimBrojemMesta(Sala sala){
        return salaRepository.vratiAktivneSaleSaOdredjenimBrojemMesta(sala.getBrojMesta());
    }
    
    @Transactional
    public void obrisiSalu(Sala sala){
        salaRepository.delete(sala);
    }
    @Transactional
    public void updateSale(Sala sala){
        salaRepository.save(sala);
    }
    
    
    @Transactional
    public Sala findSalaById(Long id){
        return salaRepository.findSalaById(id);
    }
}
