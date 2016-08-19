package cnam.teleconsult.controller.controleur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Specialite;
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
	 * Chargement du formulaire de cr�ation d'un service
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/configservice", method = RequestMethod.GET)
	public ModelAndView configservice (HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> param = new HashMap<>();
		param.put("title", "Creation");
		param.put("titrePage", "Cr�er un service");
		
		//Liste des specialit�s
		List<Specialite> specialite = new ArrayList<Specialite>();
		// specialite = via DAO ; ?
		param.put("specialite", specialite);
		
		
		ModelAndView mv = new ModelAndView("configservice", param);
		return mv;
	}
	

	/**
	 * Validation du formulaire de cr�ation d'un nouveau service 
	 *  
	 * @param request
	 * @param response
	 * @param nom
	 * @param prenom
	 * @param motDePasse
	 * @param confirmation
	 * @param email
	 * @param adeli
	 * @param rpps
	 * @param telephone
	 * @param specialite
	 * @param role
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value="/configservice", method = RequestMethod.POST)
	public ModelAndView controleConfigurationService (HttpServletRequest request,
			HttpServletResponse response, @RequestParam String nom, @RequestParam String prenom, @RequestParam String motDePasse,
			@RequestParam String confirmation, @RequestParam String email, @RequestParam String adeli, @RequestParam String rpps,
			@RequestParam String telephone, @RequestParam String specialite, @RequestParam String role) throws Exception {
		

		Map<String, Object> param = new HashMap<>();
		
		// Cr�ation d'un nouveau personnel de sant�
		Dmpcpersonnelsante personnel = new Dmpcpersonnelsante();
		
		// On contr�le l'int�grit� du formulaire
		validationNom(nom);
		validationPrenom(prenom);
		validationMotsDePasse(motDePasse,confirmation);
		validationEmail(email);
		validationNum(adeli, 10);
		validationNum(rpps, 12);
		validationNum(telephone, 10);
		
		personnel.setPersonnelsanteNom(nom);
		personnel.setPersonnelsantePrenom(prenom);
		personnel.setPersonnelsanteMdp(motDePasse);
		personnel.setPersonnelsanteEmail(email);
		personnel.setPersonnelsanteAdeli(adeli);
		personnel.setPersonnelsanteRpps(rpps);
		personnel.setPersonnelsanteTelephone(telephone);
		personnel.setPersonnelsanteRole(role);
		
		dmpcpersonnelsanteDAO.saveOrUpdate(personnel);
		
		System.out.println("Personnel" + personnel.getPersonnelsanteNom() + "enregistr�e  ");
		
		param.put("title", "Accueil");
		param.put("titrePage", "TeleConsult");

		ModelAndView mv = new ModelAndView("accueil");
		
		if(param.containsKey("erreurIdent")){

			mv = new ModelAndView("connexion");
		}
		
		mv.addAllObjects(param);
		return mv;
	}
	
	private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Les mots de passe entr�s sont diff�rents, merci de les saisir � nouveau.");
	        } else if (motDePasse.trim().length() < 3) {
	            throw new Exception("Les mots de passe doivent contenir au moins 3 caract�res.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

	/**
	 * Valide le nom d'utilisateur saisi.
	 */
	private void validationNom( String nom ) throws Exception {
	    if ( nom != null ) {
	        throw new Exception( "Le nom d'utilisateur doit �tre obligatoirement renseign�" );
	    }
	}
	
	private void validationPrenom( String nom ) throws Exception {
	    if ( nom != null ) {
	        throw new Exception( "Le pr�nom de l'utilisateur doit �tre obligatoirement renseign�" );
	    }
	}
	
	private void validationEmail( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	}	
	
	private void validationNum( String telephone, int nb ) throws Exception {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Le num�ro de t�l�phone doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < nb ) {
                throw new Exception( "Le num�ro de t�l�phone doit contenir au moins n chiffres." );
            }
        } else {
            throw new Exception( "Merci d'entrer un num�ro de t�l�phone." );
        }
    }
	
}
