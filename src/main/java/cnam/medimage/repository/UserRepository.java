package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;

import cnam.medimage.bean.User;

public class UserRepository {

	private Persistence persistence;
	
	public List<User> findByNom(String nom) {
		return persistence.findByIndex("nom", nom, User.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}

	public void save(User user) {
		persistence.insert(user);
	}

	public User findOne(UUID uuid) {
		return persistence.findByKey(uuid, User.class);
	}
}
