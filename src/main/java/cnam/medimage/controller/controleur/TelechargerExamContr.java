package cnam.medimage.controller.controleur;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cnam.medimage.bean.Dicom;
import cnam.medimage.repository.ExamenDicomRepository;

@Controller
public class TelechargerExamContr {

	private HashMap<String,String> listeFichiers;
	private String pathFichierSortie;
	private String nomFichierSortie;
	private String dossier;
	private String realPath;
	
	TelechargerExamContr(){
		listeFichiers = new HashMap<String, String>();
    }
	
	@RequestMapping(value = "/telechargerExamen", method = RequestMethod.GET)
	public void getFile(@RequestParam("id_examen") String id_examen, 
	HttpServletResponse response, HttpServletRequest request) {
		try {
			//on Trouve la liste de dicoms pour l'examen
	        ExamenDicomRepository examDicomRepo = new ExamenDicomRepository();
	        List<Dicom> dicoms = examDicomRepo.getListeDicoms(UUID.fromString(id_examen));
	        System.out.println("dicom path = " + dicoms.get(0).getFile_path());
	        realPath = request.getSession().getServletContext().getRealPath("/") +  "fichiers";
	        dossier = dicoms.get(0).getFile_path();
	        dossier = dossier.replace("/"+dicoms.get(0).getNom(), "");
	        System.out.println("dir path = " + dossier);
	        nomFichierSortie =  dicoms.get(0).getNom_examen() + ".zip";
	        System.out.println("nomFichierSortie = " + nomFichierSortie);
	        pathFichierSortie = realPath + dossier + "/" + nomFichierSortie;
	        pathFichierSortie = pathFichierSortie.replace("/", "\\");
	        System.out.println("pathFichierSortie = " + pathFichierSortie);
	        for(Dicom dicom : dicoms){
	        	
	        	String pathFichierDicom = realPath + dicom.getFile_path();
	        	pathFichierDicom = pathFichierDicom.replace("/", "\\");
	        	listeFichiers.put(dicom.getNom(), pathFichierDicom);
	        	System.out.println("pathFichierDicom = " + pathFichierDicom);
	        }
	        this.compresser(pathFichierSortie);
	        System.out.println("Je vais ouvrir le fichier " + nomFichierSortie);
			File fichier = new File(pathFichierSortie);
			response.setHeader("Content-Disposition", "attachment; filename=" + nomFichierSortie);
			FileInputStream fis = new FileInputStream(fichier);
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
			fis.close();
//			if(fichier.delete()){
//    			System.out.println(fichier.getName() + " a été effacé");
//    		}else{
//    			System.out.println("echec de la suppression de " + nomFichierSortie);
//    		}
		} catch (Exception ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
	
	 /**
     * This method zips the directory
     * @param dir
     * @param zipDirName
     */
    private void compresser(String zipName) {
        try {
            //now zip files one by one
            //create ZipOutputStream to write to the zip file
            FileOutputStream fos = new FileOutputStream(zipName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for(String fileName : listeFichiers.keySet()){
                System.out.println("Zipping "+fileName);
                //for ZipEntry we need to keep only relative file path, so we used substring on absolute path
                ZipEntry ze = new ZipEntry(fileName);
                zos.putNextEntry(ze);
                //read the file and write to ZipOutputStream
                FileInputStream fis = new FileInputStream(listeFichiers.get(fileName));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
}
