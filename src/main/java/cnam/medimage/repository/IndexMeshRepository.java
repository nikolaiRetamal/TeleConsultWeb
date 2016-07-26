package cnam.medimage.repository;

import java.util.List;

import org.easycassandra.persistence.cassandra.Persistence;

import cnam.medimage.bean.IndexMesh;

public class IndexMeshRepository {

	private Persistence persistence;
	
	public List<IndexMesh> findByNom(String nom) {
		return persistence.findByIndex("nom", nom, IndexMesh.class);
	}

	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}


	public void save(IndexMesh index) {
		persistence.insert(index);
	}

	public IndexMesh findByDicomId(String id_tag) {
		return persistence.findByKey(id_tag, IndexMesh.class);
	}
	
	public List<IndexMesh> getAllIndexes(){
		System.out.println("All Indexes...");
		return persistence.findAll(IndexMesh.class);
	}
}
