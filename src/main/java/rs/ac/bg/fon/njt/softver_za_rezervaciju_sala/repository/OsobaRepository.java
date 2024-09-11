/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Osoba;

/**
 *
 * @author Korisnik
 */
public interface OsobaRepository extends JpaRepository<Osoba, Long> {
    
    
    
    Osoba findOsobaByEmail(String email);
}
