package cnam.medimage.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.MetaDataDico;
import cnam.medimage.bean.Tag;
import cnam.medimage.repository.DicomRepository;
import cnam.medimage.repository.TagRepository;
import cnam.medimage.service.ServiceDicoDicomCrawler;
import cnam.medimage.service.ServiceMeshCrawler;

@org.springframework.stereotype.Controller
public class AideController implements Controller{


	@RequestMapping(value="/aide")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		
		System.out.println("Je suis dans le contrôleur de l'aide");
		
//		ServiceMeshCrawler serviceMeshCrawler = ServiceMeshCrawler.getInstance(request);
//		DicomRepository dicomRepo = new DicomRepository();
//		TagRepository tagRepo = new TagRepository();
//
//		List<Tag> tags = new ArrayList<Tag>();
//		ArrayList<Dicom> dicoms = new ArrayList<Dicom>();
//		ArrayList<String> listeTagHierarchie = serviceMeshCrawler.getNuageRechercheFromDescriptorUI("D007251");
//		
//		for(String s:listeTagHierarchie){
//			tags.addAll(tagRepo.findByNom(s));
//		}
//				
//		for(Dicom d:dicoms){
//			System.out.println(d.getNom());
//			System.out.println(d.getFile_path());
//			System.out.println(d.getNom_usage());
//		}
		
		ServiceDicoDicomCrawler serviceDicoDicomCrawler = ServiceDicoDicomCrawler.getInstance(request);
		
		ArrayList<MetaDataDico> liste = serviceDicoDicomCrawler.getDictionnaire();
				
		ModelAndView mav = new ModelAndView();
        mav.setViewName("aide");
        mav.addObject("message", "Bienvenue Nikolaï");
		return mav;
	}
}
