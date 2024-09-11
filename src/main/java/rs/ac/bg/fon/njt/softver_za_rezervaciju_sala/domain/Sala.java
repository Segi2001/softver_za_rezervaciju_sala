/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.*;
import java.util.Objects;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="sala")
@DiscriminatorColumn(name="tip_sale")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Sala {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected int id;
    
    @Column(name="naziv")
    protected String naziv;
    
    @Column(name="napomena")
    protected String napomena;
    
    @Column(name="broj_mesta")
    protected int brojMesta;

    @Column(name = "status")
    protected boolean status;

    public Sala() {
    }

    public Sala(String naziv, String napomena, int brojMesta,boolean status) {
        this.naziv = naziv;
        this.napomena = napomena;
        this.brojMesta = brojMesta;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public int getBrojMesta() {
        return brojMesta;
    }

    public void setBrojMesta(int brojMesta) {
        this.brojMesta = brojMesta;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean imaRacunare() {
        return false;
    }

    

    @Override
    public String toString() {
        return "Sala{" + "id=" + id + ", naziv=" + naziv + ", napomena=" + napomena + ", brojMesta=" + brojMesta + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
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
        final Sala other = (Sala) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.brojMesta != other.brojMesta) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        return Objects.equals(this.napomena, other.napomena);
    }

  
    
    
    public String getTipSale() {
        if (this instanceof Amfiteatar) {
            return "Amfiteatar";
        } else if (this instanceof Kombinovana) {
            return "Kombinovana sala";
        } else if (this instanceof RCSala) {
            return "Racunarski centar";
        } else if(this instanceof SalaZaSastanke) {
            return "Sala za sastanke";
        } else
            return "Ucionica";
    }
    
    
    
}
