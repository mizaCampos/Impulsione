package com.impulsione.frontRequest;

import antlr.Token;
import com.impulsione.DTO.ClienteDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.config.ClienteDetails;
import com.impulsione.service.ClienteService;
import com.impulsione.serviceImpl.ClienteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    ClienteDetails clienteDetails;

    @Autowired
    ClienteService clienteService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String Token;

    @GetMapping(path = "/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }


    @GetMapping(path = "/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping(path = "/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        try {
            clienteService.forgotPassword(email, model);
            return "redirect:/validarToken";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/validarToken";
    }

    //pagina para recebendo o token de atualizacao de senha
    @GetMapping(path = "/validarToken")
    public String obterTOken(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "validarToken";
    }


    //receber o token de atualizacao de senha
    @PostMapping(path = "/obterToken")
    public String forgotPassword(@RequestParam("token") String token, Model model, Principal principal) {
       Cliente cliente = clienteService.findByVerificationCode(token);
       if(token.equals(cliente.getVerificationCode())){
           System.out.println("Token Valido");

           Token = token;
           System.out.println("Valor do token: " + token);
           return "redirect:/alterarComToken";
       }else {
           System.out.println("Token Invalido");
           return "redirect:/validarToken";
       }
    }

    @GetMapping(path = "/alterarComToken")
    public String alterarSenhaComToken(Model model){
        return "alterarSenhaComToken";
    }

    @PostMapping(path = "/alterarComToken")
    public String alterarSenhaComToken(@RequestParam("newPassword")String newPassword, @RequestParam("repeatPassword")String repeatPassword,
                                        Model model){

        Cliente cliente = clienteService.findByVerificationCode(Token);
        log.info(cliente.getUsername());
        log.info("Acima cliente encontrado pelo token");
        try {
            if(cliente != null){
                if (newPassword.equals(repeatPassword)){

                    cliente.setPassword(passwordEncoder.encode(newPassword));
                    clienteService.alterarSenha(cliente);
                    System.out.println("Senha alterada com sucesso");
                    return "redirect:/alterarComToken";
                }else {
                    model.addAttribute("error", "As senhas não são iguais");
                    System.out.println("As senhas não são iguais");
                    return "redirect:/alterarComToken";
                }
            }else {
                model.addAttribute("error", "Email não encontrado");
                return "redirect:/alterarComToken";
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "redirect:/alterarComToken";

    }

    @RequestMapping(path = "/index")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            return "redirect:/login";
//        }

        return "index";
    }


    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("clienteDto", new ClienteDTO());
        return "register";
    }

    @PostMapping(path = "/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("clienteDto") ClienteDTO clienteDto,
                              BindingResult result,
                              Model model) {
        System.out.println("clienteDto password: " + clienteDto.getPassword());

        try {
            model.addAttribute("message", "");
            if (result.hasErrors()) {
                model.addAttribute("clienteDto", clienteDto);
                return "register";
            }
            String username = clienteDto.getUsername();
            Cliente cliente = clienteService.findByUsername(username);

            if (cliente != null) {
                model.addAttribute("clienteDto", clienteDto);
                System.out.println("clienteDto not null");
                model.addAttribute("emailError", "Your email has been registered");
                return "register";
            }
            if (clienteDto.getPassword().equals(clienteDto.getRepeatPassword())) {
                clienteService.save(clienteDto);
                System.out.println("success");
                model.addAttribute("success", "Register successfully!");
                model.addAttribute("clienteDto", clienteDto);

            } else {
                model.addAttribute("clienteDto", clienteDto);
                model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
                System.out.println("password not same");
                return "register";
            }

            return "register";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("errors", "The server has been wrong!");
        }

        return "register";
    }


}


