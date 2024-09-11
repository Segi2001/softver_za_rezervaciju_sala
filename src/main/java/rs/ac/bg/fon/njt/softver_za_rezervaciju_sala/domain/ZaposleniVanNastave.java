/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="zaposleni_van_nastave")
@DiscriminatorValue("ZNN")
public class ZaposleniVanNastave extends Zaposleni {

    public ZaposleniVanNastave() {
                System.out.println("konstruktor zaposleniVanNastavi");

    }

    public ZaposleniVanNastave(String ime, String prezime, String email, String titula) {
        super(ime, prezime, email, titula);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    
    
    
}
