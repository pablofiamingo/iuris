package com.proyectoIuris.iuris.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Util {

    //String RUTA_CARPETA_IURIS = "C:\\Users\\mati0\\OneDrive\\Escritorio\\IURIS2\\"; //aca va a ir el nombre del dispositivo para la carpeta compartida

    String RUTA_CARPETA_IURIS = "\\\\NBVARELA21\\prueba\\"; //aca va a ir el nombre del dispositivo para la carpeta compartida
    static boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@*+%{}!$&/:;¬°()=?¡´¨<`^>\\[\\]|\"]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    static boolean isLogged(HttpSession session) {
        return (session.getAttribute("user") != null);
    }

    static void mostrarAlertas(Model model, HttpSession session, String template) {
        String redirected = (String) session.getAttribute(template);
        if(redirected!=null) {
            model.addAttribute(redirected,true);
            session.removeAttribute(template);
        }
    }
}
