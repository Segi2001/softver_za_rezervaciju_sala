/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name="student")
@DiscriminatorValue("S")
public class Student extends Osoba {





    @Column(name="svrha_pristupa")
    private String svrhaPristupa;

    public Student() {
    }


    public Student(String ime, String prezime, String email, String svrhaPristupa) {
        super(ime, prezime, email);
        this.svrhaPristupa = svrhaPristupa;
    }

    public String getSvrhaPristupa() {
        return svrhaPristupa;
    }

    public void setSvrhaPristupa(String svrhaPristupa) {
        this.svrhaPristupa = svrhaPristupa;
    }
    @Override
    public String toString() {
        return super.toString()+"svrha pristupa: "+svrhaPristupa;
    }
    
    
}
