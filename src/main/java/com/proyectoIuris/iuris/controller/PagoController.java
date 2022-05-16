package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.ListaDeTareas;
import com.proyectoIuris.iuris.model.Pago;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IArchivoService;
import com.proyectoIuris.iuris.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pago")
public class PagoController {
    @Autowired
    private IPagoService pagoService;
    @Autowired
    private IArchivoService fileService;

    @GetMapping("/lista")
    public String getAll(Model model) {
        List<Pago> listaDePagos = pagoService.getAll();
        model.addAttribute("listaDePagos", listaDePagos);
        return "listadoPago";
    }

    @GetMapping("/agregar")
    public String getAgregarPago(Pago pago, Model model, HttpSession httpSession) {
        Usuario user = (Usuario) httpSession.getAttribute("user");
        //pago.setCantAbonada(user.getFullName());

        //Aca me colge, y me puse a pensar si no necesitamos que
        // cliente y pago o usuario esten conectado, bueno despues lo vemos.
       // porque necesitamos la cantidad abonada por el cliente y x caso
        model.addAttribute("pago", pago);
        model.addAttribute("user", user);
        return "agregarPago";
    }

    @GetMapping("/buscar")
    public String getPago() {
        return "testPago";
    }

    @GetMapping(value = "/editar/{idPago}")
    public String getEditarPago(@PathVariable("idPago") int idPago, Model model ) {
        Optional<Pago> pago = pagoService.findById(idPago);
        model.addAttribute("pago", pago);
        return "editarPago";
    }

    @GetMapping("/ver/{idPago}")
    public String getPagoByID(@PathVariable("idPago") int idPago, Model model) {
        if(pagoService.findById(idPago).isPresent()) {
            Optional<Pago> pago = pagoService.findById(idPago);
            model.addAttribute("pago", pago);
        } else {
            model.addAttribute("error", "No se encontró ningún pago realizado.");
        }
        return "vistaPago";
    }
    //POST

    @PostMapping("/alta")
    public String agregarPago(@RequestPart Pago pago, HttpSession session) {
        if (pagoService.save(pago)) {
            fileService.crearDir(System.getenv("APPDATA") +
                    "\\IURIS\\Archivos\\Pagos\\"
                    + pago.getIdPago());
        }
        return "altaPago";
    }

    @PostMapping("/baja")
    public void eliminarPago(@RequestParam("idPago") int idPago) {
        pagoService.delete(idPago);
    }

    @PostMapping("/editar")
    public String editarPago(@RequestPart Pago pago, Model model) {
        if (pagoService.save(pago)) {
            model.addAttribute("exito", true);
        } else {
            model.addAttribute("exito", false);
        }
        model.addAttribute("pago", pago);
        return "vistaPago";
    }
}
