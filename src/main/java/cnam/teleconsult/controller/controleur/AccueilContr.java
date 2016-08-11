package cnam.teleconsult.controller.controleur;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class AccueilContr {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView helloWorld(){

		ModelAndView model = new ModelAndView("accueil");
		model.addObject("titrePage", "teleconsult");
				 
		return model;
	}
	

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(){

		ModelAndView model = new ModelAndView("accueil");
		model.addObject("titrePage", "teleconsult");
				 
		return model;
	}
	
}
