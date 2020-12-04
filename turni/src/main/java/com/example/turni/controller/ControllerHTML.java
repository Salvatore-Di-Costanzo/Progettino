package com.example.turni.controller;



import com.example.turni.pojo.Response;
import com.example.turni.service.HTMLService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ControllerHTML {


    @Autowired
    HTMLService htmlService;

    @GetMapping("/")
    public String Home() {
        return "welcome";
    }

    @GetMapping("/welcome")
    public String Welcome() {

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

        List<Response> listResponse = htmlService.getMultipleResponse(String.valueOf(LocalDate.now()),"2021-12-12");

        List<String> string = htmlService.getData(String.valueOf(LocalDate.now()),"2021-12-12");

        model.addAttribute("listdata" , string);
        model.addAttribute("lists" , listResponse);


        return "showturni";
    }

    @PostMapping("/deleteTurno/{data}")
    public String deleteTurno(@PathVariable(value = "data") String data) {

        htmlService.deleteTurno(data);

        return "redirect:/showturni";
    }


    @PostMapping("updateTurno")
    public String updateTurno(@RequestParam(value = "nome") String nome, @RequestParam(value = "cognome") String cognome,
                              @RequestParam(value = "index_g") int index_g) {

       htmlService.updateDependent(nome, cognome, index_g);

        if(StringUtils.isNumeric(nome) || StringUtils.isNumeric(cognome))
            return "error";

        return "redirect:/editturno";
    }


    @GetMapping("editturno")
    public String editTurno(Model model,@RequestParam(name = "data", required = false, defaultValue = "")
            String data){

        List<Response> list = htmlService.getDependents();
        List<Response> listTurni = htmlService.getList(data);

        String listData = htmlService.selectData(data);
        List<Integer> listDays = htmlService.selectDays(data);

        model.addAttribute("Days" , listDays);
        model.addAttribute("listDays", listData);
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

        if(StringUtils.isNumeric(response.getNome()) || StringUtils.isNumeric(response.getCognome()))
            return "error";


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

    @GetMapping("/login")
    public void Login(){

    }

    @GetMapping("/logout")
    public String Logout(){

        return "logout";
    }

    @GetMapping("/github")
    public void gitHub(){

    }


}
