package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.*;
import com.proyectoIuris.iuris.service.Interfaces.*;
import com.proyectoIuris.iuris.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/caso")
public class CasoController {
    @Autowired
    private ICasoService casoService;
    @Autowired
    private IArchivoService archivoService;
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/lista")
    public String getCasos(Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login"; //si no hay usuario logueado redirecciona al login
        Usuario user = (Usuario) session.getAttribute("user"); //obtiene el user de la sesion
        Util.mostrarAlertas(model,session,"eliminarCaso"); //muestra alerta de exito o error si existe alguna
        List<Caso> casos;
        if(user.getRol().equalsIgnoreCase("abogado")) {
            casos = casoService.listPermisoAbogado(user.getIdUsuario()); //si se logueo un abogado se listaran solo sus casos
        } else {
            casos = casoService.list(); //si es empleado o admin vera todos los casos
        }
        model.addAttribute("resultados", casos); //finalmente los casos, de haber, se envian al template
        return "resultadosCaso";
    }

    @GetMapping("/lista/{cliente}")
    public String getCasosCliente(Model model, HttpSession session, @PathVariable("cliente") int idCliente) {
        //se listan los casos unicamente del cliente que se especificó por la variable de ruta
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        model.addAttribute("resultados", casoService.findCasoByIdCliente(idCliente));
        return "resultadosCaso";
    }

    @GetMapping("/alta")
    public String getAgregarCaso(Caso caso, Model model, HttpSession session) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login"; //chequeo si hay usuario logueado
        Usuario user = (Usuario) session.getAttribute("user"); //obtengo el usuario logueado desde la sesion
        Util.mostrarAlertas(model,session,"agregarCaso"); //muestro alertas si hay
        List<Cliente> clientes;
        boolean abogado = false;

        if(user.getRol().toLowerCase().equals("abogado")) {
            clientes = clienteService.listPermisoAbogado(user.getIdUsuario()); //si el usuario es abogado listo sus clientes
            caso.setRepresentante(user.getFullName()); //seteo el representante en el caso para evitar errores en el template y omitir un paso obvio
            abogado = true;
        } else {
            clientes = clienteService.list(); //si no es abogado listo todos los clientes
            List<Usuario> usuarios = usuarioService.list(); //también traigo una lista de todos los usuarios
            usuarios.removeIf(u -> !u.getRol().equals("abogado")); //elimino de la lista todos los que no sean abogados
            model.addAttribute("usuarios",usuarios); //agrego la lista de usuarios
        }
        //agrego los objetos que va a requerir el template para trabajar
        model.addAttribute("abogado",abogado);
        model.addAttribute("caso", caso);
        model.addAttribute("user", user);
        model.addAttribute("clientes", clientes);
        return "agregarCaso";
    }

    @GetMapping("/editar/{idCaso}")
    public String getEditarCaso(@PathVariable("idCaso") int idCaso, Model model, HttpSession session ) {

        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Util.mostrarAlertas(model, session, "editarCaso");

        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        Caso caso = casoService.findCasoById(idCaso);

        List<Cliente> clientes;
        boolean abogado = false;

        if(!usuarioActivo.getRol().equals("abogado")) {
            clientes = clienteService.list(); //mismo procedimiento que en alta
            List<Usuario> usuarios = usuarioService.list();
            usuarios.removeIf(u -> !u.getRol().equals("abogado"));
            model.addAttribute("usuarios",usuarios);
        } else {
            abogado = true;
            clientes = clienteService.listPermisoAbogado(usuarioActivo.getIdUsuario());
        }

        model.addAttribute("abogado",abogado);
        model.addAttribute("clientes", clientes);
        model.addAttribute("caso", caso);
        return "editarCaso";
    }

    //POSTMAPPING-----------------------------------------------------------------------------------------------------
    @PostMapping("/agregar")
    public String agregarCaso(@Validated Caso caso, @RequestParam("cliente")int id , @RequestParam(value = "repr", required = false, defaultValue = "0")int repr, HttpSession session) {
        /*
        * Trae el objeto caso desde el template, ya con los datos para agregarlo a la bd y termina de agregar los datos faltantes, como el cliente, que primero busca
        * con el id proporcionado, y en caso de que el que este agregando no sea abogado, busca en la bd el representante con la id proporcionada en "repr" y lo setea al caso.
        * Luego el mismo se verifica que el representante corresponda al cliente elegido, de ser asi se guarda y se redirecciona al controller bajo la ruta /caso/alta
        * Estado es para alertar del exito o el error
        * */
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");
        String estado;
        Cliente cli = clienteService.findById(id);

        if(repr != 0) caso.setRepresentante(usuarioService.findByIdUsuario(repr).getFullName());

        if ( ( cli.getUsuario().getIdUsuario() == repr ) || (cli.getUsuario().getIdUsuario() == usuarioActivo.getIdUsuario()) ) {
            caso.setCliente(clienteService.findById(id));
            estado = casoService.save(caso) ? "exito" : "error";
        } else {
            estado = "error";
        }

        session.setAttribute("agregarCaso", estado);
        return "redirect:/caso/alta";
    }

    @PostMapping("/editar")
    public String editarCaso(@Validated Caso caso, HttpSession session, @RequestParam(value = "repr", required = false, defaultValue = "0")int repr, @RequestParam("cliente")int id) {
        Cliente cliente = clienteService.findById(id); //busca el cliente segun el id proporcionado
        String estado, redir = "redirect:/caso/editar/" + caso.getIdCaso();
        Usuario usuarioActivo = (Usuario) session.getAttribute("user");

        if ((cliente.getUsuario().getIdUsuario() == repr) || (cliente.getUsuario().getIdUsuario() == usuarioActivo.getIdUsuario())) {
            //si el id del usuario (el abogado) del cliente es igual al id proporcionado por parametro. es decir, si el cliente es de hecho un cliente del abogado
            //el mismo va a poder ser agregado en el caso
            caso.setCliente(cliente);
        } else {
            //de no darse lo anterior, agrega la alerta de error y redirecciona a /caso/editar/"caso"
            session.setAttribute("editarCaso","error");
            return redir;
        }
        if (repr != 0) caso.setRepresentante(usuarioService.findByIdUsuario(repr).getFullName()); //si el id de representante es diferente a 0 (valor default) se agrega al mismo como nuevo representante del caso.
        estado = casoService.save(caso) ? "exito" : "error"; //luego se guarda el caso y se devuelve ya sea exito por true o error por false.
        session.setAttribute("editarCaso",estado);
        return redir;
    }

    @PostMapping("/baja")
    public String eliminarCaso(@RequestParam("id") int idCaso, HttpSession session) {
        Caso caso  = casoService.findCasoById(idCaso);
        List<Archivo> archivosDelCaso = archivoService.list(caso.getCliente().getUsuario().getIdUsuario(), idCaso);

        casoService.delete(idCaso);//   elimina el caso
        //elimina los archivos locales del caso
        if(!archivosDelCaso.isEmpty()) {
            for ( Archivo arc : archivosDelCaso) {
                File file = new File(arc.getRuta());// SE ELIMINA EL ARCHIVO LOCAL
                String resultado = file.delete() ? "exito" : "error";
                if(resultado.equalsIgnoreCase("error")) {
                    session.setAttribute("eliminarCaso", "error");
                    return "redirect:/caso/lista";
                }
            }
        }

        session.setAttribute("eliminarCaso", "exito"); //guarda la alerta de exito
        return "redirect:/caso/lista"; //se redirecciona a la vista de casos
    }
}
