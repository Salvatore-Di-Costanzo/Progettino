package com.example.turni.controller;


import com.example.turni.pojo.Response;
import com.example.turni.pojo.Turno;
import com.example.turni.service.HTMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ControllerHTML {


    @Autowired
    HTMLService htmlService;

    @GetMapping("/welcome")
    public String Welcome(Model model) {
        String welcome = "Benvenuto";

        model.addAttribute("welcome", welcome);
        return "welcome";
    }

    @GetMapping("/selectTurno")
    public String getTurni(@RequestParam(name = "dataInizio") String dataInizio,@RequestParam(name="dataFine")String dataFine,
                           Model model) {

        List<Response> listResponse = htmlService.getMultipleResponse(dataInizio,dataFine);

        List<String> string = htmlService.getData(dataInizio,dataFine);

        model.addAttribute("listdata" , string);
        model.addAttribute("lists" , listResponse);

        return "selectturno";
    }

    @PostMapping("/turniDays")
    public String creaTurni(@RequestParam(name = "nGiornate") int nGiornate, Model model ,@RequestParam(name = "nDipendenti") int nDipendenti) {

        String string = htmlService.creaTurni(nGiornate,nDipendenti);

        model.addAttribute("createturns", string);

        return "createturni";
    }

    @GetMapping("/showturni")
    public String showTurni(Model model) {

        List<Turno> list = htmlService.showAllTurns();

        model.addAttribute("turni", list);

        return "showturni";
    }

    @PostMapping("/deleteTurno/{id}")
    public String deleteTurno(@PathVariable(value = "id") int id) {

        htmlService.deleteTurno(id);

        return "redirect:/showturni";
    }


    @PostMapping("updateTurno")
    public String updateTurno(@RequestParam(value = "index_g") int index_g, Turno turno, @RequestParam(value = "index_d") String index_d) {

        turno.setIndex_d(index_d);

        htmlService.updateTurno(index_d, index_g);

        return "redirect:/editturno";
    }


    @GetMapping("editturno")
    public String editTurno(Model model,@RequestParam(name = "data", required = false, defaultValue = "")
            String data){

        List<Response> list = htmlService.getDependents();
        List<Response> listTurni = htmlService.getList(data);



        List<Integer> listIDgiorni = htmlService.queryIdDays();

        model.addAttribute("listDays", listIDgiorni);
        model.addAttribute("listTurni", listTurni);
        model.addAttribute("dependents" , list);


        return "editturno";
    }


    @GetMapping("/dipendenti")
    public String getDependents(Model model, @SpringQueryMap String keyword) {

        List<Response> list = htmlService.getDependents();

        if (keyword != null)
            model.addAttribute("dependents", htmlService.findByKeyword(keyword));
        else
            model.addAttribute("dependents", list);

        return "dipendenti";
    }


    @PostMapping("/deleteDependent/{id}")
    public String deleteDependent(@PathVariable(value = "id") String id) {

        htmlService.deleteDependent(id);

        return "redirect:/dipendenti";
    }


    @PostMapping("/addDependent")
    public String postDependent(Model model, @ModelAttribute("response") Response response) {

        model.addAttribute("nome", response.getNome());
        model.addAttribute("cognome", response.getCognome());
        model.addAttribute("salary", response.getSalary());

        htmlService.postDependent(response);

        return "register";
    }

    @GetMapping("/register")
    public String registerPage() {

        return "register";
    }

    @GetMapping("/turni")
    public String turniPage() {

        return "turni";
    }

    @GetMapping("/createturni")
    public String createTurni() {

        return "createturni";
    }

    @GetMapping("/editdipendenteturno")
    public String editTurno(){

        return "editdipendenteturno";
    }

}
