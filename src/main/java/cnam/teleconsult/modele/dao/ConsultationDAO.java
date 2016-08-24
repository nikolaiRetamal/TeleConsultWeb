/**
 * 
 */
package cnam.teleconsult.modele.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cnam.teleconsult.modele.bean.Consultation;

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
	     String hql = "from consultation where consultation_id=" + id;
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Consultation> Consultations = (List<Consultation>) query.list();
	      
	     if (Consultations != null && !Consultations.isEmpty()) {
	         return Consultations.get(0);
	     }
	      
	     return null;
	 }
	
		 
}
