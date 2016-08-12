package cnam.teleconsult.controller.controleur;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class AccueilContr {
	

	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView acceuil(){

		ModelAndView model = new ModelAndView("accueil");
		model.addObject("titrePage", "teleconsult");
				 
		return model;
	}
	

	@RequestMapping(value="/connexion")
	public ModelAndView identification(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> param = new HashMap<>();
		param.put("title", "Connexion");
		param.put("titrePage", "Connexion");
		ModelAndView mv = new ModelAndView("connexion", param);
		return mv;
	}
	
}
