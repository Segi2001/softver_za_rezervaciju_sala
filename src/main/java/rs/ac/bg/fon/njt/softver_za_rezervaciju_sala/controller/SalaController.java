/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Amfiteatar;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Kombinovana;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.RCSala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Sala;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalaDTO;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.SalaZaSastanke;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.domain.Ucionica;
import rs.ac.bg.fon.njt.softver_za_rezervaciju_sala.repository.impl.SalaImpl;

/**
 *
 * @author Stefan
 */
@Controller
@RequestMapping("/hall")
public class SalaController {

    private SalaImpl salaImpl;

    @Autowired
    public SalaController(SalaImpl salaImpl) {
        this.salaImpl = salaImpl;
    }
    
    @ResponseBody
     @GetMapping("/allHallsArray")
    public List<Sala> vratiSveSale2() {
        List<Sala> sale = salaImpl.vratiAktivneSale();
        return sale;

    }
    
    

    @GetMapping("/allHalls")
    public String vratiSveSale(Model model) {

        List<Sala> sale = salaImpl.vratiAktivneSale();
        model.addAttribute("sale", sale);
        System.out.println("Evo su sve sale:" + sale);
        return "sala/allHalls";

    }

    @GetMapping("/createHall")
    public String kreirajSalu(@RequestParam("tip") String tip, Model model) {

        System.out.println("tip je: " + tip);
        SalaDTO sala = new SalaDTO();
        model.addAttribute("sala", sala);
        model.addAttribute("tip", tip);

        return "sala/hallsSave";
    }

    @PostMapping("/saveHall")
    public String saveSala(@RequestParam("tip") String tip, @ModelAttribute("sala") SalaDTO salaDTO) {

        System.out.println("tip je: " + tip);
        System.out.println("ID JE:"+salaDTO.getId());
        if (salaDTO.getId() != 0) {
            Sala s = salaImpl.findSalaById(Long.valueOf(salaDTO.getId()));
            s.setNaziv(salaDTO.getNaziv());
            s.setStatus(salaDTO.isStatus());
            s.setNapomena(salaDTO.getNapomena());
            s.setBrojMesta(salaDTO.getBrojMesta());
            if (s instanceof Kombinovana) {
                
                ((Kombinovana) s).setBrojRacunara(salaDTO.getBrojRacunara());
                salaImpl.updateSale(s);
                 return "redirect:/hall/allHalls";

            }
            if (s instanceof RCSala) {
                
                ((RCSala) s).setBrojRacunara(salaDTO.getBrojRacunara());
                salaImpl.updateSale(s);
                 return "redirect:/hall/allHalls";

            }
            salaImpl.updateSale(s);
            return "redirect:/hall/allHalls";

        } else {

            switch (tip) {
                case "Kombinovana": {

                    Kombinovana s = new Kombinovana();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    s.setBrojRacunara(salaDTO.getBrojRacunara());
                    salaImpl.updateSale(s);
                    return "redirect:/hall/allHalls";

                }
                case "RC sala": {
                    RCSala s = new RCSala();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    s.setBrojRacunara(salaDTO.getBrojRacunara());
                    salaImpl.updateSale(s);
                    return "redirect:/hall/allHalls";

                }

                case "Amfiteatar": {
                    Amfiteatar s = new Amfiteatar();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return "redirect:/hall/allHalls";

                }

                case "Sala za sastanke": {
                    SalaZaSastanke s = new SalaZaSastanke();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return "redirect:/hall/allHalls";

                }

                case "Ucionica": {
                    Amfiteatar s = new Amfiteatar();
                    s.setNaziv(salaDTO.getNaziv());
                    s.setNapomena(salaDTO.getNapomena());
                    s.setStatus(salaDTO.isStatus());
                    s.setBrojMesta(salaDTO.getBrojMesta());
                    salaImpl.updateSale(s);
                    return "redirect:/hall/allHalls";

                }

                default: {
                    return "redirect:/hall/allHalls";
                }

            }

        }

    }

    @GetMapping("/updateHall")
    public String updateHall(@RequestParam("id") int id, Model model) {

        System.out.println("Id je:" + id);

        Sala sala = salaImpl.findSalaById(Long.valueOf(id));
        SalaDTO salaDto = new SalaDTO();
        salaDto.setNaziv(sala.getNaziv());
        salaDto.setBrojMesta(sala.getBrojMesta());
        salaDto.setNapomena(sala.getNapomena());
        salaDto.setStatus(sala.isStatus());
        salaDto.setId(sala.getId());
        String tip = "";

        if (sala instanceof Kombinovana) {
            salaDto.setBrojRacunara(((Kombinovana) sala).getBrojRacunara());
            tip = "Kombinovana";
        }
        if (sala instanceof RCSala) {
            salaDto.setBrojRacunara(((RCSala) sala).getBrojRacunara());
            tip="RC sala";

        }
        if(sala instanceof SalaZaSastanke)
            tip="Sala za sastanke";
        
        if(sala instanceof Ucionica)
            tip="Ucionica";
        if(sala instanceof Amfiteatar)
            tip="Amfiteatar";
        
        model.addAttribute("sala",salaDto);
        model.addAttribute("tip",tip);
        
        return "sala/hallsSave";

    }
    
    @PostMapping("/deleteHall")
    public String deleteHall(@RequestParam("id") int id){
        
        Sala sala=salaImpl.findSalaById(Long.valueOf(id));
        
        salaImpl.obrisiSalu(sala);
        System.out.println("Id je: " + id);
        System.out.println("Obrisana sala");
        return "redirect:/hall/allHalls";        
    }
}
