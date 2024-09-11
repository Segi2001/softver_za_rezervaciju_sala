package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Entity
@Table(name = "korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="sifra")
    private String sifra;

    @OneToOne(cascade ={ CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "osoba_id",referencedColumnName = "id")
    private Osoba osoba;
    
    @Column(name="admin")
    private boolean admin;
    
    @Column(name="privremena_sifra")
    private boolean privremenaSifra;

    @OneToMany(cascade =CascadeType.ALL)
    @JoinColumn(name = "korisnik_id")
    private List<Roles> roles;

    public Korisnik() {
    }

    public Korisnik(String sifra, Osoba osoba, boolean admin, boolean privremenaSifra) {
        this.sifra = sifra;
        this.osoba = osoba;
        this.admin = admin;
        this.privremenaSifra = privremenaSifra;
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPrivremenaSifra() {
        return privremenaSifra;
    }

    public void setPrivremenaSifra(boolean privremenaSifra) {
        this.privremenaSifra = privremenaSifra;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Korisnik{" + "id=" + id + ", sifra=" + sifra + ", osoba=" + osoba + ", admin=" + admin + ", privremenaSifra=" + privremenaSifra + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Korisnik other = (Korisnik) obj;
        return this.id == other.id;
    }

    

    
}
