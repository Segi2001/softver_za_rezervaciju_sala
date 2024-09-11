/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

/**
 *
 * @author Korisnik
 */
public class SalaDTO {
    private int id;
    private String naziv;
    private String napomena;
    private int brojMesta;
    private boolean status;
    private String tipSale;
    private int brojRacunara;

    // Getteri i setteri

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

    public String getTipSale() {
        return tipSale;
    }

    public void setTipSale(String tipSale) {
        this.tipSale = tipSale;
    }

    public int getBrojRacunara() {
        return brojRacunara;
    }

    public void setBrojRacunara(int brojRacunara) {
        this.brojRacunara = brojRacunara;
    }
}

