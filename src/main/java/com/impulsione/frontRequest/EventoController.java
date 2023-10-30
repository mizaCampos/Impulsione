//package com.impulsione.frontRequest;
//
//import com.impulsione.POJO.Evento;
//import com.impulsione.RestImpl.RestEventoImpl;
//import com.impulsione.service.EventoService;
//import com.impulsione.serviceImpl.EventoServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//import java.security.Principal;
//
//@Controller
//@RequestMapping(path = "/front")
//public class EventoController {
//
//    @Autowired
//    private EventoServiceImpl eventoService;
//
//    @Autowired
//    private RestEventoImpl restEventoImpl;
//
//    @GetMapping("/registerEvent")
//    public String register(Model model) {
//        model.addAttribute("title", "Register");
//        model.addAttribute("evento", new Evento());
//        return "addEvento";
//    }
//
//    @PostMapping("/register-new")
//    public String addNewAdmin(@Valid @ModelAttribute("evento") Evento evento,
//                              BindingResult result,
//                              Model model, Principal principal){
//
//
//                restEventoImpl.newEvent(evento,principal);
//                System.out.println("success");
//                model.addAttribute("success", "Register successfully!");
//                model.addAttribute("evento", new Evento());
//
//        return "index";
//    }
//
////    @PostMapping("/register-new")
////    public String addNewAdmin(@Valid @ModelAttribute("evento") Evento evento,
////                              BindingResult result,
////                              Model model, Principal principal) {
////
////        eventoService.createEventoWithAuten(evento);
////        return "index";
////
////    }
//
//
//
//    //    @GetMapping("/eventos")
////    public String eventos(Model model, Principal principal) {
////        // Obtém o nome do usuário autenticado
////        String username = principal.getName();
////        log.info(username);
////        // Verifica se o usuário tem a função "admin" (ou outra função desejada)
////        boolean isAdmin = adminDetails.isUserAdmin(username);
////
////        // Faça algo com a variável isAdmin, por exemplo, para autorizar o acesso apenas para administradores
////        if (isAdmin) {
////            List<Evento> eventoList = eventoService.findAll();
////            model.addAttribute("eventos", eventoList);
////            model.addAttribute("size", eventoList.size());
////
////            return "eventos";
////        } else {
////            // Redirecione ou retorne uma mensagem de erro, dependendo do seu caso
////            return "redirect:/acesso-negado";
////        }
////    }
//
//}
//
