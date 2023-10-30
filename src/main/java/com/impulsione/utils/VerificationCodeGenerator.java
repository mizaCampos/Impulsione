package com.impulsione.utils;

import java.util.Random;

public class VerificationCodeGenerator {

    public static String generateVerificationCode() {
        // Tamanho do código de verificação (6 dígitos)
        int codeLength = 6;

        // Caracteres permitidos para o código (0-9)
        String allowedChars = "0123456789";

        // Usando a classe Random para gerar o código
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < codeLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

}
