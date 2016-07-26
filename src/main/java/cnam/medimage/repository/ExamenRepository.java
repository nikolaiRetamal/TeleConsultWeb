package cnam.medimage.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.Examen;
import cnam.medimage.bean.ExamenUser;
import cnam.medimage.bean.MetaDataExamen;
import cnam.medimage.bean.TagExamen;
import cnam.medimage.bean.UsageExamen;
import cnam.medimage.bean.UsageUser;

public class ExamenRepository {

	private Persistence persistence;
	
	public List<Examen> findByExam(UUID id_examen) {
		return persistence.findByIndex("id_examen", id_examen, Examen.class);
	}
	
	public List<Examen> findByDicom(UUID id_dicom) {
		return persistence.findByIndex("id_dicom", id_dicom, Examen.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}

	public List<Examen> findAll() {
		SelectBuilder<Examen> select = persistence.selectBuilder(Examen.class);
		return select.execute();
	}
	
	public Examen findOne(UUID uuid) {
		return persistence.findByKey(uuid, Examen.class);
	}
	
	public void save(Examen examen, UUID id_usage) {
		//Sauvergarde en base de l'association usage-exam dans la table USAGE_EXAMEN
		UsageExamenRepository usageExamRepo = new UsageExamenRepository();
		usageExamRepo.save(new UsageExamen(UUID.randomUUID(), id_usage, examen.getId_examen()));
		
		//Sauvergarde en base de l'association usage-exam dans la table EXAMEN_USER
		ExamenUserRepository examUserRepo = new ExamenUserRepository();
		examUserRepo.save(new ExamenUser(UUID.randomUUID(), examen.getId_examen(), examen.getId_user()));
		
		//Sauvegarde des tags dans la table TAG_EXAMEN
		TagExamenRepository tagExamRepo = new TagExamenRepository();
		for(String tag : examen.getTagsId()) {
			tagExamRepo.save(new TagExamen(UUID.randomUUID(), tag, examen.getId_examen()));
	    }
		
	    //Sauvegarde de l'association USAGE-USER dans la table USAGE_USER
	    UsageUserRepository usageUserRepo = new UsageUserRepository();
	    UsageRepository usageRepo = new UsageRepository();
	    System.out.println("Réponse  = " + usageRepo.findOne(id_usage));
	    System.out.println("Taille  = " + (boolean) (usageRepo.findOne(id_usage) != null));
	    if(usageRepo.findOne(id_usage) == null)
	    	usageUserRepo.save(new UsageUser(UUID.randomUUID(), id_usage, examen.getId_user()));
	    
		//Sauvegarde des metadonnées dans la table METADATA_EXAMEN
		MetaDataExamenRepository metadataExamenRepo = new MetaDataExamenRepository();
		for (Map.Entry<String, String> entry : examen.getMetadataIds().entrySet()){
			metadataExamenRepo.save(new MetaDataExamen(UUID.randomUUID(), examen.getId_examen(), entry.getKey(), entry.getValue()));
		}
		System.out.println("date avant enregistrement = " + examen.getDate_import());
		//Sauvegarde en base de l'examen
		persistence.insert(examen);
	}

}
