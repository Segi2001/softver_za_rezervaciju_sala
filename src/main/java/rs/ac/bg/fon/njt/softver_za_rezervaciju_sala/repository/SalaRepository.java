/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Kombinovana;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RCSala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;

/**
 *
 * @author Sreja
 */
public interface SalaRepository extends JpaRepository<Sala, Integer>{
    
    @Query("SELECT s FROM Sala s WHERE s.status=TRUE")
    public List<Sala> vratiSaleKojeSuAktivne();
    
    @Query("SELECT s FROM Sala s WHERE s.status=TRUE AND s.brojMesta >= :broj")
    public List<Sala> vratiAktivneSaleSaOdredjenimBrojemMesta(@Param("broj") Integer brojMesta);
    
    @Query("SELECT s FROM Sala s WHERE s.status=TRUE AND TYPE(s) IN (:RCSala, :Kombinovana)")
    public List<Sala> vratiSveSaleKojeImajuRacunare(@Param("RCSala") Class<RCSala> rcSalam,@Param("Kombinovana") Class<Kombinovana> kombinovana);
    
    @Query("SELECT s FROM Sala s WHERE s.id=:id")
    public Sala findSalaById(@Param("id") Long id);
}
