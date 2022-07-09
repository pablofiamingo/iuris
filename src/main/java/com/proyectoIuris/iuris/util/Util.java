package com.proyectoIuris.iuris.util;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Util {

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
