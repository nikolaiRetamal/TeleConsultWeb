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

import cnam.teleconsult.modele.bean.Avis;

/**
 * @author Jullien
 *
 */

@Repository
public class AvisDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 	 

	 
	public AvisDAO() {
	}

	
	public AvisDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
		 
	@Transactional
	 public List<Avis> list() {
	     @SuppressWarnings("unchecked")
	     List<Avis> listAvis = (List<Avis>) sessionFactory.getCurrentSession()
	             .createCriteria(Avis.class)
	             .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	
	     return listAvis;
	 }
	
	 @Transactional
	 public void saveOrUpdate(Avis dmpcsubmission) {
	     sessionFactory.getCurrentSession().saveOrUpdate(dmpcsubmission);
	 }
	
	 @Transactional
	 public void delete(int id) {
	     Avis dmpcsubmissionToDelete = new Avis();
	     dmpcsubmissionToDelete.setDmpcsubmissionId(id);
	     sessionFactory.getCurrentSession().delete(dmpcsubmissionToDelete);
	 }
	
	 @Transactional
	 public Avis get(int id) {
	     String hql = "from Avis where dmpcsubmission_id=" + id;
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Avis> Aviss = (List<Avis>) query.list();
	      
	     if (Aviss != null && !Aviss.isEmpty()) {
	         return Aviss.get(0);
	     }
	      
	     return null;
	 }
	
}
