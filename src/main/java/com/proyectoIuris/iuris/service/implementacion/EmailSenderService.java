package com.proyectoIuris.iuris.service.implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email,
                          String tituloMail, //agregamos los parametros que tiene un mail
                          String cuerpo){
        SimpleMailMessage message = new SimpleMailMessage(); //instanciamos  la clase para del mail

        message.setFrom("iuris.sprt@gmail.com"); //aca va el mail de quien lo envia
        // (habria que crear un mail para iuris)
        message.setTo(email); //el mail de quien lo recibe
        message.setText(cuerpo); //cuerpo del mail
        message.setSubject(tituloMail); //y el titulo del mail

        mailSender.send(message); //se envia
    }
}
