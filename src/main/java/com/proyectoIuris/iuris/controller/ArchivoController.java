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
    private IArchivoService fileService;
    @Autowired
    private ICasoService casoService;


    @GetMapping("/test")
    public String testFile(HttpSession session, Model model) {
        //ESTE MÉTODO ERA DE PRUEBA PERO LO VOY A DEJAR PORQUE VOY A NECESITAR HACER COPYPASTE DE ACA
        if (!Util.isLogged(session)) return "redirect:/usuario/login";

        Usuario usuario = (Usuario) session.getAttribute("user");
        List<Archivo> archivos = fileService.list(usuario.getIdUsuario());

        if(!archivos.isEmpty()) {
            model.addAttribute("archivos", archivos);
        } else {
            model.addAttribute("error","No hay archivos.");
        }

        return "testFile";
    }

    /**
     * Con este metodo, cuando se le pegue a la ruta /ver/{id} va a visualizar el archivo que tenga el id indicado. </br>
     *
     * @param id identificador del archivo. tipo int
     * @param session recibe la sesion actual para ... todavia no sé para qué.
     * @return vista del pdf en el navegador
     * @throws FileNotFoundException si no encuentra el archivo
     */
    @GetMapping(value = "/ver/{id}")
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable("id") int id, HttpSession session) throws FileNotFoundException {
        //tengo que restringir el acceso a pdf que no son propios del usuario.
        Archivo archivo = fileService.findById(id);
        String filePath = archivo.getRuta();
        String fileName = archivo.getNombre() ;
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" +fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    //POSTMAPPING-------------------------------------------------------------------------------------------------------

    /**
     * Con este método de tipo POST, el usuario va a poder cargar un archivo nuevo para el caso deseado. <br>
     * Recibe un objeto de tipo MultipartFile, el archivo en cuestión, junto con el id del caso.<br>
     * Con estos datos, se procede a crear un directorio para almacenar el archivo recibido por formulario, siguiendo el siguiente patrón: Archivos>Usuario>Cliente>Caso>Archivo <br>
     * @param file de tipo MultipartFile
     * @param idCaso el id del caso para almacenar el archivo
     * @param session de tipo HttpSession, obtiene la sesión actual
     * @return pendiente
     */
    @PostMapping("/subir")
    public String fileHandler(@RequestParam("file") MultipartFile file,
                              @RequestParam("idCaso") int idCaso,
                              HttpSession session){

        Usuario user = (Usuario) session.getAttribute("user");
        Caso caso = casoService.findCasoById(idCaso);
        String ruta = System.getenv("APPDATA") + "\\IURIS\\Archivos\\Usuario_" + user.getIdUsuario() +
                                                                            "\\Cliente_" + caso.getCliente().getIdCliente()+
                                                                            "\\Caso_" + caso.getIdCaso() + "\\";

        fileService.crearDir(ruta);
        fileService.uploadToLocal(file, ruta);

        Archivo archivo = new Archivo();
        archivo.setCaso(caso);
        archivo.setNombre(file.getOriginalFilename());
        archivo.setRuta(ruta+file.getOriginalFilename());
        fileService.insert(archivo);

        return "testFile";
    }

    /**
     *Método POST para eliminar el archivo deseado, requiriendo el parametro "id" para especificar que archivo es.
     * @param id tipo int, identificador del archivo
     */
    @PostMapping("/delete")
    public void eliminarArchivo(@RequestParam("id") int id) {
        fileService.delete(id);
    }
}
