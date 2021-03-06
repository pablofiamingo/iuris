package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.Interfaces.ICasoService;
import com.proyectoIuris.iuris.service.Interfaces.IPagoService;
import com.proyectoIuris.iuris.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/pago")
public class  PagoController {
    @Autowired
    private IPagoService pagoService;
    @Autowired
    private ICasoService casoService;

    @GetMapping("/lista/{caso}")
    public String getPagosFindIdCaso(Model model, HttpSession session, @PathVariable("caso") int caso) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        model.addAttribute("resultados", pagoService.findPagoByIdCaso(caso));
        model.addAttribute("caso", casoService.findCasoById(caso));
        return "resultadosPagos";
    }
    @GetMapping("/alta/{idCaso}")
    public String getAgregarPago(Pago pago, Model model, HttpSession session, @PathVariable("idCaso") int idCaso)  {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Usuario user = (Usuario) session.getAttribute("user");

        pago.setCaso(casoService.findCasoById(idCaso));
        model.addAttribute("pago", pago);
        model.addAttribute("user", user);
        return "agregarPago";
    }
    @GetMapping("/editar/{idPago}")
    public String getEditarPago(@PathVariable("idPago") String idPago, Model model, HttpSession session ) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Util.mostrarAlertas(model,session,"editarPago");

        Integer id = Integer.parseInt(idPago);

        model.addAttribute("pago", pagoService.findPagoById(id));
        return "editarPago";
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarPago(@Validated Pago pago, HttpSession session) {
        String estado = pagoService.save(pago) ? "exito" : "error";

        session.setAttribute("agregarPago", estado);
        return "redirect:/pago/alta/" + pago.getCaso().getIdCaso();
    }
    @PostMapping("/editar")
    public String editarPago(@Validated Pago pago, HttpSession session) {
        String estado = pagoService.save(pago) ? "exito":"error";
        session.setAttribute("editarPago", estado);

        return  "redirect:/pago/editar/" + pago.getIdPago();
    }
    @PostMapping("/baja")
    public String eliminarPago(@RequestParam("id") String idPago, @RequestParam("idcaso") String idCaso) {
        Integer id = Integer.parseInt(idPago);
        pagoService.delete(id);
        return "redirect:/pago/lista/" + idCaso;
    }
}
