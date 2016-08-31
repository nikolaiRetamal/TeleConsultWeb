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

import cnam.teleconsult.modele.bean.Dmpcpersonnelsante;

/**
 * @author Jullien
 *
 */

@Repository
public class DmpcpersonnelsanteDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 	 

	 
	public DmpcpersonnelsanteDAO() {
	}

	
	public DmpcpersonnelsanteDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
		 
	@Transactional
	 public List<Dmpcpersonnelsante> list() {
	     @SuppressWarnings("unchecked")
	     List<Dmpcpersonnelsante> listDmpcpersonnelsante = (List<Dmpcpersonnelsante>) sessionFactory.getCurrentSession()
	             .createCriteria(Dmpcpersonnelsante.class)
	             .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	
	     return listDmpcpersonnelsante;
	 }
	
	 @Transactional
	 public void saveOrUpdate(Dmpcpersonnelsante dmpcpersonnelsante) {
	     sessionFactory.getCurrentSession().saveOrUpdate(dmpcpersonnelsante);
	 }
	
	 @Transactional
	 public void delete(int id) {
	     Dmpcpersonnelsante dmpcpersonnelsanteToDelete = get(id);
	     sessionFactory.getCurrentSession().delete(dmpcpersonnelsanteToDelete);
	 }
	
	 @Transactional
	 public Dmpcpersonnelsante get(int id) {
	     String hql = "from Dmpcpersonnelsante where personnelsante_id=" + id;
	     
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Dmpcpersonnelsante> Dmpcpersonnelsantes = (List<Dmpcpersonnelsante>) query.list();
	      
	     if (Dmpcpersonnelsantes != null && !Dmpcpersonnelsantes.isEmpty()) {
	         return Dmpcpersonnelsantes.get(0);
	     }
	      
	     return null;
	 }
	
	 @Transactional
	 public Dmpcpersonnelsante getByPassword(String login,String password) {
	
	     String hql = "from Dmpcpersonnelsante where personnelsante_nom ='" + login + "' and personnelsante_mdp ='" + password+"'";
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);
	      
	     @SuppressWarnings("unchecked")
	     List<Dmpcpersonnelsante> Dmpcpersonnelsantes = (List<Dmpcpersonnelsante>) query.list();
	      
	     if (Dmpcpersonnelsantes != null && !Dmpcpersonnelsantes.isEmpty()) {
	         return Dmpcpersonnelsantes.get(0);
	     }
	      
	     return null;
	 }
		 
}
