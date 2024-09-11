/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;



/**
 *
 * @author Korisnik
 */

@Service
public class EmailService {
 

    public EmailService() {
    }

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
  
 
    private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String host;
    public void sendRegistrationEmail(String toEmail, String token) throws MessagingException {
      try {
          MimeMessage message = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true);
          helper.setFrom(host,"FON");
          helper.setTo(toEmail);
          helper.setSubject("Potvrda registracije");
          helper.setText("Kliknite na sledeći link da potvrdite registraciju: http://localhost:8080/register/confirm?token=" + token);
          
          
          javaMailSender.send(message);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    
    public void cancelReservation(Rezervacija rezervacija) throws MessagingException{
     
        
         try {
          MimeMessage message = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true);
          helper.setFrom(host,"FON");
          helper.setTo(rezervacija.getKorisnik().getOsoba().getEmail());
          helper.setSubject("Otkazivanje rezervacije");
          
          DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
          
          
          helper.setText("Poštovani/a "+ rezervacija.getKorisnik().getOsoba().getIme() + "\n" +
"Sa žaljenjem Vas obaveštavamo da smo primorani da otkažemo Vašu rezervaciju za salu: "+ rezervacija.getSala().getNaziv() + " zakazanu za " +formatter.format(rezervacija.getVremePocetka()) +  "\n" +
"Nažalost, zbog neočekivanih okolnosti, sala neće biti dostupna u planiranom terminu. Razumemo koliko je ovo važno za Vas i iskreno se izvinjavamo zbog nastalih neprijatnosti.\n\n\n" +
"Ukoliko želite, možemo Vam ponuditi alternativni termin ili Vam pomoći u pronalaženju druge odgovarajuće sale. Molimo Vas da nas kontaktirate na " +host + " kako bismo zajedno našli najbolje moguće rešenje.\n" +
"Još jednom se izvinjavamo zbog ove situacije i zahvaljujemo se na razumevanju.\n\n" +
"Srdačno,\n Fakultet organizacionih nauka");

          
          
          javaMailSender.send(message);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
      }
        
        
        
    }
    
    
    
    public void conformationReservation(Rezervacija rezervacija) throws MessagingException{
        
          try {
          MimeMessage message = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true);
          helper.setFrom(host,"FON");
          helper.setTo(rezervacija.getKorisnik().getOsoba().getEmail());
           DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
          helper.setSubject("Potvrda Vaše Rezervacije - " + rezervacija.getSala().getNaziv()+" za " + formatter.format( rezervacija.getVremePocetka()));
          
         
          
          
          
          
          
          
          
            DateFormat formatterVreme = new SimpleDateFormat("dd-MM-yyyy HH:mm");
          
          
          
          
          helper.setText("Poštovani/a "+ rezervacija.getKorisnik().getOsoba().getIme() + "\n" +
"Zadovoljstvo nam je obavestiti Vas da je Vaša rezervacija za " + rezervacija.getSala().getNaziv() +  " za datum " + formatter.format(rezervacija.getVremePocetka() ) +" uspešno potvrđena.\n\n"
          + "Detalji Vaše rezervacije:\n" + 
                  "* Sala: " + rezervacija.getSala().getNaziv()+"\n"
                + "* Vreme pocetka: " + formatterVreme.format(rezervacija.getVremePocetka())+"\n"
                + "* Vreme zavrsetka: " + formatterVreme.format(rezervacija.getVremeZavrsetka()) + "\n"
                +"Ukoliko imate bilo kakva dodatna pitanja ili potrebe za promenama, slobodno nas kontaktirajte na "+ host +" . Rado ćemo Vam pomoći.\n" +
"Hvala Vam što ste odabrali našu uslugu. Radujemo se Vašem dolasku.\n" +
"Srdačno,\n"
                  +" Fakultet organizacionih nauka"
          
          
          
          
          
          );

          
          
          javaMailSender.send(message);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
      }
        
        
        
        
        
        
    }
    
    
    
    
    public void changeDataReservation(Rezervacija rezervacija,Rezervacija backup) throws MessagingException{
         try {
          MimeMessage message = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(message, true);
          helper.setFrom(host,"FON");
          helper.setTo(rezervacija.getKorisnik().getOsoba().getEmail());
          DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
          helper.setSubject("Ažuriranje Vaše Rezervacije - "+ rezervacija.getSala().getNaziv() +  " za " + dateFormat.format(rezervacija.getVremePocetka()));
          
          String text = "";
          
          
          DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
          
          
          if(!rezervacija.getVremePocetka().equals(backup.getVremePocetka()))
          {
              text+="* Vreme pocetka: " + formatter.format(rezervacija.getVremePocetka()) + "\n" + "  (prethodno:  " + formatter.format(backup.getVremePocetka()) + ")\n\n";
          }
          
          if(!rezervacija.getVremeZavrsetka().equals(backup.getVremeZavrsetka()))
          {
              text+="* Vreme zavrsetka: " + formatter.format(rezervacija.getVremeZavrsetka()) + "\n" + "  (prethodno:  " + formatter.format(backup.getVremeZavrsetka()) + " )\n\n";
          }
          
          
          if(!rezervacija.getSvrhaRezervacije().getOpis().equals(backup.getSvrhaRezervacije().getOpis())){
              text+="* Opis: " + rezervacija.getSvrhaRezervacije().getOpis() + "\n" + "  (prethodno: " + backup.getSvrhaRezervacije().getOpis() + ")\n\n";
          }
          
           if(!rezervacija.getSvrhaRezervacije().getOrganizator().equals(backup.getSvrhaRezervacije().getOrganizator())){
              text+="* Organizator: " + rezervacija.getSvrhaRezervacije().getOrganizator()+ "\n" + "  (prethodno: " + backup.getSvrhaRezervacije().getOrganizator()+ ")\n\n";
          }
           
            if(!rezervacija.getSvrhaRezervacije().getSvrha().equals(backup.getSvrhaRezervacije().getSvrha())){
              text+="* Svrha: " + rezervacija.getSvrhaRezervacije().getSvrha()+ "\n" + "  (prethodno: " + backup.getSvrhaRezervacije().getSvrha()+ ")\n\n";
          }
            
            if(!rezervacija.getStatusRezervacije().equals(backup.getStatusRezervacije())){
                text+="* Status rezervacije: " + rezervacija.getStatusRezervacije()+ "\n" + "  (prethodno: " + backup.getStatusRezervacije()+ ")\n\n";
            }
        
          helper.setText("Poštovani/a "+ rezervacija.getKorisnik().getOsoba().getIme() + ",\n" +
"Obaveštavamo Vas da su izvršene izmene u Vašoj rezervaciji za " + rezervacija.getSala().getNaziv() + ". Molimo Vas da pregledate sledeće ažurirane detalje:\n"+
          "Detalji Vaše rezervacije:\n\n" + text + "\n" + "Napomena: U gore navedenim detaljima navedene su samo one informacije koje su promenjene.\n" +
"Ukoliko imate bilo kakva dodatna pitanja ili želite da razgovarate o ovim promenama, molimo Vas da nas kontaktirate na " + host + " .\nRadujemo se što možemo da Vam pomognemo i unapred zahvaljujemo na razumevanju.\n" +
"Srdačno,\n"
                  + "Fakultet organizacionih nauka"
          );

          
          
          javaMailSender.send(message);
      } catch (UnsupportedEncodingException ex) {
          Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
      }
        
        
        
        
    }
}
