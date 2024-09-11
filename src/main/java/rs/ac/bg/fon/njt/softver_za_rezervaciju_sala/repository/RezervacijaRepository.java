/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;

/**
 *
 * @author BaNbO01
 */
public interface RezervacijaRepository extends JpaRepository<Rezervacija, RezervacijaID>{
    
    @Query("SELECT r FROM Rezervacija r WHERE r.statusRezervacije='POTVRDJENA'")
    public List<Rezervacija> vratiPotvrdjeneRezervacije();
    
//  @Query("SELECT COUNT(*) FROM Rezervacija r WHERE r.sala = :#{#rezervacija.sala} AND ( (r.vremePocetka BETWEEN :#{#rezervacija.vremePocetka} AND :#{#rezervacija.vremeZavrsetka}) OR (r.vremeZavrsetka BETWEEN :#{#rezervacija.vremePocetka} AND :#{#rezervacija.vremeZavrsetka}) OR (:#{#rezervacija.vremePocetka} BETWEEN r.vremePocetka AND r.vremeZavrsetka) OR (:#{#rezervacija.vremeZavrsetka} BETWEEN r.vremePocetka AND r.vremeZavrsetka) )")
    
    @Query("SELECT COUNT(*) FROM Rezervacija r WHERE r.sala = :#{#rezervacija.sala} AND ( (r.vremePocetka >:#{#rezervacija.vremePocetka} AND r.vremePocetka<:#{#rezervacija.vremeZavrsetka}) OR (r.vremeZavrsetka BETWEEN :#{#rezervacija.vremePocetka} AND :#{#rezervacija.vremeZavrsetka}) OR (:#{#rezervacija.vremePocetka} BETWEEN r.vremePocetka AND r.vremeZavrsetka) OR (:#{#rezervacija.vremeZavrsetka}> r.vremePocetka AND :#{#rezervacija.vremeZavrsetka}<r.vremeZavrsetka) )")
    public Integer brojRezervacija(@Param("rezervacija") Rezervacija rezervacija);
    
   // @Query("SELECT r.sala.id, COUNT(r) FROM Rezervacija r WHERE FUNCTION('DATE', r.vremePocetka) = FUNCTION('DATE', :datum) GROUP BY r.sala.id, HOUR(r.vremePocetka) ORDER BY r.sala.id, HOUR(r.vremePocetka)") 
    //List<Object[]> vratiZauzetostSala(@Param("datum") Date datum);
    
    @Query("SELECT (COUNT(r)>0),r.vremePocetka,r.vremeZavrsetka,r.statusRezervacije FROM Rezervacija r WHERE r.sala.id = :sala AND DATE(r.vremePocetka) =:datum  AND  ((HOUR(r.vremePocetka) < :sat) OR (HOUR(r.vremePocetka)=:sat AND (MINUTE(r.vremePocetka)<= :minuti))) AND ((HOUR(r.vremeZavrsetka)> :sat) OR (HOUR(r.vremeZavrsetka)=:sat AND (MINUTE(r.vremeZavrsetka)> :minuti)))")
    public List<Object[]> vratiSalePoSatu(@Param("sala") Integer sala,@Param("datum")Date datum,@Param("sat")Integer sat,@Param("minuti") Integer minuti);
    
    
    @Query("SELECT r.svrhaRezervacije FROM Rezervacija r WHERE  r.statusRezervacije!='ODBIJENA' AND r.sala.id = :sala AND DATE(r.vremePocetka) =:datum  AND  ((HOUR(r.vremePocetka) < :sat) OR (HOUR(r.vremePocetka)=:sat AND (MINUTE(r.vremePocetka)<= :minuti))) AND ((HOUR(r.vremeZavrsetka)> :sat) OR (HOUR(r.vremeZavrsetka)=:sat AND (MINUTE(r.vremeZavrsetka)> :minuti)))")
    public SvrhaRezervacije vratiSvhruRezervacije(@Param("sala") Integer sala,@Param("datum")Date datum,@Param("sat")Integer sat,@Param("minuti") Integer minuti);
    
    @Query("SELECT r FROM Rezervacija r WHERE r.RezervacijaID= :id")
    public Rezervacija findRezervacijaById(@Param("id") RezervacijaID rezervacijaID);
    
//    @Query("SELECT r FROM Rezervacija r ORDER BY r.statusRezervacije")
    @Query("SELECT r FROM Rezervacija r ORDER BY CASE WHEN r.statusRezervacije='POTVRDJENA' THEN 1 WHEN r.statusRezervacije='U CEKANJU' THEN 2 WHEN r.statusRezervacije='ODBIJENA' THEN 3 END, r.statusRezervacije ASC")
    public List<Rezervacija> findAllRezervacija();
    
    @Query("SELECT r FROM Rezervacija r WHERE r.korisnik.id=:id ORDER BY CASE WHEN r.statusRezervacije='POTVRDJENA' THEN 1 WHEN r.statusRezervacije='U CEKANJU' THEN 2 WHEN r.statusRezervacije='ODBIJENA' THEN 3 END, r.statusRezervacije ASC")
    public List<Rezervacija> findRezervacijeByUser(@Param("id") Integer id);
    
    
    
    
    
     @Query("SELECT MIN(TIMESTAMPDIFF(MINUTE, :currentDate, r.vremePocetka)) " +
           "FROM Rezervacija r " +
           "WHERE r.vremePocetka > :currentDate AND DATE(r.vremePocetka)=DATE(:currentDate) AND r.sala.id=:id AND r.statusRezervacije!='ODBIJENA'")
    public Long findMinutesToNextReservation(@Param("currentDate") Date currentDate,@Param("id") int id);
    
    
    
    
     @Query("SELECT (COUNT(r)>0),r.vremePocetka,r.vremeZavrsetka,r.statusRezervacije FROM Rezervacija r WHERE r.sala.id=:sala AND r.RezervacijaID!= :id  AND DATE(r.vremePocetka) =:datum  AND  ((HOUR(r.vremePocetka) < :sat) OR (HOUR(r.vremePocetka)=:sat AND (MINUTE(r.vremePocetka)<= :minuti))) AND ((HOUR(r.vremeZavrsetka)> :sat) OR (HOUR(r.vremeZavrsetka)=:sat AND (MINUTE(r.vremeZavrsetka)> :minuti)))")
    public List<Object[]> vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(@Param("datum")Date datum,@Param("sat")Integer sat,@Param("minuti") Integer minuti,@Param("id") RezervacijaID rezervacijaID,@Param("sala") int salaId);
    
    
    @Query("SELECT MIN(TIMESTAMPDIFF(MINUTE, :currentDate, r.vremePocetka)) " +
           "FROM Rezervacija r " +
           "WHERE r.vremePocetka > :currentDate AND DATE(r.vremePocetka)=DATE(:currentDate) AND r.RezervacijaID!=:id AND r.sala.id=:sala AND r.statusRezervacije!='ODBIJENA'")
    public Long findMinutesToNextReservation(@Param("currentDate") Date currentDate,@Param("id") RezervacijaID id,@Param("sala") int salaId);
}
