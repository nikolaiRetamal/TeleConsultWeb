package cnam.medimage.controller.controleur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.Examen;
import cnam.medimage.bean.ImportForm;
import cnam.medimage.bean.Usage;
import cnam.medimage.bean.User;
import cnam.medimage.repository.DicomRepository;
import cnam.medimage.repository.ExamenRepository;
import cnam.medimage.repository.UsageRepository;
import cnam.medimage.repository.UserRepository;

@Controller
@Scope("session")
public class AccueilImportContr {
	private Examen examen;
	private String dest_Path;
	private String dir_name;
	private Usage usage;
	private User user;
	private String current_filename;
	private ImportForm importForm;
	private List<String> tags;

	@RequestMapping(value="/import")
	public ModelAndView handleRequest(HttpServletRequest request, HttpSession session,
			HttpServletResponse response) throws Exception {
		System.out.println("Je suis dans le contrôleur de l'import");
		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		String id_user = "";
		User user;
		ImportForm form = new ImportForm();
		if(session.getAttribute("id_user") != null){
			id_user = session.getAttribute("id_user").toString();
			System.out.println("USER = " + id_user);
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(id_user));
			form.setUser(user);
		}
		ModelAndView mv = new ModelAndView("accueilImport");
		mv.addObject("form", form);
		return mv;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody
	String upload(MultipartHttpServletRequest request, HttpSession session,
	HttpServletResponse response, @ModelAttribute ImportForm form) throws IOException {
		//récupération de l'user
		if(session.getAttribute("id_user") !=null){
			System.out.println("USER = " + session.getAttribute("id_user").toString());
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(session.getAttribute("id_user").toString()));
		}
		importForm = form;
		this.examen = new Examen();
		this.usage = new Usage();
		this.usage.setDate_creat(new Date());
		if(!"".equals(importForm.getUsageConnu()))
			this.usage.setId_usage(UUID.fromString(importForm.getUsageConnu()));
		else
			this.usage.setId_usage(UUID.randomUUID());
		this.usage.setNom(importForm.getNom_usage());
		//on enregistre le contexte pour enregistrer plus loin les fichiers
		dest_Path = request.getSession().getServletContext().getRealPath("/") +  "fichiers/";
		//on récupère les fichiers soumis pour les enregistrer
		Map<String, MultipartFile> fileMap = request.getFileMap();
		System.out.println("imagePublique : " + importForm.getPublique());
		this.examen.setId_examen(UUID.randomUUID());
		this.examen.setId_user(this.user.getId_user());
		this.examen.setDate_import(new Date());
		System.out.println("date = " + this.examen.getDate_import());
		this.examen.setNom_examen(importForm.getNom_examen());
		this.examen.setNom_usage(importForm.getNom_usage());
		this.examen.setTags(new ArrayList<String>());
		for(String s:  request.getParameter("chosenTagsValue").split("\n")){
			this.examen.getTags().add(s.trim());
		}
		this.tags = new ArrayList<>();
		for(String s:  request.getParameter("chosenTagsValueForDicom").split("\n")){
			this.tags.add(s.trim());
		}
		if(fileMap.size() > 1){
			this.dir_name = user.getNom() + "_" + importForm.getNom_usage().replace(" ", "-") + "_" + importForm.getNom_examen().replace(" ", "-");
			boolean success = (new File(this.dest_Path + this.dir_name)).mkdirs();
			if (!success) {
				System.out.println("Erreur création dossier");
				//A coder Exception   
			}
			this.dir_name = "/" + this.dir_name + "/";
		}else
			this.dir_name = "/" + user.getNom() + "_" + importForm.getNom_usage().replace(" ", "-") + "_" + importForm.getNom_examen().replace(" ", "-") + "_";
		
		boolean estPremierFichier = true;
		for (MultipartFile multipartFile : fileMap.values()) {
			
			// Sauvegarde physique
			this.current_filename = multipartFile.getOriginalFilename();
			sauvegardeFichier(multipartFile);
			
			// Sauvegarde en base
			sauvegardeEnBase(estPremierFichier);
			
			estPremierFichier = false;
		}
		//sauvegarde en base de l'examen
		ExamenRepository examRepo = new ExamenRepository();
		examRepo.save(this.examen, this.usage.getId_usage());
		
		//sauvegarde en base de l'usage
		if("".equals(importForm.getUsageConnu())){
			UsageRepository usageRepo = new UsageRepository();
			usageRepo.save(this.usage);
		}
		
		return "{\"id_examen\" : \""+ this.examen.getId_examen().toString()+"\"}";
	}
	
	/*Sauvegarde physique d'un fichier dicom sur le serveur*/
	private void sauvegardeFichier(MultipartFile multipartFile)
	throws IOException, FileNotFoundException {
		FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(getOutputFilename()));
	}
	
	/*Sauvevarge en base de données des données d'un fichier*/
	private void sauvegardeEnBase(boolean estPremierFichier) {
		DicomInputStream dicomInput = null;
		File fichierDicom = new File(getOutputFilename());
		DicomRepository dicoRepo = new DicomRepository();		
		try {
		    dicomInput = new DicomInputStream(fichierDicom);
		    Dicom dicom = new Dicom();
		    //Données de base
		    dicom.setId_dicom(UUID.randomUUID());
		    dicom.setDate_import(new Date());
		    dicom.setFile_path(this.dir_name + this.current_filename);
		    dicom.setId_user(this.user.getId_user());
		    dicom.setNom(this.current_filename);
		    dicom.setPublique(importForm.getPublique());
		    dicom.setId_examen(this.examen.getId_examen());
		    dicom.setNom_examen(this.examen.getNom_examen());
		    dicom.setNom_usage(this.examen.getNom_usage());
		    dicom.setId_usage(this.usage.getId_usage());
		    dicom.setTagsId(this.examen.getTags());
		    dicom.setTags(this.tags);
		    //récupération des métadonnées
		    listMetaInfo(dicomInput.readFileMetaInformation(), dicom);
		    listHeader(dicomInput.readDicomObject(), dicom);
		    dicoRepo.save(dicom);
		    this.usage.getDicoms().add(dicom.getId_dicom());
		    //Si c'est le premier DICOM de l'examen, alors on enregistre
		    //les métadonnées du DICOM pour l'examen
		    if(estPremierFichier){
		    	this.examen.setMetadatas(dicom.getMetadatas());
		    	this.examen.setMetadataIds(dicom.getMetadataIds());
		    	this.examen.setTagsId(dicom.getTagsId());
		    }
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
		finally {
		    try {
		    	dicomInput.close();
		    }
		    catch (IOException ignore) {
		    }
		}
	}
	
	/*Extrait les métadonnées du fichier dicom (DicomObject) 
	 * et les intègre au Bean Dicom en paramètre*/
	public void listMetaInfo(DicomObject object, Dicom dicom) {
	   Iterator<DicomElement> iter = object.fileMetaInfoIterator();
	   while(iter.hasNext()) {
	      DicomElement element = (DicomElement) iter.next();
	      int tag = element.tag();
	      try {
	    	 String tagName = object.nameOf(tag);
	         String tagAddr = TagUtils.toString(tag);
	         String tagVR = object.vrOf(tag).toString();
	         if (tagVR.equals("SQ")) {
	            if (element.hasItems()) {
	               listMetaInfo(element.getDicomObject(), dicom);
	               continue;
	            }
	         }    
	         String tagValue = object.getString(tag);
			dicom.getMetadatas().put(tagName, tagValue);
			dicom.getMetadataIds().put(tagAddr, tagValue);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }  
	}
	
	/*Extrait les métadonnées d'en tête du fichier dicom (DicomObject) 
	 * et les intègre au Bean Dicom en paramètre*/
	public void listHeader(DicomObject object, Dicom dicom) {
	   Iterator<DicomElement> iter = object.datasetIterator();
	   while(iter.hasNext()) {
	      DicomElement element = (DicomElement) iter.next();
	      int tag = element.tag();
	      //Si on a terminé de lire l'en tête en qu'on
	      //passe à la partie binaire du fichier
	      if(tag == Tag.PixelData)
	    	  break;
	      try {
			String tagName = object.nameOf(tag);
			String tagAddr = TagUtils.toString(tag);
			String tagVR = object.vrOf(tag).toString();
			if (tagVR.equals("SQ")) {
			   if (element.hasItems()) {
			      listHeader(element.getDicomObject(), dicom);
			      continue;
			   }
			}       	 
			String tagValue = object.getString(tag);
			dicom.getMetadatas().put(tagName, tagValue);
			dicom.getMetadataIds().put(tagAddr, tagValue);
	      } catch (Exception e) {
			e.printStackTrace();
	      }
	   }  
	}
	
	/*Retourne l'adresse physique du fichier courant sur le serveur*/
	private String getOutputFilename() {
		return this.dest_Path + this.dir_name + this.current_filename;
	}
}
