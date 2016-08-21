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
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class ListeMedecinContr {
	
	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	
	@RequestMapping(value = "/listemedecin", method = RequestMethod.GET)
	public ModelAndView ListeMedecin(){

		ModelAndView model = new ModelAndView("listemedecin");
		List<Dmpcpersonnelsante> medecin = dmpcpersonnelsanteDAO.list();
		model.addObject("title", "ListeMedecins");
		model.addObject("medecin", medecin);
		
		return model;
	}
	
	

	
}
