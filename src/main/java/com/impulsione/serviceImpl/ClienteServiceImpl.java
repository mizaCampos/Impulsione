package com.impulsione.serviceImpl;

import com.google.common.base.Strings;
import com.impulsione.DTO.ClienteDTO;
import com.impulsione.DTO.EventoDTO;
import com.impulsione.POJO.Cliente;
import com.impulsione.POJO.Evento;
import com.impulsione.repositories.ClienteRepository;
import com.impulsione.service.ClienteService;
import com.impulsione.utils.EmailUtils;
import com.impulsione.utils.ImageUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.impulsione.utils.VerificationCodeGenerator.generateVerificationCode;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ImageUpload imageUpload;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailUtils emailUtils;

    @Override
    public Cliente findById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }


    @Override
    public Cliente save(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setFirstName(clienteDTO.getFirstName());
        cliente.setLastName(clienteDTO.getLastName());
        cliente.setUsername(clienteDTO.getUsername());
        cliente.setTelephone(cliente.getTelephone());
        cliente.setAddress(cliente.getAddress());
        cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));
        cliente.setRole("ADMIN");

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente findByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }

    @Override
    public String forgotPassword(String email, Model model) {
        String verificationCode = generateVerificationCode();
        try{
            //extraindo os dados do user atraves do email
            Cliente cliente = clienteRepository.findByUsername(email);
            if(!Objects.isNull(cliente) && !Strings.isNullOrEmpty(cliente.getUsername())){
                log.info("Entrou no if do email");
                emailUtils.forgotMail(cliente.getUsername(), "Codigo de acesso",verificationCode);
                cliente.setVerificationCode(verificationCode);
                setarCodigoVerificacao(cliente);
                log.info("Enviou");
                model.addAttribute("message", "Check your mail for Credentials.");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
          return "validarToken";
//        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String findPasswordById(Long id) {
        Cliente cliente = findById(id);
        cliente.getPassword();
        log.info(cliente.getPassword());
        return cliente.getPassword();
    }

    public Cliente alterarSenha(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente setarCodigoVerificacao(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente findByVerificationCode(String code){
        return clienteRepository.findByVerificationCode(code);
    }
    @Override
    public Cliente salveNewName(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //metodo para adicionar imagem adicionado no dia 26/10

    @Override
    public Cliente addImage(MultipartFile imageCliente, Principal principal) {
        try {
            log.info("Entrou no add image de cliente");
            String username = principal.getName();
            Cliente cliente = clienteRepository.findByUsername(username);
            log.info(username);
            if (imageCliente == null){
                cliente.setImage(cliente.getImage());
            }else {
                if(imageUpload.checkExisted(imageCliente) == false){
                    System.out.println("Upload to folder");
                    imageUpload.uploadImageCliente(imageCliente);
                }
                cliente.setImage(Base64.getEncoder().encodeToString(imageCliente.getBytes()));
            }
            return clienteRepository.save(cliente);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
