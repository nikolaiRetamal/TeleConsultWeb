package cnam.teleconsult.controller.controleur;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.bean.Specialite;
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;
import cnam.teleconsult.modele.dao.SpecialiteDAO;


/**
 * 
 * Création d'un médecin
 * 
 * @author Luana
 *
 */
@Controller
@Scope("session")
public class CreerMedecinContr {
	

	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	@Autowired
	private SpecialiteDAO specialiteDAO;
	
	/**
	 * Formulaire de création d'un médecin
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/creermedecin", method = RequestMethod.GET)
	public ModelAndView creerMedecin (HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> param = new HashMap<>();
		Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
		//Liste des specialités
		List<Specialite> specialite = new ArrayList<Specialite>();
		specialite = specialiteDAO.list();
		
		param.put("specialite", specialite);
		param.put("title", "Creation");
		param.put("titrePage", "Créer un médecin");
		param.put("hopital", hopital);
		ModelAndView mv = new ModelAndView("creermedecin", param);
		return mv;
	}
	
	@RequestMapping(value="/controle_creationmedecin")
	public ModelAndView controleCreationMedecin (HttpServletRequest request,
			HttpServletResponse response, @RequestParam ("type") String  type, @RequestParam ("nom") String nom, 
			@RequestParam ("prenom") String prenom, @RequestParam ("motDePasse") String motDePasse,
			@RequestParam ("confirmation") String confirmation, @RequestParam ("email") String email, 
			@RequestParam ("adeli") String adeli, @RequestParam ("rpps") String rpps, @RequestParam ("telephone") String telephone, 
			@RequestParam ("idspecialite") String idspecialite, @RequestParam ("role") String role) throws Exception {
		
		
		System.out.println("Entrée dans controle_creationmedecin");
		
		Map<String, Object> param = new HashMap<>();
		Specialite specialitechoisie = new Specialite();
		
		// Création d'un nouveau personnel de santé
		Dmpcpersonnelsante personnel = new Dmpcpersonnelsante();
		
		
		// On contrôle l'intégrité du formulaire
		try {

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
			
			specialitechoisie = specialiteDAO.get( new Integer(idspecialite));
			personnel.setSpecialite(specialitechoisie);
			
			dmpcpersonnelsanteDAO.saveOrUpdate(personnel);
			
			System.out.println("Personnel " + personnel.getPersonnelsanteNom() + " enregistrée  ");
			
			
			//Ajout du médecin à la structure connectée
			Dmpcstructuresante hopital = (Dmpcstructuresante)request.getSession().getAttribute("hopital");
			
			//"toc".equals(type)
			if (type == "referent") {
				hopital.ajouteReferent(personnel);
			}
			else {
				hopital.ajouteMedecin(personnel);
			}
				
			dmpcstructuresanteDAO.saveOrUpdate(hopital);
		
		} catch (Exception e) {

			param.put("erreurForm", e.getMessage());

		}
			

		return new ModelAndView("redirect:/listemedecin");
		
		
		
	}
	
	/**
	 * Valide le mot de passe de l'utilisateur saisi
	 * @param motDePasse
	 * @param confirmation
	 * @throws Exception
	 */
	private void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception{
	    if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
	        if (!motDePasse.equals(confirmation)) {
	            throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
	        } else if (motDePasse.trim().length() < 3) {
	            throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
	        }
	    } else {
	        throw new Exception("Merci de saisir et confirmer votre mot de passe.");
	    }
	}

	/**
	 * Valide le nom d'utilisateur saisi
	 * @param nom
	 * @throws Exception
	 */
	 
	private void validationNom( String nom ) throws Exception {
	    if ( nom == null ) {
	        throw new Exception( "Le nom d'utilisateur doit être obligatoirement renseigné" );
	    }
	}
	
	/**
	 * Valide le prénom de l'utilisateur saisi
	 * @param nom
	 * @throws Exception
	 */
	 
	private void validationPrenom( String nom ) throws Exception {
	    if ( nom == null ) {
	        throw new Exception( "Le prénom de l'utilisateur doit être obligatoirement renseigné" );
	    }
	}
	
	/**
	 * Valide l'email de l'utilisateur saisi
	 * @param email
	 * @throws Exception
	 */
	private void validationEmail( String email ) throws Exception {
	    if ( email != null && email.trim().length() != 0 ) {
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    } else {
	        throw new Exception( "Merci de saisir une adresse mail." );
	    }
	}	
	
	/**
	 * Valide les champs telephone, adelie et rpps d'utilisateur saisis
	 * @param telephone
	 * @param nb
	 * @throws Exception
	 */
	 
	private void validationNum( String telephone, int nb ) throws Exception {
        if ( telephone != null ) {
            if ( !telephone.matches( "^\\d+$" ) ) {
                throw new Exception( "Le champs doit uniquement contenir des chiffres." );
            } else if ( telephone.length() < nb ) {
                throw new Exception( "Le champs doit contenir au moins n chiffres." );
            }
        } else {
            throw new Exception( "Merci d'entrer un numéro " );
        }
    }
	
	/**
	 * Suppression d'un médecin
	 * @param id
	 * @return
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam ("id") int id) throws RemoteException, NotBoundException {
	
		// Suppression du personnel
		dmpcpersonnelsanteDAO.delete(id);		
		return new ModelAndView("redirect:/listemedecin");
    }
	
	
	
	/*
	
	/*@RequestMapping(value = "/modifier", method = RequestMethod.POST)
	public ModelAndView modifier(HttpServletRequest request) throws RemoteException, NotBoundException {
		
		System.out.print("Modification fiche médecin");
		
		Dmpcpersonnelsante personnel = new Dmpcpersonnelsante();
		
		//Récupération de l'id
		Integer id = Integer.parseInt(request.getParameter("id"));
		System.out.print("id_medecin" + id);
		
		//personnel.setID(id);
		// Modi
		
			
		return model;
		
	}	
	
	 */
	
}
