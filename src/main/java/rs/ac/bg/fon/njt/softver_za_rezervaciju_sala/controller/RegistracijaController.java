/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Osoba;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Registracija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Roles;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Zaposleni;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.EmailService;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.KorisnikImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.OsobaImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.RegistracijaImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.ZaposleniImpl;

/**
 *
 * @author Korisnik
 */

@Controller
@RequestMapping("/register")
public class RegistracijaController {

    private KorisnikImpl korisnikImpl;
    private RegistracijaImpl registracijaImpl;
    private ZaposleniImpl zaposleniImpl;
    private EmailService emailService;
    private OsobaImpl osobaImpl;
    private PasswordEncoder passwordEncoder;

    
    @Autowired
    public RegistracijaController(KorisnikImpl korisnikImpl,RegistracijaImpl registracijaImpl
            ,ZaposleniImpl zaposleniImpl,EmailService emailService,OsobaImpl osobaImpl) {
    this.korisnikImpl = korisnikImpl;
    this.registracijaImpl = registracijaImpl;
    this.zaposleniImpl = zaposleniImpl;
    this.emailService = emailService;
    this.osobaImpl = osobaImpl;
   
    }
    
    
    @GetMapping("/confirm")
    @Transactional
    public String register(@RequestParam String token,Model model) {
      
        Registracija r = registracijaImpl.pronadjiRegistracijuPoTokenu(token);
        System.out.println(r);
        model.addAttribute("trenutnaSifra", token);
        if(r==null)
            return "register/error_message";
        else{
                    Korisnik k = new Korisnik();
                    k.setOsoba(r.getOsoba());
                    token=PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(token);
                    k.setSifra(token);
                    k.setPrivremenaSifra(true);
                    List<Roles>  roles=new ArrayList<>();
                    roles.add(new Roles("ROLE_KORISNIK"));
                    k.setRoles(roles);
                    korisnikImpl.save(k);
                    registracijaImpl.obrisi(r);
                     return "register/successful_registration";
        }
                   
                   
        
        
    }
    
    
    
    @GetMapping("/form")
    public String showRegistrationForm(HttpSession session, Model model, @RequestParam(required = false) String allow) {
        if (Boolean.TRUE.equals(session.getAttribute("allowRegistration"))) {
           session.removeAttribute("allowRegistration");
            return "register/register"; // Ovo je ime vašeg Thymeleaf templat-a za registraciju
        } else if ("true".equals(allow)) {
            session.setAttribute("allowRegistration", true);
            return "redirect:/register/form";
        } else {
    
            return "redirect:/login";
        }
    }

    @PostMapping("/result")
    public String handleRegistration(@RequestParam String email) {
        //Zaposleni zaposleni = zaposleniImpl.vratiZaposlenogSaDatimEmailom(email);
        System.out.println(email);
        Osoba osoba = osobaImpl.findOsobaByEmail(email);
        System.out.println(osoba);
        if(osoba==null){
            return "register/person_not_exist";
        }
        else{
            try {
                Korisnik korisnik = korisnikImpl.vratiKorisnikaPoOsobaId(osoba.getId());
                if(korisnik!=null){
                    return "register/already_exist";
                }
                else{
                    String token = UUID.randomUUID().toString();
                     emailService.sendRegistrationEmail(email, token);
                   
                    Registracija registracija = new Registracija(token, osoba);
                    registracijaImpl.sacuvajRegistraciju(registracija);
                     return "register/conformation_send";
                }
                
            } catch (MessagingException ex) {
                return "register/error_message";
            }
            
        }
        
       
    }
}
