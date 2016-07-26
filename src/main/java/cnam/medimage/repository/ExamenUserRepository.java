package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.ExamenUser;
import cnam.medimage.bean.UsageUser;

public class ExamenUserRepository {
	private Persistence persistence;
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}
	
	public List<ExamenUser> findByExamen(UUID id_examen) {
		return persistence.findByIndex("id_examen", id_examen, ExamenUser.class);
	}

	public List<ExamenUser> findByUser(UUID id_user) {
		return persistence.findByIndex("id_user", id_user, ExamenUser.class);
	}
	

	public List<ExamenUser> findAll() {
		SelectBuilder<ExamenUser> select = persistence.selectBuilder(ExamenUser.class);
		return select.execute();
	}
	
	public ExamenUser findOne(UUID id) {
		return persistence.findByKey(id, ExamenUser.class);
	}
	
	public void save(ExamenUser examenUser) {
		persistence.insert(examenUser);
	}
}
