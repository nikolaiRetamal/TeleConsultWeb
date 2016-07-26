package cnam.medimage.repository;

import org.easycassandra.persistence.cassandra.ClusterInformation;
import org.easycassandra.persistence.cassandra.EasyCassandraManager;
import org.easycassandra.persistence.cassandra.Persistence;

public enum CassandraManager {
	INSTANCE;
	private EasyCassandraManager easyCassandraManager;
	private Persistence persistence;
	{
		easyCassandraManager = new EasyCassandraManager(ClusterInformation.create().addHost("localhost").withKeySpace("medimage"));
		persistence = easyCassandraManager.getPersistence();
	}
	
	public Persistence getPersistence() {
		return persistence;
	}
}
