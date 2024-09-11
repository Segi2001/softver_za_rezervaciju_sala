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
@Table(name="zaposleni_u_nastavi")
@DiscriminatorValue("ZN")
public class ZaposleniUNastavi extends Zaposleni{
    
    @Column(name="zvanje")
    private String zvanje;
    
    @Column(name="katedra")
    private String katedra;

    public ZaposleniUNastavi() {
        System.out.println("konstruktor zaposleniUNastavi");
    }

    public ZaposleniUNastavi(String zvanje, String katedra, String ime, String prezime, String email, String titula) {
        super(ime, prezime, email, titula);
        this.zvanje = zvanje;
        this.katedra = katedra;
    }

    public String getZvanje() {
        return zvanje;
    }

    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }

    public String getKatedra() {
        return katedra;
    }

    public void setKatedra(String katedra) {
        this.katedra = katedra;
    }

    @Override
    public String toString() {
        return super.toString() + "zvanje=" + zvanje + ", katedra=" + katedra ;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.zvanje);
        hash = 13 * hash + Objects.hashCode(this.katedra);
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
        if(!super.equals(obj)){
            return false;
        }
        final ZaposleniUNastavi other = (ZaposleniUNastavi) obj;
        if (!Objects.equals(this.zvanje, other.zvanje)) {
            return false;
        }
        return Objects.equals(this.katedra, other.katedra);
    }
    
    
    
}
