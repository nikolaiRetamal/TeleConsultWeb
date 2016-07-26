package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.UsageUser;

public class UsageUserRepository {
	private Persistence persistence;
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}
	
	public List<UsageUser> findByUsage(UUID id_usage) {
		return persistence.findByIndex("id_usage", id_usage, UsageUser.class);
	}

	public List<UsageUser> findByUser(UUID id_user) {
		return persistence.findByIndex("id_user", id_user, UsageUser.class);
	}
	

	public List<UsageUser> findAll() {
		SelectBuilder<UsageUser> select = persistence.selectBuilder(UsageUser.class);
		return select.execute();
	}
	
	public UsageUser findOne(UUID id) {
		return persistence.findByKey(id, UsageUser.class);
	}
	
	public void save(UsageUser dicomUser) {
		persistence.insert(dicomUser);
	}
}
