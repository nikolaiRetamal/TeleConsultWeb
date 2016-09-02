package cnam.teleconsult.controller.controleur;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * Contrôleur par défaut
 * 
 * @author Jullien
 *
 */
@Controller
@Scope("session")
public class AccueilContr {
	

	/**
	 * 
	 * Contrôleur par défaut d'accueil de l'application
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView acceuil(){

		ModelAndView model = new ModelAndView("accueil");
		model.addObject("titrePage", "teleconsult");
				 
		return model;
	}
	

	
}
