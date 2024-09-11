/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author BaNbO01
 */
@Embeddable
public class RezervacijaID {
    
    @Column(name="id")
    private int id;
    @Column(name="sala_id")
    private int sala_id;
    @Column(name="korisnik_id")
    private int korisnik_id;

    public RezervacijaID() {
    }

    public RezervacijaID(int id, int sala_id, int korisnik_id) {
        this.id = id;
        this.sala_id = sala_id;
        this.korisnik_id = korisnik_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }

    public int getKorisnik_id() {
        return korisnik_id;
    }

    public void setKorisnik_id(int korisnik_id) {
        this.korisnik_id = korisnik_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + this.sala_id;
        hash = 59 * hash + this.korisnik_id;
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
        final RezervacijaID other = (RezervacijaID) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sala_id != other.sala_id) {
            return false;
        }
        return this.korisnik_id == other.korisnik_id;
    }

  

    @Override
    public String toString() {
        return "RezervacijaID{" + "id=" + id + ", sala_id=" + sala_id + ", korisnik_id=" + korisnik_id + '}';
    }
    
    
}
