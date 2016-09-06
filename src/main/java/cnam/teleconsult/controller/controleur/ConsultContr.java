package cnam.teleconsult.controller.controleur;


import java.util.ArrayList;
import java.util.Iterator;
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
 * Ce Contrôleur couvre la lecture d'une consultation donnée
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
	 * Remontée d'une consultation "consultationId"
	 * 
	 * - Dans le cas d'un hôpital on y affecte les référents
	 * - Dans le cas d'un référent on peut consulter ses consultations et ses demandes d'avis
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
		
		/* On parcourt les résultats afin de remonter le résultat affiché */
		Resultat resultat = null;
		ArrayList<Resultat> resultats = new ArrayList<Resultat>();
		resultats.addAll(consultation.getResultats());
		
			
		
		//On récupère le paramètre resultId
		Integer resultId = 0;	
		try {
			resultId = Integer.parseInt(request.getParameter("resultId"));
			if(resultId == resultats.size()) resultId = 0;
		} catch (Exception e) {
			//pas de resultId, on laisse la valeur par défaut
		}
						
		//La liste des confrères affichée est la liste des référents de l'hôpital moins le référent
		//Moins les référents déjà sollicités
		List<Dmpcpersonnelsante> confreresTampon = hopital.getListeReferent();
		ArrayList<Dmpcpersonnelsante> confreres = new ArrayList<Dmpcpersonnelsante>();
		
		for(Dmpcpersonnelsante confrere:confreresTampon){
			if(confrere.getPersonnelsanteId() != referent.getPersonnelsanteId()){
				confreres.add(confrere);
			}
		}
		
		if(!resultats.isEmpty()){

			resultat = resultats.get(resultId);
			
			resultat = formatte(resultat);
			
			model.addObject("resultat", resultat);
			model.addObject("titreResultat", "Examen "+(resultId+1)+"/"+resultats.size()+" : "+resultat.getExamen().getExamenNom());	
			
		}else{

			model.addObject("titreResultat", "Pas d'examen attaché à cette consultation.");	
		}
		
		
		
		model.addObject("consultation", consultation);
		model.addObject("consulter", consulter);
		model.addObject("aviser", aviser);
		model.addObject("confreres", confreres);
		model.addObject("resultId", resultId);	
		model.addObject("title", "Consultation" );
		model.addObject("titrePage", patient.getPatientNom()+" "+patient.getPatientPrenom()+" - "+consultation.getConsultationDateLisible() );
		
		return model;
	}
	
	/***
	 * 
	 * Servlet d'ajout d'une demande d'avis à un confrère
	 * 
	 * @param request
	 * @param consulter
	 * @param resultId
	 * @return
	 */
	@RequestMapping(value = "/demandeAvis")
	public ModelAndView demandeAvis (HttpServletRequest request, @RequestParam String consulter, @RequestParam String aviser, @RequestParam String resultId){

		ModelAndView model = new ModelAndView("redirect:/afficheConsultation");
		
		//On récupère le confrère à consulter
		Integer confreres = Integer.parseInt(request.getParameter("confreres"));		
		Dmpcpersonnelsante confrere = dmpcpersonnelsanteDAO.get(new Integer(confreres));		
		
		Consultation consultation = null;
		//On récupère la consultation		

		if(consulter != null && !"".equals(consulter)) {
			 consultation= consultationDAO.get(new Integer(consulter)) ;			
		} else {
			 consultation= consultationDAO.get(new Integer(aviser)) ;
		}
		
		
		//On crée le nouvel avis à insérer
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
	
	/***
	 * 
	 * Servlet de rédaction d'un avis
	 * 
	 * @param request
	 * @param consulter
	 * @param resultId
	 * @return
	 */
	@RequestMapping(value = "/redactionAvis")
	public ModelAndView redactionAvis (HttpServletRequest request, @RequestParam String consulter, @RequestParam String aviser, @RequestParam String resultId){

		
		ModelAndView model = new ModelAndView("redirect:/afficheConsultation");
				
		Consultation consultation = null;
		Dmpcpersonnelsante referent = (Dmpcpersonnelsante)request.getSession().getAttribute("referent");
		//On récupère la consultation		

		if(consulter != null && !"".equals(consulter)) {
			 consultation= consultationDAO.get(new Integer(consulter)) ;			
		} else {
			 consultation= consultationDAO.get(new Integer(aviser)) ;
		}

		String definitif = request.getParameter("definitif");
		String avisRedige = request.getParameter("avisRedige");
		
		Set<Avis> avises = consultation.getAvises();
		Iterator<Avis> itAvis = avises.iterator();
		
		//On itère sur les avis de la consultation
		while(itAvis.hasNext()){
		
			Avis avisCourant = itAvis.next();
		   //Si c'est un contributeur il faut encore itérer
			Set<Contributeur> contributeurs = avisCourant.getContributeurs();
			Iterator<Contributeur> itContributeur = contributeurs.iterator();
			
			while(itContributeur.hasNext()){
				Contributeur contributeurCourant = itContributeur.next();
				if(referent.getPersonnelsanteId() == contributeurCourant.getDmpcpersonnelsante().getPersonnelsanteId()){
					avisCourant.setAvis(avisRedige);

					if(definitif != null){
						avisCourant.setFlagFinal(1);
					}
				}			
				avisDAO.saveOrUpdate(avisCourant);		
			}
		}
		

		model.addObject("consulter", consulter);
		model.addObject("aviser", aviser);
		model.addObject("resultId", resultId);		
		return model;
		
	}

	/**
	 * 
	 * Fonction de mise en forme des différents résultats
	 * - Actuellement : mets en forme le rythme cardiaque pour affichage Google Chart
	 * 
	 * @param resultat
	 * @return
	 */
	private Resultat formatte (Resultat resultat){
		
		String rawData = resultat.getRawData() ;
		
		if(rawData!= null && !"NoData".equals(rawData)){
			//On récupère les pulsations
			rawData = rawData.replace("[", "").replace("]", "");
			String[] data = rawData.split(",");
			
			String newData = "[";
			int time=0;
			
			for(String s:data){
				newData+="["+time+","+s.trim()+"],";
				time++;
			}			
			newData+="]";

			resultat.setRawData(newData);
		}
		
		
		return resultat;
	}
	
	
	
	
}
