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
import cnam.teleconsult.modele.bean.Dmpcstructuresante;


/**
 * @author Jullien
 *
 */

@Repository
public class DmpcstructuresanteDAO {

	 @Autowired
	    private SessionFactory sessionFactory;
	 
	 
	public DmpcstructuresanteDAO() {
	}


	public DmpcstructuresanteDAO(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	 

    @Transactional
    public List<Dmpcstructuresante> list() {
        @SuppressWarnings("unchecked")
        List<Dmpcstructuresante> listDmpcstructuresante = (List<Dmpcstructuresante>) sessionFactory.getCurrentSession()
                .createCriteria(Dmpcstructuresante.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listDmpcstructuresante;
    }
 
    @Transactional
    public void saveOrUpdate(Dmpcstructuresante dmpcstructuresante) {
        sessionFactory.getCurrentSession().saveOrUpdate(dmpcstructuresante);
    }
 
    @Transactional
    public void delete(int id) {
        Dmpcstructuresante dmpcstructuresanteToDelete = new Dmpcstructuresante();
        dmpcstructuresanteToDelete.setStructuresanteId(id);
        sessionFactory.getCurrentSession().delete(dmpcstructuresanteToDelete);
    }
 
    @Transactional
    public Dmpcstructuresante get(int id) {
        String hql = "from Dmpcstructuresante where structuresante_id=" + id;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
         
        @SuppressWarnings("unchecked")
        List<Dmpcstructuresante> Dmpcstructuresantes = (List<Dmpcstructuresante>) query.list();
         
        if (Dmpcstructuresantes != null && !Dmpcstructuresantes.isEmpty()) {
            return Dmpcstructuresantes.get(0);
        }
         
        return null;
    }

    @Transactional
    public Dmpcstructuresante getByPassword(String login,String password) {

        String hql = "from Dmpcstructuresante where structuresante_login='" + login + "' and structuresante_mdp ='" + password+"'";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
         
        @SuppressWarnings("unchecked")
        List<Dmpcstructuresante> Dmpcstructuresantes = (List<Dmpcstructuresante>) query.list();
         
        if (Dmpcstructuresantes != null && !Dmpcstructuresantes.isEmpty()) {
            return Dmpcstructuresantes.get(0);
        }
         
        return null;
    }
    

    @Transactional
    public List<Dmpcstructuresante> getByReferent(int referentId) {
		 
	     @SuppressWarnings("unchecked")
		 List<Dmpcstructuresante> structures = (List<Dmpcstructuresante>)sessionFactory.getCurrentSession().createSQLQuery(
			        "select s.* from dmpcstructuresante s, dmpcstructuresante_referent r"
			        + " where s.STRUCTURESANTE_ID = r.STRUCTURESANTE_ID "
			        + " and   r.PERSONNELSANTE_ID = "+referentId
			        
			    )
			    .addEntity("s", Dmpcstructuresante.class)
			    .list(); 
	     	      
	     return structures;
    }
    
}
