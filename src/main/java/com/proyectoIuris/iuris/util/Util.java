package com.proyectoIuris.iuris.util;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Util {

    public static boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[~#@*+%{}!$&/:;¬°()=?¡´¨<`^>\\[\\]|\"]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    public static boolean isLogged(HttpSession session) {
        return (session.getAttribute("user") != null);
    }

}
