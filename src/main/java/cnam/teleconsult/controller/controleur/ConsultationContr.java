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

/**
 * 
 * Gestion des consultations 
 * Parcours des consultations affect�es/en attente
 * 
 * @author Luana/Jullien
 *
 */
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
	 * Renvoie � la vue de consultation des consultation pour l'utilisateur H�pital
	 * 
	 * - Dans le cas d'un h�pital on y affecte les r�f�rents
	 * - Dans le cas d'un r�f�rent on peut consulter ses consultations et ses demandes d'avis
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hopitalConsultations", method = RequestMethod.GET)
	public ModelAndView hopitalConsultations (HttpServletRequest request){

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
	 * Servlet de dispatch des r�f�rents sur les consultations
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
		
		//On r�cup�re la consultation � dispatcher
		Consultation consult = consultationDAO.get(new Integer(consultationChangement));
		
		//On r�cup�re le r�f�rent
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
	
	/**
	 * 
	 * Renvoie � la vue de consultation du m�decin avec toutes ses consultations (Historique)
	 * 
	 * - Dans le cas d'un h�pital on y affecte les r�f�rents
	 * - Dans le cas d'un r�f�rent on peut consulter ses consultations et ses demandes d'avis
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medecinConsultations")
	public ModelAndView medecinConsultations (HttpServletRequest request, @RequestParam String type){

		ModelAndView model = new ModelAndView("consultations");
		Dmpcpersonnelsante referent = (Dmpcpersonnelsante)request.getSession().getAttribute("referent");
		
		//On v�rifie s'il s'agit de remonter toutes les consultations ou seulement les "en cours"
		Boolean histo = "historique".equals(type);		
		
		List<Consultation> consultations= consultationDAO.getListeConsultationReferent(referent.getPersonnelsanteId()) ;
		model.addObject("consultations", consultations);
		
		List<Consultation> avis= consultationDAO.getListeAvisReferent(referent.getPersonnelsanteId()) ;
		model.addObject("avis", avis);
		
		return model;
	}

	
}
