package cnam.medimage.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.easycassandra.persistence.cassandra.Persistence;
import org.easycassandra.persistence.cassandra.SelectBuilder;

import cnam.medimage.bean.Dicom;
import cnam.medimage.bean.DicomUser;
import cnam.medimage.bean.ExamenDicom;
import cnam.medimage.bean.MetaData;
import cnam.medimage.bean.Tag;
import cnam.medimage.bean.UsageUser;

public class DicomRepository {

	private Persistence persistence;
	
	public List<Dicom> findByIndex(String nom) {
		return persistence.findByIndex("nom", nom, Dicom.class);
	}

	public List<Dicom> findByExam(UUID id_examen) {
		return persistence.findByIndex("id_examen", id_examen, Dicom.class);
	}
	
	{
		this.persistence = CassandraManager.INSTANCE.getPersistence();
	}

	public void save(Dicom dicom) {
		MetadataRepository metaRepo = new MetadataRepository();
		TagRepository tagRepo = new TagRepository();
		
		//Sauvegarde des MetaDatas dans la table medimage.metadata
	    Iterator it = dicom.getMetadataIds().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry metadata = (Map.Entry)it.next();
	        metaRepo.save(new MetaData(UUID.randomUUID(), dicom.getId_dicom(),
	        		(String) metadata.getKey(),(String) metadata.getValue()));
	    }
	    //Sauvegarde des tags dans la table medimage.tag
	    for(String tag : dicom.getTagsId()) {
	    	Tag monTag = new Tag(UUID.randomUUID() ,dicom.getId_dicom(),tag,Boolean.TRUE);
	    	
	    	//Détection des tags codifiés des non-codifiés
	    	//On split le tag sur le caractère "D" et on tente de faire un Integer avec la deuxième partie
	    	//Si le tag est de type "D0000000" ça passe, il est codifié, sinon -> non-codifié
	    	try{
	    		new Integer(tag.trim().split("D")[1]);
	    	}catch(Exception e){
	    		//Le tag n'est pas un id_tag de type "DXXXXX"
	    		monTag.setCodifie(Boolean.FALSE);
	    	}
	    	
	        tagRepo.save(monTag);
	    }
	    //Sauvegarde de l'association DICOM-USER dans la table DICOM_USER
	    DicomUserRepository dicomUserRepo = new DicomUserRepository();
	    dicomUserRepo.save(new DicomUser(UUID.randomUUID(), dicom.getId_dicom(), dicom.getId_user()));
	    
	    //Sauvegarde de l'association DICOM-EXAMEN dans la table EXAMEN_DICOM
	    ExamenDicomRepository examDicomRepo = new ExamenDicomRepository();
	    examDicomRepo.save(new ExamenDicom(UUID.randomUUID(), dicom.getId_dicom(), dicom.getId_examen()));
	    
	    //Sauvegarde du DICOM
		persistence.insert(dicom);
	}

	public List<Dicom> findAll() {
		SelectBuilder<Dicom> select = persistence.selectBuilder(Dicom.class);
		return select.execute();
	}
	public Dicom findOne(UUID uuid) {
		return persistence.findByKey(uuid, Dicom.class);
	}

	
	public List<Dicom> findDicomByTagNom(String tagNom) {
		
		//On va chercher les Dicom UUID dans la table de jointure tag
		TagRepository tagRepo = new TagRepository();
		List<Tag> tags = tagRepo.findByNom(tagNom);
		
		ArrayList<UUID> list_id_dicom = new ArrayList<UUID>();
		
		for(Tag t:tags){
			list_id_dicom.add(t.getId_dicom());
		}
		
		//On traite les résultats avec une map pour éviter les doublons
		HashMap<UUID,Dicom> resultat = new HashMap<UUID,Dicom>();
		
		//On itère pour chaque Dicom Remontés
		for(Tag t:tags){
			
			//Si le résultat ne contient pas encore le Dicom
			if(!resultat.containsKey(t.getId_dicom())){
				resultat.put(t.getId_dicom(), findOne(t.getId_dicom()));
			}
						
		}
		
		ArrayList<Dicom> liste = new ArrayList<Dicom>();
		liste.addAll(resultat.values());
		
		return liste;
		
	}
}
