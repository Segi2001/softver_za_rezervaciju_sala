package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.ApiKeyService;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.EncryptionService;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.KorisnikImpl;

@Controller
public class LoginController {
   
    private KorisnikImpl korisnikImpl;
    public LoginController(KorisnikImpl korisnikImpl) {
        this.korisnikImpl = korisnikImpl;
    
    }

    

    @GetMapping("/login")
    public String login(){

        return "login/fancy-login";

    }
     @PostMapping("/changePassword")
    public String promeniSifru(@RequestParam("novaSifra")String novaSifra, @AuthenticationPrincipal User user) {
        Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
         
        novaSifra = "{bcrypt}" + new BCryptPasswordEncoder().encode(novaSifra);
        korisnik.setSifra(novaSifra);
        korisnik.setPrivremenaSifra(false);
        korisnikImpl.update(korisnik);
        return "redirect:/main";
    }
    
      @GetMapping("/changePassword")
    public String showPromenaSifrePage() {
          
        return "login/changePassword";
    }
    
     @GetMapping("/main")
    public String postLogin(@AuthenticationPrincipal User user) {
        Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
         System.out.println(korisnik);
        if (korisnik.isPrivremenaSifra()) {
            return "redirect:/changePassword";
        } else {
            return "rezervacija/index";
        }
    }
    
    
    @GetMapping("/access-denied")
    public String accessedDeniedPage(){
        return "login/denied-page";
    }
    
   
}
