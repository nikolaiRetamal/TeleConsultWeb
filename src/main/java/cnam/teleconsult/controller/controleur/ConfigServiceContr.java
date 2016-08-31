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
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class ConfigServiceContr {
	

	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	
	
	/**
	 * Chargement du formulaire de création d'un service
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/configservice", method = RequestMethod.GET)
	public ModelAndView configservice (HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> param = new HashMap<>();
		Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
		
		param.put("title", "Creation");
		param.put("titrePage", "Créer un service");
		param.put("hopital", hopital);
		
		ModelAndView mv = new ModelAndView("configservice", param);
		return mv;
	}
	

	
	/**
	 * Validation du formulaire de création d'un service
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/controle_configuration", method = RequestMethod.POST)
	public ModelAndView controleConfigurationService (HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		Map<String, Object> param = new HashMap<>();
		Dmpcstructuresante hopital = dmpcstructuresanteDAO.get(id);
		
		param.put("title", "Accueil");
		param.put("titrePage", "TeleConsult");
		param.put("hopital", hopital);
		
		ModelAndView mv = new ModelAndView("accueil");
		
		return mv;
	}
	
	
	
}
