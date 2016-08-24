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

import cnam.teleconsult.modele.bean.Specialite;

/**
 * @author Jullien
 *
 */

@Repository
public class SpecialiteDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 	 

	 
	public SpecialiteDAO() {
	}

	
	public SpecialiteDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
		 
	@Transactional
	 public List<Specialite> list() {
	     @SuppressWarnings("unchecked")
	     List<Specialite> listSpecialite = (List<Specialite>) sessionFactory.getCurrentSession()
	             .createCriteria(Specialite.class)
	             .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	
	     return listSpecialite;
	 }
	
	 @Transactional
	 public void saveOrUpdate(Specialite specialite) {
	     sessionFactory.getCurrentSession().saveOrUpdate(specialite);
	 }
	
	 @Transactional
	 public void delete(int id) {
	     Specialite specialiteToDelete = new Specialite();
	     specialiteToDelete.setSpecialiteId(id);
	     sessionFactory.getCurrentSession().delete(specialiteToDelete);
	 }
	
	 @Transactional
	 public Specialite get(int id) {
	     String hql = "from specialite where specialite_id=" + id;
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Specialite> Specialites = (List<Specialite>) query.list();
	      
	     if (Specialites != null && !Specialites.isEmpty()) {
	         return Specialites.get(0);
	     }
	      
	     return null;
	 }
	
}
