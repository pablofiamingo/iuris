package com.proyectoIuris.iuris.controller;

import com.proyectoIuris.iuris.model.Archivo;
import com.proyectoIuris.iuris.model.Caso;
import com.proyectoIuris.iuris.model.Usuario;
import com.proyectoIuris.iuris.service.IArchivoService;
import com.proyectoIuris.iuris.service.ICasoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/archivo")
public class ArchivoController {

    @Autowired
    private IArchivoService fileService;
    @Autowired
    private ICasoService casoService;

    @GetMapping("/test")
    public String testFile() {
        return "testFile";
    }

    @GetMapping(value = "/ver/{id}")
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable("id") int id) throws FileNotFoundException {

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

    @PostMapping("/subir")
    public String fileHandler(@RequestParam("file") MultipartFile file,
                               HttpSession session){
        /*
        *
        * @RequestParam("file") MultipartFile file,
                                @RequestParam("idCaso") int idCaso,
                                    HttpSession session
        * */


        Usuario user = (Usuario) session.getAttribute("user");
        Caso caso = casoService.findCasoById(1);
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

    @PostMapping("delete")
    public void eliminarArchivo(@RequestParam("id") int id) {
        fileService.delete(id);
    }
}
