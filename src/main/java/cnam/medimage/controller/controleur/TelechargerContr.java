package cnam.medimage.controller.controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cnam.medimage.bean.Dicom;
import cnam.medimage.repository.DicomRepository;

@Controller
public class TelechargerContr {

	@RequestMapping(value = "/telecharger", method = RequestMethod.GET)
	public void getFile(@RequestParam("id_dicom") String id_dicom, 
	    HttpServletResponse response, HttpServletRequest request) {
	    try {
	      // get your file as InputStream
	      DicomRepository dicoRepo = new DicomRepository();
	      Dicom dicom = dicoRepo.findOne(UUID.fromString(id_dicom));
	      File fichier = new File(request.getSession().getServletContext().getRealPath("/") +  
		  			"fichiers/" + dicom.getFile_path());
	      response.setHeader("Content-Disposition", "attachment; filename="+dicom.getNom());
	      FileInputStream fis = new FileInputStream(fichier);
	      // copy it to response's OutputStream
	      org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
	      response.flushBuffer();
	      fis.close();
	    } catch (IOException ex) {
	      throw new RuntimeException("IOError writing file to output stream");
	    }

	}
}
