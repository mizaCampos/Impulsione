package com.impulsione.rest;

import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Evento;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/evento")
public interface RestEvento {


    @GetMapping("/registerEvent")
    String register(Model model, Principal principal);


    @PostMapping("/register-new")
    public String addNewEvent(@ModelAttribute("evento")Evento evento,
                              @RequestParam("imageEvent")MultipartFile imageProduct,
                              BindingResult result,
                              Model model, Principal principal,
                              RedirectAttributes attributes
                              );


    //todos eventos
    @GetMapping("")
    public String eventos(Model model, Principal principal);

    @GetMapping("/update-evento/{id}")
    public String updateEventoForm(@PathVariable("id") Long id, Model model, Principal principal);

    @PostMapping("/update-evento/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("eventoDTO") EventoDTO eventoDTO,
                                @RequestParam("imageEvent")MultipartFile imageEvent,
                                RedirectAttributes attributes);

    @GetMapping("/delete/{id}")
    public String deleteEvento(@PathVariable("id") Long id, RedirectAttributes attributes, Principal principal);


    @GetMapping("/detalhes/{id}")
    public String eventoDetalhes(@PathVariable("id")Long id, Model model);

    @GetMapping("/anunciante")
    public String anunciantePerfil(Model model);
}
