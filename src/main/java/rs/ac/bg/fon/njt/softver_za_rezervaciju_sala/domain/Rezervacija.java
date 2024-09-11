/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import java.util.Date;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 * @author BaNbO01
 */
@Entity
@Table(name="rezervacija")
public class Rezervacija {
    
    @EmbeddedId
    private RezervacijaID RezervacijaID;

 
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="sala_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Sala sala;


    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="korisnik_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Korisnik korisnik;
    @Column(name="vreme_pocetka")
    private Date vremePocetka;

    @Column(name="vreme_zavrsetka")
    private Date vremeZavrsetka;

    @Column(name="status_rezervacije")
    private String statusRezervacije;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "svrha_id",referencedColumnName = "id")
    private SvrhaRezervacije svrhaRezervacije;




    public Rezervacija() {
    }

    public Rezervacija(Date vremePocetka, Date vremeZavrsetka, String statusRezervacije, Sala sala, Korisnik korisnik,SvrhaRezervacije svrhaRezervacije) {
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
        this.statusRezervacije = statusRezervacije;
        this.sala = sala;
        this.korisnik = korisnik;
        this.svrhaRezervacije=svrhaRezervacije;
    }

    public RezervacijaID getRezervacijaID() {
        return RezervacijaID;
    }

    public void setRezervacijaID(RezervacijaID RezervacijaID) {
        this.RezervacijaID = RezervacijaID;
    }

    
    
    public Date getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Date vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public Date getVremeZavrsetka() {
        return vremeZavrsetka;
    }

    public void setVremeZavrsetka(Date vremeZavrsetka) {
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public String getStatusRezervacije() {
        return statusRezervacije;
    }

    public void setStatusRezervacije(String statusRezervacije) {
        this.statusRezervacije = statusRezervacije;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public SvrhaRezervacije getSvrhaRezervacije() {
        return svrhaRezervacije;
    }

    public void setSvrhaRezervacije(SvrhaRezervacije svrhaRezervacije) {
        this.svrhaRezervacije = svrhaRezervacije;
    }

    @Override
    public String toString() {
        return "Rezervacija{" + "vremePocetka=" + vremePocetka + ", vremeZavrsetka=" + vremeZavrsetka + ", statusRezervacije=" + statusRezervacije + ", sala=" + sala + ", korisnik=" + korisnik + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.RezervacijaID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rezervacija other = (Rezervacija) obj;
        if (!Objects.equals(this.statusRezervacije, other.statusRezervacije)) {
            return false;
        }
        if (!Objects.equals(this.RezervacijaID, other.RezervacijaID)) {
            return false;
        }
        if (!Objects.equals(this.sala, other.sala)) {
            return false;
        }
        if (!Objects.equals(this.korisnik, other.korisnik)) {
            return false;
        }
        if (!Objects.equals(this.vremePocetka, other.vremePocetka)) {
            return false;
        }
        if (!Objects.equals(this.vremeZavrsetka, other.vremeZavrsetka)) {
            return false;
        }
        return Objects.equals(this.svrhaRezervacije, other.svrhaRezervacije);
    }
    
    
    
    
    
}
