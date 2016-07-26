package cnam.medimage.controller.controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cnam.medimage.bean.AccueilForm;
import cnam.medimage.bean.Examen;
import cnam.medimage.bean.UsageExamen;
import cnam.medimage.bean.User;
import cnam.medimage.repository.ExamenRepository;
import cnam.medimage.repository.MetaDataExamenRepository;
import cnam.medimage.repository.TagExamenRepository;
import cnam.medimage.repository.UsageExamenRepository;
import cnam.medimage.repository.UserRepository;
import cnam.medimage.service.ServiceDicoDicomCrawler;

@Controller
@Scope("session")
public class RechercherContr {

	@RequestMapping(value = "/recherche", method = RequestMethod.POST)
	public ModelAndView effectuerRecherche(HttpServletRequest request, HttpSession session,
	HttpServletResponse response, @ModelAttribute AccueilForm form) throws IOException {
		
		AccueilForm monForm = form;
		System.out.println("usage : " + monForm.getUsage());
		//System.out.println("usageConnu: " + monForm.getUsageConnu() + " , " + UUID.fromString(monForm.getUsageConnu()));
		System.out.println("metas : " + monForm.getMetadatas());
		System.out.println("tags : " + monForm.getChosenTagsValue());
		/*Sets UUID d'id_examen*/
		Set<UUID> id_examens = new HashSet<>();		/*Set Final*/
		Set<UUID> id_exam_metas = new HashSet<>();	/*Set d'exams trouvés pour les metadonnées*/
		Set<UUID> id_exam_tags = new HashSet<>();	/*Set d'exams trouvés pour les tags*/
		Set<UUID> id_exam_usages = new HashSet<>();	/*Set d'exams trouvés pour les tags*/
		
		/*Recherche par usage*/
		if(!"".equals(monForm.getUsageConnu())){
			UsageExamenRepository usageExamRepo = new UsageExamenRepository();
			List<UsageExamen> usageExamens;
			usageExamens = usageExamRepo.findByUsage(UUID.fromString(monForm.getUsageConnu()));
			//usageExamens = usageExamRepo.findAll();
			System.out.println("exams trouvé : " +usageExamens.size());
			//Pour chaque usage associé à un exam on extrait l'id_examen
			for(UsageExamen usageExam : usageExamens){
				id_exam_usages.add(usageExam.getId_examen());
				System.out.println("exam trouvé : " + usageExam.getId_examen());
			}
			System.out.println("avec usages : "+ id_exam_usages.size());
		}
		
		/*recherche par metadonnes*/
		if(!"".equals(monForm.getMetadatas())){
	        Gson gson = new GsonBuilder().create();
	        List<metadataJSON> metaList = gson.fromJson(monForm.getMetadatas(), new TypeToken<List<metadataJSON>>(){}.getType());
	        MetaDataExamenRepository metaExamRepo = new MetaDataExamenRepository();
	        List<UUID> metasExams;
	        //Pour chaque métadonnée
	        for(metadataJSON meta : metaList){
	        	ServiceDicoDicomCrawler serviceDico = new ServiceDicoDicomCrawler();
	        	String keyMeta = serviceDico.formaterTag(meta.getKey());	
	        	String valueMeta = meta.getValue();
	        	System.out.println("keyMeta = " + keyMeta + ", valueMeta = " + valueMeta);
	        	//on trouve la liste de id_examen associés
	        	metasExams = metaExamRepo.getListeExamens(keyMeta, valueMeta);
	        	//qu'on stocke dans le set 
	        	for(UUID id_exam : metasExams){
	        		System.out.println("id_exam pour " + keyMeta + " : " + id_exam);
	        		id_exam_metas.add(id_exam);
	        	}
	        }
	        System.out.println("avec metas : "+ id_exam_metas.size());
		}
		
		/*recherche par Tags*/
		if(!"".equals(monForm.getChosenTagsValue())){
			TagExamenRepository tagExamRepo = new TagExamenRepository();
			List<UUID> tagsExams;
			String tag;
			//Pour chaque tag...
			for(String s:  monForm.getChosenTagsValue().split("\n")){
				tag = s.trim();
				tagsExams = tagExamRepo.getListeExamens(tag);
				for(UUID tagExam : tagsExams){
					System.out.println("exam_tag = " + tagExam);
					id_exam_tags.add(tagExam);
				}
			}
			System.out.println("avec tags : "+ id_exam_tags.size());
		}
		
		Set<UUID> triIntermediaire = new HashSet<>();
		
		//Intersection des id_exam_usages et id_exam_metas
		if(id_exam_usages.isEmpty() || id_exam_metas.isEmpty()){
			triIntermediaire = id_exam_usages.isEmpty() ? id_exam_metas : id_exam_usages;
		}else{
			if(id_exam_usages.size()>=id_exam_metas.size()){
				for(UUID id_exam_usage : id_exam_usages){
					for(UUID id_exam_meta : id_exam_metas){
						if(id_exam_usage.compareTo(id_exam_meta) == 0)
							triIntermediaire.add(id_exam_usage);
					}
				}
			}else{
				for(UUID id_exam_meta : id_exam_metas){
					for(UUID id_exam_usage : id_exam_usages){
						if(id_exam_usage.compareTo(id_exam_meta) == 0)
							triIntermediaire.add(id_exam_usage);
					}
				}
			}
		}
		System.out.println("apres premier tri : "+ triIntermediaire.size());
		//Intersection avec les tags
		if(triIntermediaire.isEmpty() || id_exam_tags.isEmpty()){
			id_examens = triIntermediaire.isEmpty() ? id_exam_tags : triIntermediaire;
		}else{
			if(triIntermediaire.size()>=id_exam_tags.size()){
				System.out.println("je suis ici");
				for(UUID id_exam_inter : triIntermediaire){
					for(UUID id_exam_tag : id_exam_tags ){
						System.out.println("id_exam_inter = " + id_exam_inter +  " <-> " + id_exam_tag);
						if(id_exam_inter.compareTo(id_exam_tag) == 0)
							id_examens.add(id_exam_inter);
					}
				}
			}else{
				for(UUID id_exam_tag : id_exam_tags){
					for(UUID id_exam_inter : triIntermediaire){
						if(id_exam_inter.compareTo(id_exam_tag) == 0)
							id_examens.add(id_exam_inter);
					}
				}
			}
		}
		System.out.println("apres deuxième tri : "+ id_examens.size());
		
		ExamenRepository examRepo = new ExamenRepository();
		List<Examen> examens= new ArrayList<>();
		for(UUID id_examen : id_examens){
			examens.add(examRepo.findOne(id_examen));
		}
		Map<String, Object> param = new HashMap<>();
		String id_user = "";
		User user;
		if(session.getAttribute("id_user") != null){
			id_user = session.getAttribute("id_user").toString();
			System.out.println("USER = " + id_user);
			UserRepository userRepo = new UserRepository();
			user = userRepo.findOne(UUID.fromString(id_user));
			param.put("user", user);
		}
		for(Examen exam : examens){
			UserRepository userRepo = new UserRepository();
			User userBean = userRepo.findOne(exam.getId_user());
			exam.setNom_user(userBean.getNom());
		}
		System.out.println("examens trouvés finaux : "+ examens.size());
		param.put("title", "Résultat");
		param.put("titrePage", "Résultat recherche");
		param.put("examens", examens);
		ModelAndView mv = new ModelAndView("resultatRecherche");
		mv.addAllObjects(param);
		return mv;
	}
	
	public class metadataJSON{
		private String key;
		private String value;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}		
	}
}
