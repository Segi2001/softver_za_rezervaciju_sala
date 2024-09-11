/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "kombinovana")
@DiscriminatorValue("KOM")
public class Kombinovana extends Sala {

    @Column(name = "broj_racunara")
    private Integer brojRacunara;

    public Integer getBrojRacunara() {
        return brojRacunara;
    }

    public void setBrojRacunara(Integer brojRacunara) {
        this.brojRacunara = brojRacunara;
    }

    @Override
    public boolean imaRacunare() {
        return true;
    }

    public Kombinovana() {
    }

    public Kombinovana(int brojRacunara, String naziv, String napomena, Integer brojMesta, boolean status) {
        super(naziv, napomena, brojMesta, status);
        this.brojRacunara = brojRacunara;
    }

    @Override
    public String toString() {
        return "Kombinovana: " + super.toString() + " broj racunara: " + brojRacunara;
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
        if (!super.equals(obj)) {
            return false;
        }
        final Kombinovana other = (Kombinovana) obj;
        return Objects.equals(this.brojRacunara, other.brojRacunara);
    }

}