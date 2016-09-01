package cnam.teleconsult.controller.controleur;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Consultation;
import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.dao.ConsultationDAO;
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class ConsultationContr {
	
	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	@Autowired
	private ConsultationDAO consultationDAO;

	/**
	 * 
	 * Renvoie à la vue de consultation des consultation
	 * 
	 * - Dans le cas d'un hôpital on y affecte les référents
	 * - Dans le cas d'un référent on peut consulter ses consultations et ses demandes d'avis
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/consultations", method = RequestMethod.GET)
	public ModelAndView consultation (HttpServletRequest request){

		ModelAndView model = new ModelAndView("consultations");
		Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
		
		List<Consultation> consultations= consultationDAO.getListeConsultationHopital(hopital.getStructuresanteId()) ;
		List<Dmpcpersonnelsante> listreferent = hopital.getListeReferent();
				
		model.addObject("title", "ListeConsultations");
		model.addObject("consultations", consultations);
		model.addObject("listreferent", listreferent);
		model.addObject("hopital", hopital);
		
		return model;
	}
	
	/**
	 * 
	 * Servlet de dispatch des référents sur les consultations
	 * 
	 * @param request
	 * @param referentChangement
	 * @param consultationChangement
	 * @return
	 */
	@RequestMapping(value = "/hopitalModifieConsultation")
	public ModelAndView hopitalModifieConsultation (HttpServletRequest request, @RequestParam String referentChangement, @RequestParam String consultationChangement){

		ModelAndView model = new ModelAndView("consultations");
		Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
		
		//On récupère la consultation à dispatcher
		Consultation consult = consultationDAO.get(new Integer(consultationChangement));
		
		//On récupère le référent
		Dmpcpersonnelsante referent = dmpcpersonnelsanteDAO.get(new Integer(referentChangement));
		
		consult.setDmpcpersonnelsante(referent);
		consultationDAO.saveOrUpdate(consult);
		
		
		List<Consultation> consultations= consultationDAO.getListeConsultationHopital(hopital.getStructuresanteId()) ;
		List<Dmpcpersonnelsante> listreferent = hopital.getListeReferent();
				
		model.addObject("title", "ListeConsultations");
		model.addObject("consultations", consultations);
		model.addObject("listreferent", listreferent);
		model.addObject("hopital", hopital);
		
		return model;
	}
	
	

	
}
