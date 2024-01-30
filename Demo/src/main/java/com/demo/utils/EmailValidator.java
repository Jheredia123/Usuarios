package com.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    // Expresión regular para validar el formato de un correo electrónico
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Patrón compilado a partir de la expresión regular
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    /**
     * Método para validar el formato de un correo electrónico.
     *
     * @param email El correo electrónico a validar.
     * @return true si el formato es válido, false en caso contrario.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        String correo = "aaaaaaa@dominio.cl";
        if (isValidEmail(correo)) {
            System.out.println("El correo electrónico es válido.");
        } else {
            System.out.println("El correo electrónico no es válido.");
        }
    }
}
