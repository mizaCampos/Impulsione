package com.impulsione.RestImpl;

import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import com.impulsione.config.ClienteDetails;
import com.impulsione.rest.RestEvento;
import com.impulsione.serviceImpl.ClienteServiceImpl;
import com.impulsione.serviceImpl.EventoServiceImpl;
import com.impulsione.utils.ExtractTimeExample;
import com.impulsione.utils.ImageUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Date;
import java.util.List;


@Controller
@Slf4j
public class RestEventoImpl implements RestEvento {

    @Autowired
    EventoServiceImpl eventoService;

    @Autowired
    private ImageUpload imageUpload;

    @Autowired
    ClienteDetails clienteDetails;

    @Autowired
    ClienteServiceImpl clienteService;

    @Autowired
    ExtractTimeExample extractTimeExample;

    @Override
    public String register(Model model, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Register");
        model.addAttribute("evento", new Evento());
        return "CriarEvento";
    }

    @Override
    public String addNewEvent(Evento evento, MultipartFile imageEvent, BindingResult result, Model model, Principal principal, RedirectAttributes attributes) {

        try {
            log.info("Entrou no AddNewEvent");
            String username = principal.getName();
            log.info(username);
            Long getId = clienteDetails.getId(username);
            eventoService.createEventoWithAuten(imageEvent, getId, evento);
            model.addAttribute("evento", new Evento());
            model.addAttribute("success", "Add successfully");

            return "index";

        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("Error", "Failed to add!");
        }
        return "redirect:/evento";

    }

    //getAllEvents
    @Override
    public String eventos(Model model, Principal principal) {
        List<Evento> eventoList = eventoService.findAll();
        List<Evento> eventoShowList = eventoService.findByCategory("Show");
        List<Evento> eventoPalestraList = eventoService.findByCategory("Palestra");
        List<Evento> eventoWorkshopList = eventoService.findByCategory("Workshop");
        List<Evento> eventoTeatroList = eventoService.findByCategory("Teatro");
        List<Evento> eventoExposicaoList = eventoService.findByCategory("Exposição");
        List<Evento> eventoEsportList = eventoService.findByCategory("Eventos de Esporte");
        model.addAttribute("eventos", eventoList);
        model.addAttribute("eventoShowList",eventoShowList);
        model.addAttribute("eventoPalestraList",eventoPalestraList);
        model.addAttribute("eventoWorkshopList",eventoWorkshopList);
        model.addAttribute("eventoTeatroList",eventoTeatroList);
        model.addAttribute("eventoExposicaoList",eventoExposicaoList);
        model.addAttribute("eventoEsportList",eventoEsportList);

        if(principal == null){
            model.addAttribute("imagem", imageUpload.converterParaBase64());
        }else {
            String username = principal.getName();
            Cliente cliente = clienteService.findByUsername(username);
            log.info(cliente.getUsername());
            model.addAttribute("imagem", cliente.getImage());
        }

        return "allevent2";
    }

    //Editar evento

    @Override
    public String updateEventoForm(Long id, Model model, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        EventoDTO eventoDTO = eventoService.getById(id);
        model.addAttribute("eventoDTO", eventoDTO);
        return "eventos/edit";
    }

    @Override
    public String processUpdate(Long id, EventoDTO eventoDTO, MultipartFile imageEvent, RedirectAttributes attributes) {
        try {
            eventoService.update(imageEvent, eventoDTO);
            attributes.addFlashAttribute("success", "Update Successfully");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update!");
        }
        return "redirect:/cliente/eventos";
    }

    @Override
    public String deleteEvento(Long id, RedirectAttributes attributes, Principal principal) {

        if(principal == null){
            return "redirect:/login";
        }
        try {
            eventoService.deleteById(id);
            attributes.addFlashAttribute("success", "Delete Successfully");
            return "redirect:/cliente/eventos";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/cliente/eventos";
    }


    @Override
    public String eventoDetalhes(Long id, Model model) {
        Evento evento = eventoService.findById(id);
        log.info(evento.getDate());
        String hora =  extractTimeExample.extrairHora(evento.getDate());
        String data =  extractTimeExample.extrairData(evento.getDate());

        model.addAttribute("enventoDTO", evento);
        model.addAttribute("imagem", evento.getImage());
        model.addAttribute("eventName", evento.getEventName());
        model.addAttribute("data", data);
        model.addAttribute("horario", hora);
        model.addAttribute("desc", evento.getTexto());
        model.addAttribute("endereco", evento.getEndereco());
        return "eventoDetalhes";
    }
}


