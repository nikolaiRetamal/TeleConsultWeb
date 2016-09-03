/**
 * 
 */
package cnam.teleconsult.modele.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cnam.teleconsult.modele.bean.Avis;
import cnam.teleconsult.modele.bean.Consultation;
import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;

/**
 * @author Jullien
 *
 */

@Repository
public class ConsultationDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 	 

	 
	public ConsultationDAO() {
	}

	
	public ConsultationDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
		 
	@Transactional
	 public List<Consultation> list() {
	     @SuppressWarnings("unchecked")
	     List<Consultation> listConsultation = (List<Consultation>) sessionFactory.getCurrentSession()
	             .createCriteria(Consultation.class)
	             .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	
	     return listConsultation;
	 }
	
	 @Transactional
	 public void saveOrUpdate(Consultation consultation) {
	     sessionFactory.getCurrentSession().saveOrUpdate(consultation);
	 }
	
	 @Transactional
	 public void delete(int id) {
	     Consultation consultationToDelete = new Consultation();
	     consultationToDelete.setConsultationId(id);
	     sessionFactory.getCurrentSession().delete(consultationToDelete);
	 }
	
	 @Transactional
	 public Consultation get(int id) {
	     String hql = "from Consultation where consultation_id=" + id;
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Consultation> Consultations = (List<Consultation>) query.list();
	      
	     if (Consultations != null && !Consultations.isEmpty()) {
	         return Consultations.get(0);
	     }
	      
	     return null;
	 }
	

	 /**
	  * 
	  * Renvoie la liste des consultations d'un hopital
	  * 
	  * @param referentId
	  * @return
	  */
	 @Transactional
	 public List<Consultation> getListeConsultationHopital(int hopitalId) {

		 //C'est une requ�te � 3 jointures, pour des facilit�s de conception on en revient � du bon vieux SQL
		 // On remonte toutes les consultations des m�decins de l'h�pital tri�es par date
		 
	     @SuppressWarnings("unchecked")
		 List<Consultation> consultations = (List<Consultation>)sessionFactory.getCurrentSession().createSQLQuery(
			        "select c.* from consultation c, dmpcpatient p, dmpcstructuresante_medecin m"
			        + " where c.SSN_ID = p.SSN_ID "
			        + " and   m.PERSONNELSANTE_ID = p.PERSONNELSANTE_ID "
			        + " and   m.STRUCTURESANTE_ID = "+hopitalId
			        + " order by c.CONSULTATION_DATE "
			    )
			    .addEntity("c", Consultation.class)
			    .list(); 
	     	      
	     return consultations;
	 }
	 

	 /**
	  * 
	  * Renvoie la liste des consultations d'un referent
	  * 
	  * @param referentId
	  * @return
	  */
	 @Transactional
	 public List<Consultation> getListeConsultationReferent(int referentId) {

		 //Clone du SQL de getListeConsultationHopital
		 
	     @SuppressWarnings("unchecked")
		 List<Consultation> consultations = (List<Consultation>)sessionFactory.getCurrentSession().createSQLQuery(
			        "select c.* from consultation c"
			        + " where PERSONNELSANTE_ID = "+referentId
			        + " order by c.CONSULTATION_DATE "
			    )
			    .addEntity("c", Consultation.class)
			    .list(); 
	     	      
	     return consultations;
	 }

	 /**
	  * 
	  * Renvoie la liste des consultations pour lesquelles un contributeur a �t� sollicit�
	  * 
	  * @param referentId
	  * @return
	  */
	 @Transactional
	 public List<Consultation> getListeAvisReferent(int referentId) {

		 //Clone du SQL de getListeConsultationHopital
		 
	     @SuppressWarnings("unchecked")
		 List<Consultation> consultations = (List<Consultation>)sessionFactory.getCurrentSession().createSQLQuery(
			        "select c.* from consultation c"
			        + " where CONSULTATION_ID in (select CONSULTATION_ID from avis a, contributeur c  where PERSONNELSANTE_ID = "+referentId
			        							+ " and a.DMPCSUBMISSION_ID = c.DMPCSUBMISSION_ID) 	"
			        + " and c.PERSONNELSANTE_ID != "+referentId
			        + " order by c.CONSULTATION_DATE "
			    )
			    .addEntity("c", Consultation.class)
			    .list(); 
	     	      
	     return consultations;
	 }
		
	 

		/**
		 * 
		 * Cette fonction d�tecte l'�tat de la consultation : 
		 *  - 0 : la consultation est "nouvelle" � dispatcher sur un r�f�rent
		 *  - 1 : la consultation est "en traitement", affect�e � un r�f�rent mais pas d�finitivement trait�e
		 *  - 2 : la consultation est "trait�e", elle a un avis d�finitif
		 * 
		 * @return
		 */
		public Integer getEtatConsultation(Consultation consultation) {
			
			Integer result = new Integer(0);
			

			Dmpcpersonnelsante referent = consultation.getDmpcpersonnelsante();
			//Si on trouve le personnel principal, la consultation est d�j� affect�e
			if(referent != null){
				//On est s�r que la consultation est au moins "en traitement"
				result = new Integer(1);
				
				Set<Avis> listeAvis = consultation.getAvises();
				
				if(listeAvis != null){					
					Iterator<Avis> it = listeAvis.iterator();					
					while(it.hasNext()){
						Avis avisEnCours = it.next();
						
						//Si on trouve un avis final dans les avis, la consultation est trait�e
						if(avisEnCours.getFlagFinal() != null && avisEnCours.getFlagFinal() > new Integer(0)){
							result = new Integer(1);	
						}
						
					}
					
				}
				

			}
			
			return result;
		}
	 
}
