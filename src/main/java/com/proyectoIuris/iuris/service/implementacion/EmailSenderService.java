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
                          String tituloMail,
                          String cuerpo){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("matiasvarela076@gmail.com");
        message.setTo(email);
        message.setText(cuerpo);
        message.setSubject(tituloMail);

        mailSender.send(message);

        System.out.println("email enviado");

    }
}
