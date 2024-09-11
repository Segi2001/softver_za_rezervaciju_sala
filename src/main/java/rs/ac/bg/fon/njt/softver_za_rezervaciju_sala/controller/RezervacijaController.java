/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Korisnik;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Rezervacija;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaDTO;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RezervacijaID;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalePoDanu;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SvrhaRezervacije;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.EmailService;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.KorisnikImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.RezervacijaImpl;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.SalaImpl;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Korisnik
 */
@Controller
@RequestMapping("/reservation")
public class RezervacijaController {

    private RezervacijaImpl rezervacijaImpl;
    private KorisnikImpl korisnikImpl;
    private SalaImpl salaImpl;
    private EmailService emailService;

    @Value("${trajanja}")
    private List<String> trajanja;

    public RezervacijaController() {
    }

    @Autowired
    public RezervacijaController(RezervacijaImpl rezervacijaImpl, SalaImpl salaImpl, KorisnikImpl korisnikImpl, EmailService emailService) {
        this.rezervacijaImpl = rezervacijaImpl;
        this.salaImpl = salaImpl;
        this.korisnikImpl = korisnikImpl;
        this.emailService = emailService;
    }
    
    
    
    @ResponseBody
    @GetMapping("/getMinutesTillNextReservation")
      public Long getMinutesTillNextReservation(@RequestParam("date") String strDatumPocetka,
        @RequestParam("salaId") int salaId,@RequestParam("id") int id,@RequestParam("korisnikId") int korisnikId){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date datumPocetka = sdf.parse(strDatumPocetka);
            LocalDateTime novoVreme = datumPocetka.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
            Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, salaId, id, korisnikId);
            System.out.println("Minuti do sledece rezervacije: " + minutes);
            return minutes;
        } catch (ParseException ex) {
            Logger.getLogger(RezervacijaController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
      }
    
  
    @ResponseBody
    @GetMapping("availableReservations")
    public List<String> availableReservations(@RequestParam("date") String strDatumPocetka,
        @RequestParam("salaId") int salaId,@RequestParam("id") int id,@RequestParam("korisnikId") int korisnikId) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date datumPocetka = sdf.parse(strDatumPocetka);
            List<String> niz = new ArrayList<>();
            List<Long> minuti = new ArrayList<>();
            for (int i = 8; i < 23; i++) {
                for (int j = 0; j < 60; j += 15) {
                    
                    
                    List<Object[]> nizObjekata = rezervacijaImpl.vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(datumPocetka, i, j,salaId,id,korisnikId);
                    for (Object[] objects : nizObjekata) {
                        Date vremePocetka = (Date) objects[1];
                        Date vremeZavrsetka = (Date) objects[2];
                        String status = (String) objects[3];
                        LocalDate localDatumPocetka = LocalDate.ofInstant(datumPocetka.toInstant(), ZoneId.systemDefault());
                        LocalDateTime novoVreme = localDatumPocetka.atTime(i, j);
                        
                        Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, salaId,id,korisnikId);
                        
                        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        minuti.add(minutes);
                        if ((minutes == null || minutes >= 60) && (vremePocetka == null || status.equals("ODBIJENA"))) {
                            if(j==0){
                                niz.add(i + "h");
                            }else{
                                niz.add(i + "h" + " " + j + "min");
                            }
                            
                            
                            
                        }
                    }
                }
            }
            
            List<Object[]> nizObjekata = rezervacijaImpl.vratiSlobodneTermineZaSaluZaKonkretniDatumBezProsledjeneRezervacije(datumPocetka, 23, 0,salaId,id,korisnikId);
            for (Object[] objects : nizObjekata) {
                Date vremePocetka = (Date) objects[1];
                Date vremeZavrsetka = (Date) objects[2];
                String status = (String) objects[3];
                LocalDate localDatumPocetka = LocalDate.ofInstant(datumPocetka.toInstant(), ZoneId.systemDefault());
                LocalDateTime novoVreme = localDatumPocetka.atTime(23,0);
                
                Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, salaId,id,korisnikId);
                
                DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                if ((minutes == null || minutes >= 60) && (vremePocetka == null || status.equals("ODBIJENA"))) {
                    niz.add(23 + "h" + " " + 0 + "min");
                }
                
            }
            for (Long minut : minuti) {
                System.out.println("Vreme do sledece rezervacije: " + minut);
            }
            
            for (String string: niz) {
                System.out.println(string);
            }
           return niz;
        } catch (ParseException ex) {
           return null;
        }
        

    }

    
      @ResponseBody
    @GetMapping("/allDurationForOneReservation")
    public List<String> allDurationForOneReservation(){
        return trajanja;
    }

    
    
    @GetMapping("/chooseDate")
    public String chooseDate() {

        return "rezervacija/chooseDate";
    }

    @GetMapping("/hallsPerDay")
    public String listSalePoDanu(@RequestParam("datum") String datumStr, Model model) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date datum = null;
            try {
                datum = sdf.parse(datumStr);
            } catch (ParseException e) {
                e.printStackTrace();
                // handle error
            }

            List<SalePoDanu> rezervacije = rezervacijaImpl.vratiZauzetostiZaDan(datum);
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd. MMMM yyyy", new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build());
            Date dat = sdf.parse(datumStr);
            model.addAttribute("datum", formatter.format(dat));
            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/hallsPerDay";

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/createReservation")
    public String showCreateRezervacijaForm(Model model, @RequestParam("salaid") Integer salaId, @RequestParam("datum") String datum, @RequestParam("sati") int sati, @RequestParam("minut") int minut) {
        try {
            Sala sala = salaImpl.findSalaById(Long.valueOf(salaId));
            model.addAttribute("sala", sala);

            System.out.println(datum);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

            RezervacijaDTO rezervacijaDTO = new RezervacijaDTO();
            rezervacijaDTO.setSalaId(salaId);

            System.out.println("minuti: " + minut);

            ZonedDateTime zonedDateTime = ZonedDateTime.parse(datum, formatter.withZone(ZoneId.of("Europe/Paris")));

            // Konvertovanje u LocalDateTime
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

            DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            LocalDateTime novoVreme = localDateTime.withHour(sati);
            novoVreme = novoVreme.plusMinutes(minut);
            Long minutes = rezervacijaImpl.numberOfMinutesDifference(novoVreme, rezervacijaDTO.getSalaId());
            String strVremePocetka = novoVreme.format(newFormat);

            rezervacijaDTO.setVremePocetka(strVremePocetka);

            novoVreme = novoVreme.plusHours(1);

            String strVremeZavrsetka = novoVreme.format(newFormat);
            rezervacijaDTO.setVremeZavrsetka(strVremeZavrsetka);
            model.addAttribute("minute", minutes);
            model.addAttribute("trajanja", trajanja);
            model.addAttribute("rezervacijaDTO", rezervacijaDTO);
            return "rezervacija/createReservation";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @PostMapping("/createReservation")
    public String createRezervacija(@ModelAttribute("rezervacijaDTO") RezervacijaDTO rezervacijaDTO, @RequestParam("vremePocetka") String vremePocetkaHour,
            @RequestParam("vremeZavrsetka") String vremeZavrsetkaHour, @AuthenticationPrincipal User user, Model model) {
        try {
            Rezervacija rezervacija = new Rezervacija();
            Sala sala = salaImpl.findSalaById(Long.valueOf(rezervacijaDTO.getSalaId()));
            rezervacija.setSala(sala);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date vremePocetka = (Date) formatter.parse(vremePocetkaHour);
            Date vremeZavrsetka = (Date) formatter.parse(vremeZavrsetkaHour);
            Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
            // Postavljanje vremena početka i završetka u rezervacijuDTO
            rezervacija.setVremePocetka(vremePocetka);
            rezervacija.setVremeZavrsetka(vremeZavrsetka);
            rezervacija.setStatusRezervacije("U CEKANJU");
            RezervacijaID rezervacijaID = new RezervacijaID();
            rezervacijaID.setSala_id(rezervacija.getSala().getId());
            rezervacijaID.setKorisnik_id(korisnik.getId());
            rezervacija.setRezervacijaID(rezervacijaID);
            SvrhaRezervacije svrhaRezervacije = rezervacijaDTO.getSvrhaRezervacije();
            rezervacija.setSvrhaRezervacije(svrhaRezervacije);

            // Sačuvaj rezervaciju
            rezervacijaImpl.sacuvajRezervaciju(rezervacija);
            return "redirect:/reservation/myReservations";

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/deleteReservation")
    public String deleteReservation(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);

            Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(rezervacijaID);
            rezervacija.setStatusRezervacije("ODBIJENA");
            rezervacijaImpl.sacuvajRezervaciju(rezervacija);
            emailService.cancelReservation(rezervacija);

            return "redirect:/reservation/allReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/confirmReservation")
    public String confirmReservation(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);
            Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(rezervacijaID);
            rezervacija.setStatusRezervacije("POTVRDJENA");
            rezervacijaImpl.sacuvajRezervaciju(rezervacija);
            emailService.conformationReservation(rezervacija);
            return "redirect:/reservation/allReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/deleteMyReservation")
    public String delete(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);
            rezervacijaImpl.deleteRezervacijaById(rezervacijaID);
            return "redirect:/reservation/myReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/myReservations")
    public String myReservations(@AuthenticationPrincipal User user, Model model) {
        try {
            Korisnik korisnik = korisnikImpl.vratiKorisnikaSaZadatimUsername(user.getUsername());
            List<Rezervacija> rezervacije = rezervacijaImpl.findRezervacijaByUser(korisnik.getId());
            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/myReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    private Date getDateWithHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @GetMapping("/allReservations")
    public String listRezervacije(Model model) {
        try {
            List<Rezervacija> rezervacije = rezervacijaImpl.findAllRezervacije();
            model.addAttribute("rezervacije", rezervacije);
            return "rezervacija/allReservations";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @GetMapping("/updateReservation")
    public String showFormForUpdate(@RequestParam("salaId") Integer salaId, @RequestParam("korisnikId") Integer korisnikId, @RequestParam("id") Integer id, Model model) {
        try {
            RezervacijaID rezervacijaID = new RezervacijaID(id, salaId, korisnikId);

            System.out.println(rezervacijaID.toString().isBlank());
            Rezervacija rezervacija = rezervacijaImpl.findRezervacijaById(rezervacijaID);
            RezervacijaDTO rezervacijaDTO = new RezervacijaDTO();
            rezervacijaDTO.setId(id);
            rezervacijaDTO.setKorisnikId(korisnikId);
            rezervacijaDTO.setSalaId(rezervacija.getSala().getId());
            rezervacijaDTO.setStatusRezervacije(rezervacija.getStatusRezervacije());
            rezervacijaDTO.setSvrhaRezervacije(rezervacija.getSvrhaRezervacije());
            System.out.println(rezervacija.getSvrhaRezervacije());
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            rezervacijaDTO.setVremePocetka(formatter.format(rezervacija.getVremePocetka()));
            rezervacijaDTO.setVremeZavrsetka(formatter.format(rezervacija.getVremeZavrsetka()));
            model.addAttribute("rezervacijaDTO", rezervacijaDTO);

            // Dodavanje potrebnih podataka za prikazivanje opcija u formi
            return "rezervacija/updateReservation";
        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

    @PostMapping("/updateReservation")
    public String saveRezervacija(@ModelAttribute("rezervacijaDTO") RezervacijaDTO rezervacijaDTO, Model model) {
        try {
            
            
            Rezervacija r = rezervacijaImpl.findRezervacijaById(new RezervacijaID(rezervacijaDTO.getId(), rezervacijaDTO.getSalaId(), rezervacijaDTO.getKorisnikId()));
            Rezervacija backup = new Rezervacija();
            backup.setStatusRezervacije(r.getStatusRezervacije());
            SvrhaRezervacije svrhaRezervacije = new SvrhaRezervacije();
            svrhaRezervacije.setOpis(r.getSvrhaRezervacije().getOpis());
            svrhaRezervacije.setOrganizator(r.getSvrhaRezervacije().getOrganizator());
            svrhaRezervacije.setSvrha(r.getSvrhaRezervacije().getSvrha());
            backup.setSvrhaRezervacije(svrhaRezervacije);
            backup.setVremePocetka(r.getVremePocetka());
            backup.setVremeZavrsetka(r.getVremeZavrsetka());
            
            r.setSvrhaRezervacije(rezervacijaDTO.getSvrhaRezervacije());
            r.setStatusRezervacije("U CEKANJU");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            r.setVremePocetka(formatter.parse(rezervacijaDTO.getVremePocetka()));
            r.setVremeZavrsetka(formatter.parse(rezervacijaDTO.getVremeZavrsetka()));
            System.out.println("Vreme pocetka:"+r.getVremePocetka());
            System.out.println("Vreme zavrsetka:"+r.getVremeZavrsetka());
            System.out.println(r.getSvrhaRezervacije());
            System.out.println(r.getSvrhaRezervacije());
            rezervacijaImpl.sacuvajRezervaciju(r);

            if (r.getStatusRezervacije().equals(backup.getStatusRezervacije())
                    && r.getSvrhaRezervacije().equals(backup.getSvrhaRezervacije())
                    && r.getVremePocetka().equals(backup.getVremePocetka())
                    && r.getVremeZavrsetka().equals(backup.getVremeZavrsetka())) {

                return "redirect:/reservation/allReservations";

            } else if (!r.getStatusRezervacije().equals(backup.getStatusRezervacije())
                    && r.getSvrhaRezervacije().equals(backup.getSvrhaRezervacije())
                    && r.getVremePocetka().equals(backup.getVremePocetka())
                    && r.getVremeZavrsetka().equals(backup.getVremeZavrsetka())) {
                emailService.conformationReservation(r);
                return "redirect:/reservation/allReservations";

            } else {
                emailService.changeDataReservation(r, backup);
                return "redirect:/reservation/allReservations";
            }

        } catch (Exception ex) {
            model.addAttribute("errorText", ex.getMessage());
            return "rezervacija/errorPage";
        }

    }

}
