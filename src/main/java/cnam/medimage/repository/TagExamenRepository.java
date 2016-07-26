package cnam.medimage.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.TagExamen;

public class TagExamenRepository {

	private Persistence persistence;
	
	public List<TagExamen> findByNomTag(String nom) {
		return persistence.findByIndex("nom", nom, TagExamen.class);
	}
	
	public List<TagExamen> findByExamen(UUID id_examen) {
		return persistence.findByIndex("id_examen", id_examen, TagExamen.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}

	public void save(TagExamen tagExamen) {
		persistence.insert(tagExamen);
	}
	public List<TagExamen> findAll() {
		SelectBuilder<TagExamen> select = persistence.selectBuilder(TagExamen.class);
		return select.execute();
	}
	public TagExamen findOne(UUID uuid) {
		return persistence.findByKey(uuid, TagExamen.class);
	}
	
	public List<UUID> getListeExamens(String nom) {
		List<TagExamen> tagExams = persistence.findByIndex("nom", nom, TagExamen.class);
		List<UUID> listeExams = new ArrayList<>();
		for(TagExamen tagExam : tagExams){
			listeExams.add(tagExam.getId_examen());
		}
		return listeExams;
	}
	
}
