package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.ICasoService;
import com.proyectoIuris.iuris.service.Interfaces.IClienteService;
import com.proyectoIuris.iuris.service.Interfaces.IPagoService;
import com.proyectoIuris.iuris.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pago")
public class  PagoController {
    @Autowired
    private IPagoService pagoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ICasoService casoService;

    @GetMapping("/lista/{caso}")
    public String getPagosFindIdCaso(Model model,
                                  HttpSession session,
                                  @PathVariable("caso") int caso) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        Caso caso1 = casoService.findCasoById(caso);
        List<Pago> pagos = pagoService.findPagoByIdCaso(caso);
        model.addAttribute("resultados", pagos);
        model.addAttribute("caso", caso1);
        return "resultadosPagos";
    }

    @GetMapping("/alta/{idCaso}")
    public String getAgregarPago(Pago pago, Model model, HttpSession session,
                                 @PathVariable("idCaso") int idCaso)  {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");
        Caso caso = casoService.findCasoById(idCaso);
        pago.setCaso(caso);
        model.addAttribute("pago", pago);
        model.addAttribute("user", user);
        return "agregarPago";
    }

    @GetMapping("/editar/{idPago}")
    public String getEditarPago(@PathVariable("idPago") String idPago,
                                Model model,
                                HttpSession session ) {
        Integer id = Integer.parseInt(idPago);
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        String redirected = (String) session.getAttribute("editarPago");
        if(redirected!=null) {
            model.addAttribute(redirected,true);
            session.removeAttribute("editarPago");
        }
        Pago pago = pagoService.findPagoById(id);
        model.addAttribute("pago", pago);
        return "editarPago"; //agregar html
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarPago(@Validated Pago pago,
                              HttpSession session) {

        if (pago != null) {
            if (pagoService.save(pago)) {
                session.setAttribute("agregarPago", "exito");
            } else {
                session.setAttribute("agregarPago", "error");
            }
        } else {
            session.setAttribute("agregarPago", "error");
        }
        return "redirect:/pago/alta/" + pago.getCaso().getIdCaso();
    }

    @PostMapping("/editar")
    public String editarPago(@Validated Pago pago, Model model, HttpSession session) {
        if (pago!=null) {
            pagoService.save(pago);
            session.setAttribute("editarPago", "exito");
        } else {
            model.addAttribute("editarPago", "error");
        }
        return  "redirect:/pago/editar/" + pago.getIdPago();
    }
    @PostMapping("/baja")
    public String eliminarPago(@RequestParam("id") String idPago,
                               @RequestParam("idcaso") String idCaso,
                               Model model) {
        Integer id = Integer.parseInt(idPago);
        pagoService.delete(id);
        return "redirect:/pago/lista/" + idCaso;
    }
}
