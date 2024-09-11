/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Registracija;

/**
 *
 * @author Korisnik
 */
public interface RegistracijaRepository extends JpaRepository<Registracija, Integer> {
    
    @Query("SELECT r FROM Registracija r WHERE r.token = :token")
    public Registracija vratiRegistracijuPoTokenu(@Param("token") String token);
}
