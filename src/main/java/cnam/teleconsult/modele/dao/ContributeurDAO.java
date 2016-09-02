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

import cnam.teleconsult.modele.bean.Contributeur;
import cnam.teleconsult.modele.bean.ContributeurId;

/**
 * @author Jullien
 *
 */

@Repository
public class ContributeurDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 	 

	 
	public ContributeurDAO() {
	}

	
	public ContributeurDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
		 
	@Transactional
	 public List<Contributeur> list() {
	     @SuppressWarnings("unchecked")
	     List<Contributeur> listContributeur = (List<Contributeur>) sessionFactory.getCurrentSession()
	             .createCriteria(Contributeur.class)
	             .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	
	     return listContributeur;
	 }
	
	 @Transactional
	 public void saveOrUpdate(Contributeur contributeur) {
	     sessionFactory.getCurrentSession().saveOrUpdate(contributeur);
	 }
	
	 @Transactional
	 public void delete(int dmpcsubmissionId,int personnelSanteId) {
	     Contributeur contributeurToDelete = new Contributeur();
	     contributeurToDelete.setId(new ContributeurId(dmpcsubmissionId,personnelSanteId));
	     sessionFactory.getCurrentSession().delete(contributeurToDelete);
	 }
	
	 @Transactional
	 public Contributeur get(int dmpcsubmissionId,int personnelSanteId) {
	     String hql = "from Contributeur where dmpcsubmission_id=" + dmpcsubmissionId+
	    		 " and personnelsante_id = "+personnelSanteId;
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Contributeur> Contributeurs = (List<Contributeur>) query.list();
	      
	     if (Contributeurs != null && !Contributeurs.isEmpty()) {
	         return Contributeurs.get(0);
	     }
	      
	     return null;
	 }
	
}