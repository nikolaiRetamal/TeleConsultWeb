package cnam.teleconsult.controller.controleur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

@Controller
@Scope("session")
public class ConnexionContr {
	

	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	
	
	/**
	 * 
	 * Chargement de l'interface de connexion
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/connexion")
	public ModelAndView identification(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> param = new HashMap<>();
		param.put("title", "Connexion");
		param.put("titrePage", "Connexion");
		ModelAndView mv = new ModelAndView("connexion", param);
		return mv;
	}

	
	/**
	 * 
	 * Servlet de destruction des éléments de connexion
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deconnexion")
	public ModelAndView deconnexion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//On drop les éléments de connexion en session

		request.getSession().setAttribute("referent", null);
		request.getSession().setAttribute("hopital", null);
		
		
		Map<String, Object> param = new HashMap<>();
		
		
		param.put("title", "Deconnexion");
		param.put("titrePage", "Deconnexion");
		ModelAndView mv = new ModelAndView("accueil", param);
		return mv;
	}
	


	/**
	 * 
	 * Servlet d'identification
	 * Le processus essaye de connecté un hopital avec les identifiants/motdepasse
	 * s'il échoue il teste avec un référent.
	 * 
	 * @param request
	 * @param response
	 * @param nom
	 * @param motDePasse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/controle_identification")
	public ModelAndView controleIdentification(HttpServletRequest request,
			HttpServletResponse response, @RequestParam String nom, @RequestParam String motDePasse) throws Exception {
			
		
		Map<String, Object> param = new HashMap<>();
		
		//
		Dmpcstructuresante hopital 	= null;
		Dmpcpersonnelsante referent = null;
		
		// On contrôle l'intégrité du formulaire
		if(nom == null || motDePasse == null
				|| "".equals(nom) || "".equals(motDePasse)){

			param.put("erreurIdent", "Les identifiant/mot de passe sont obligatoires.");
			
		}else{
			
			hopital = dmpcstructuresanteDAO.getByPassword(nom, motDePasse);
			
			/* Si l'hopital est null c'est peut-être un référent */
			if(hopital == null){
				
				referent = dmpcpersonnelsanteDAO.getByPassword(nom, motDePasse);
				

				/* Là c'est une erreur de connexion */
				if(referent == null){

					param.put("erreurIdent", "Les identifiant/mot de passe ne correspondent à aucun utilisateur.");

					/* On a trouvé un référent, on l'ajoute en session */
				}else{
					request.getSession().setAttribute("referent", referent);
				}
				
				
			/* On a trouvé un hôpital, on l'ajoute en session */
			}else{
				request.getSession().setAttribute("hopital", hopital);
			}
			
			
		}
		
	
		param.put("title", "Accueil");
		param.put("titrePage", "TeleConsult");

		ModelAndView mv = new ModelAndView("accueil");
		
		if(param.containsKey("erreurIdent")){

			mv = new ModelAndView("connexion");
		}
		
		mv.addAllObjects(param);
		return mv;
	}
	
}
