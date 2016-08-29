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
import cnam.teleconsult.modele.bean.Consultation;
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

	@RequestMapping(value = "/consultations", method = RequestMethod.GET)
	public ModelAndView Consultation (@RequestParam ("id") int id){

		ModelAndView model = new ModelAndView("consultations");
		Dmpcstructuresante hopital = dmpcstructuresanteDAO.get(id);
		
		List<Consultation> consultations= consultationDAO.list();
		List<Dmpcpersonnelsante> listreferent = hopital.getListeReferent();
		
		model.addObject("title", "ListeConsultations");
		model.addObject("consultations", consultations);
		model.addObject("listreferent", listreferent);
		model.addObject("hopital", hopital);
		
		return model;
	}
	
	

	
}
