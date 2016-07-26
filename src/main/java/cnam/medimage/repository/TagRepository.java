package cnam.medimage.repository;

import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;

import cnam.medimage.bean.MetaData;
import cnam.medimage.bean.Tag;

public class TagRepository {

	private Persistence persistence;
	
	public List<Tag> findByNom(String nom) {
		return persistence.findByIndex( nom, Tag.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}


	public void save(Tag tag) {
		persistence.insert(tag);
	}

	public Tag findByDicomId(UUID uuid) {
		return persistence.findByKey(uuid, Tag.class);
	}
	
	public List<Tag> findAll(){
		return persistence.findAll(Tag.class);
	}
	
}
