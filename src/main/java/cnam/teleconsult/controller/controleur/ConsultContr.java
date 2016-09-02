package cnam.teleconsult.controller.controleur;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cnam.teleconsult.modele.bean.Avis;
import cnam.teleconsult.modele.bean.Consultation;
import cnam.teleconsult.modele.bean.Contributeur;
import cnam.teleconsult.modele.bean.ContributeurId;
import cnam.teleconsult.modele.bean.Dmpcpatient;
import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;
import cnam.teleconsult.modele.bean.Dmpcstructuresante;
import cnam.teleconsult.modele.bean.Resultat;
import cnam.teleconsult.modele.dao.AvisDAO;
import cnam.teleconsult.modele.dao.ConsultationDAO;
import cnam.teleconsult.modele.dao.ContributeurDAO;
import cnam.teleconsult.modele.dao.DmpcpersonnelsanteDAO;
import cnam.teleconsult.modele.dao.DmpcstructuresanteDAO;

/**
 * 
 * Ce Contr�leur couvre la lecture d'une consultation donn�e
 * 
 * @author Jullien
 *
 */
@Controller
@Scope("session")
public class ConsultContr {
	
	@Autowired
    private DmpcstructuresanteDAO dmpcstructuresanteDAO;
	@Autowired
    private DmpcpersonnelsanteDAO dmpcpersonnelsanteDAO;
	@Autowired
	private ConsultationDAO consultationDAO;
	@Autowired
	private AvisDAO avisDAO;
	@Autowired
	private ContributeurDAO contributeurDAO;

	
	/**
	 * 
	 * Remont�e d'une consultation "consultationId"
	 * 
	 * - Dans le cas d'un h�pital on y affecte les r�f�rents
	 * - Dans le cas d'un r�f�rent on peut consulter ses consultations et ses demandes d'avis
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/afficheConsultation")
	public ModelAndView medecinConsultations (HttpServletRequest request){

		ModelAndView model = new ModelAndView("teleConsult");
		Dmpcpersonnelsante referent = (Dmpcpersonnelsante)request.getSession().getAttribute("referent");
		
		List<Dmpcstructuresante> hopitaux = dmpcstructuresanteDAO.getByReferent(referent.getPersonnelsanteId());
		Dmpcstructuresante hopital = hopitaux.iterator().next();
				

		String consulter = request.getParameter("consulter");
		String aviser 	 = request.getParameter("aviser");
		
		Consultation consultation = null;
		
		if(consulter != null && !"".equals(consulter)) {
			 consultation= consultationDAO.get(new Integer(consulter)) ;			
		} else {
			 consultation= consultationDAO.get(new Integer(aviser)) ;
		}
		
		Dmpcpatient patient = consultation.getDmpcpatient();
		
		/* On parcourt les r�sultats afin de remonter le r�sultat affich� */
		Resultat resultat = null;
		ArrayList<Resultat> resultats = new ArrayList<Resultat>();
		resultats.addAll(consultation.getResultats());
			
		
		//On r�cup�re le param�tre resultId
		Integer resultId = 0;	
		try {
			resultId = Integer.parseInt(request.getParameter("resultId"));
			if(resultId == resultats.size()) resultId = 0;
		} catch (Exception e) {
			//pas de resultId, on laisse la valeur par d�faut
		}
						
		//La liste des confr�res affich�e est la liste des r�f�rents de l'h�pital moins le r�f�rent
		List<Dmpcpersonnelsante> confreresTampon = hopital.getListeReferent();
		ArrayList<Dmpcpersonnelsante> confreres = new ArrayList<Dmpcpersonnelsante>();
		
		for(Dmpcpersonnelsante confrere:confreresTampon){
			if(confrere.getPersonnelsanteId() != referent.getPersonnelsanteId()){
				confreres.add(confrere);
			}
		}
		
		resultat = resultats.get(resultId);

		model.addObject("consultation", consultation);
		model.addObject("resultat", resultat);
		model.addObject("consulter", consulter);
		model.addObject("aviser", aviser);
		model.addObject("confreres", confreres);
		model.addObject("resultId", resultId);
		model.addObject("titreResultat", "Examen "+(resultId+1)+"/"+resultats.size()+" : "+resultat.getExamen().getExamenNom());		
		model.addObject("title", "Consultation" );
		model.addObject("titrePage", patient.getPatientNom()+" "+patient.getPatientPrenom()+" - "+consultation.getConsultationDateLisible() );
		
		return model;
	}
	
	/***
	 * 
	 * Servlet d'ajout d'une demande d'avis � un confr�re
	 * 
	 * @param request
	 * @param consulter
	 * @param resultId
	 * @return
	 */
	@RequestMapping(value = "/demandeAvis")
	public ModelAndView demandeAvis (HttpServletRequest request, @RequestParam String consulter, @RequestParam String aviser, @RequestParam String resultId){

		ModelAndView model = new ModelAndView("redirect:/afficheConsultation");
		
		//On r�cup�re le confr�re � consulter
		Integer confreres = Integer.parseInt(request.getParameter("confreres"));		
		Dmpcpersonnelsante confrere = dmpcpersonnelsanteDAO.get(new Integer(confreres));		
		
		Consultation consultation = null;
		//On r�cup�re la consultation		

		if(consulter != null && !"".equals(consulter)) {
			 consultation= consultationDAO.get(new Integer(consulter)) ;			
		} else {
			 consultation= consultationDAO.get(new Integer(aviser)) ;
		}
		
		
		
		//On cr�e le nouvel avis � ins�rer
		Avis nouvelAvis = new Avis(consultation,"");
		avisDAO.saveOrUpdate(nouvelAvis);
		
		ContributeurId contributeurId = new ContributeurId(nouvelAvis.getDmpcsubmissionId(), confrere.getPersonnelsanteId());
		
		Contributeur contributeur = new Contributeur(contributeurId,nouvelAvis,confrere,"");
		contributeurDAO.saveOrUpdate(contributeur);
		consultation.getAvises().add(nouvelAvis);
		//On sauve le tout
		consultationDAO.saveOrUpdate(consultation);
				
		
		model.addObject("consulter", consulter);
		model.addObject("aviser", aviser);
		model.addObject("resultId", resultId);		
		return model;
		
	}
	
	
	
}
