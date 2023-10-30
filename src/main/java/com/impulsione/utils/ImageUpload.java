package com.impulsione.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

@Component
public class ImageUpload {

    private final String UPLOAD_FOLDER = "/home/teltools/Documents/ImpulsioneSystem/src/main/resources/static/img/eventos";
    private final String UPLOAD_FOLDER_CLIENTE = "/home/teltools/Documents/ImpulsioneSystem/src/main/resources/static/img/clientes";

    public boolean uploadImage(MultipartFile imageProduct){
        boolean isUpload = false;
        try {
            Files.copy(imageProduct.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }




    public boolean checkExisted(MultipartFile imageEvent){
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER + "/" + imageEvent.getOriginalFilename());
            isExisted = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }

//    configs para imagems do cliente

    public boolean uploadImageCliente(MultipartFile imageCliente){
        boolean isUpload = false;
        try {
            Files.copy(imageCliente.getInputStream(),
                    Paths.get(UPLOAD_FOLDER_CLIENTE + File.separator, imageCliente.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }


    public boolean checkExistedImageCliente(MultipartFile imageCliente){
        boolean isExisted = false;
        try {
            File file = new File(UPLOAD_FOLDER_CLIENTE + "/" + imageCliente.getOriginalFilename());
            isExisted = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }

    public String converterParaBase64(){
        String filePath = "/home/teltools/Documents/ImpulsioneSystem/src/main/resources/static/img/eventos/imgperfil.jpg";// Substitua pelo caminho do seu arquivo de imagem

        try {
            // Lê o conteúdo do arquivo em bytes
            byte[] fileContent = Files.readAllBytes(Path.of(filePath));

            // Codifica os bytes em Base64
            String base64Image = Base64.getEncoder().encodeToString(fileContent);
            return base64Image;

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trate o erro apropriadamente ou retorne um valor padrão
        }
    }



}
