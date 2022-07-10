package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.*;
import com.proyectoIuris.iuris.service.Interfaces.*;
import com.proyectoIuris.iuris.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/archivo")
public class ArchivoController {
    @Autowired
    private IArchivoService archivoService;
    @Autowired
    private ICasoService casoService;

    @GetMapping("/lista/{idUsuario}/{idCaso}")
    public String listarArchivosCaso(HttpSession session, Model model, @PathVariable("idCaso")int idCaso, @PathVariable("idUsuario")int idUsuario) {
        if (!Util.isLogged(session)) return "redirect:/usuario/login";
        Util.mostrarAlertas(model,session,"eliminarArchivo");
        List<Archivo> archivos = archivoService.list(idUsuario, idCaso);
        model.addAttribute("resultados", archivos);
        model.addAttribute("caso", casoService.findCasoById(idCaso));
        return "resultadosArchivos";

    }

    @GetMapping(value = "/ver/{id}")
    public ResponseEntity<InputStreamResource> verArchivo(@PathVariable("id") int id) throws FileNotFoundException {
        Archivo archivo = archivoService.findById(id);
        String archivoRuta = archivo.getRuta();
        String nombreArchivo = archivo.getNombre() ;
        File file = new File(archivoRuta);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + nombreArchivo);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.valueOf(archivo.getTipo())).body(resource);
    }

    @GetMapping(value = "/descargar/{id}")
    public ResponseEntity<InputStreamResource> descargarArchivo(@PathVariable("id") int id) throws FileNotFoundException {
        Archivo archivo = archivoService.findById(id);
        String rutaDeArchivo = archivo.getRuta();
        String archivoNombre = archivo.getNombre() ;
        File file = new File(rutaDeArchivo);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment;filename=" + archivoNombre);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.valueOf(archivo.getTipo())).body(resource);
    }

    //POSTMAPPING-------------------------------------------------------------------------------------------------------
    @PostMapping("/subir")
    public String subirArchivo(@RequestParam("file") MultipartFile file, @RequestParam("idCaso") int idCaso, HttpSession session){

        Usuario user = (Usuario) session.getAttribute("user");
        Caso caso = casoService.findCasoById(idCaso);
        String ruta = Util.RUTA_CARPETA_IURIS + "Archivos\\Usuario_" + user.getIdUsuario() + "\\Cliente_" + caso.getCliente().getIdCliente()+
                                                                            "\\Caso_" + caso.getIdCaso() + "\\";

        archivoService.crearDir(ruta);
        archivoService.uploadToLocal(file, ruta);

        Archivo archivo = new Archivo();
        archivo.setCaso(caso);
        archivo.setTipo(file.getContentType());
        archivo.setNombre(file.getOriginalFilename());
        archivo.setRuta(ruta+file.getOriginalFilename());
        archivoService.insert(archivo);
        return "redirect:/archivo/lista/" + caso.getCliente().getUsuario().getIdUsuario() + "/" + caso.getIdCaso();
    }

    @PostMapping("/eliminar")
    public String eliminarArchivo(@RequestParam("idArc") int id, HttpSession session) {
        Archivo archivo = archivoService.findById(id); //SE ELIMINA DE LA BD
        String resultado = archivoService.delete(id) ? "exito" : "error";
        if (resultado.equalsIgnoreCase("exito")) {
            File file = new File(archivo.getRuta());// SE ELIMINA EL ARCHIVO LOCAL
            resultado = file.delete() ? "exito" : "error";
        }
        session.setAttribute("eliminarArchivo", resultado);
        return "redirect:/archivo/lista/" + archivo.getCaso().getCliente().getUsuario().getIdUsuario() + "/" + archivo.getCaso().getIdCaso(); //REDIRIGE A LISTA
    }
}
