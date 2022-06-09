package com.proyectoIuris.iuris.service.implementacion;

import com.proyectoIuris.iuris.model.Archivo;
import com.proyectoIuris.iuris.repository.ArchivoRepository;
import com.proyectoIuris.iuris.service.Interfaces.IArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class CArchivoService implements IArchivoService {

    @Autowired
    private ArchivoRepository arcRepo;
    @Override
    public void uploadToLocal(MultipartFile file, String ruta) {
        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(ruta + file.getOriginalFilename());
            Files.write(path,data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Archivo> list(int idUser, int idCaso) {
        return arcRepo.list(idUser, idCaso);
    }

    @Override
    public Archivo findById(int id) {
        return arcRepo.findById(id);
    }

    public boolean crearDir(String directorioCaso) {
        boolean success = false;
        File path = new File(directorioCaso);
        System.out.println(path);
        if (!path.exists()) {
            success = path.mkdirs();
        }
        return success;
    }

    @Override
    public boolean insert(Archivo archivo) {
        if(archivo!=null) {
            arcRepo.save(archivo);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        if(arcRepo.existsById(id)) {
            arcRepo.delete(findById(id));
            return true;
        } else return false;
    }
}
