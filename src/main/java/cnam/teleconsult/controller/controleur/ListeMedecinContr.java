package cnam.teleconsult.controller.controleur;


import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class ListeMedecinContr {
	
	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	
	/**
	 * Affichage de la liste des médecins affectés à la structure de santé connectée
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/listemedecin", method = RequestMethod.GET)
	public ModelAndView ListeMedecin(HttpServletRequest request){

		ModelAndView model = new ModelAndView("listemedecin");
		
		Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
		
		//On rafraîchit l'objet en session
		hopital =  dmpcstructuresanteDAO.get(hopital.getStructuresanteId());
		
		List<Dmpcpersonnelsante> listmedecin = hopital.getListeMedecin();
		List<Dmpcpersonnelsante> listreferent = hopital.getListeReferent();
		
		model.addObject("title", "ListeMedecins");
		model.addObject("listmedecin", listmedecin);
		model.addObject("listreferent", listreferent);
		model.addObject("hopital", hopital);
		
		return model;
	}
	
	

	
}
