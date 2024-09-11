/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Korisnik
 */
public class RezervacijaDTO {
    private int salaId;
      @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String vremePocetka;
    private String vremeZavrsetka;
    private SvrhaRezervacije svrhaRezervacije;
    private String statusRezervacije;
    private int korisnikId;
    private int id;
    private String trajanje;

    public String getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(String trajanje) {
        this.trajanje = trajanje;
    }

    

    
    
    
    public int getSalaId() {
        return salaId;
    }

    public void setSalaId(int salaId) {
        this.salaId = salaId;
    }

    public int getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        this.korisnikId = korisnikId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    
    
    
    

    public String getStatusRezervacije() {
        return statusRezervacije;
    }

    public void setStatusRezervacije(String statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }
    
    
    
    

    // Getters and setters
 

    public String getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(String vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public String getVremeZavrsetka() {
        return vremeZavrsetka;
    }

    public void setVremeZavrsetka(String vremeZavrsetka) {
        this.vremeZavrsetka = vremeZavrsetka;
    }

  


    public SvrhaRezervacije getSvrhaRezervacije() {
        return svrhaRezervacije;
    }

    public void setSvrhaRezervacije(SvrhaRezervacije svrhaRezervacije) {
        this.svrhaRezervacije = svrhaRezervacije;
    }

    @Override
    public String toString() {
        return "RezervacijaDTO{" + "salaId=" + salaId + ", vremePocetka=" + vremePocetka + ", vremeZavrsetka=" + vremeZavrsetka + ", svrhaRezervacije=" + svrhaRezervacije + ", statusRezervacije=" + statusRezervacije + ", korisnikId=" + korisnikId + ", id=" + id + '}';
    }

   

    
    
}
