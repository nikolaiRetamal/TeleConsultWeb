package cnam.medimage.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "examen") 
public class Examen implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id_examen;
	@Column
	private Date date_import;
	@Column
	private UUID id_user;
	private String nom_user;
	@Column
	private String nom_examen;
	@Column
	private String nom_usage;
	@ElementCollection
	@Column
	private List<String> tags;
	//Non présent en base, sert à stocker temporairement
	//les id tag pour les enregistrer dans les tables d'association
	private List<String> tagsId;
	@ElementCollection
	@Column
	private Map<String, String> metadatas;
	//Non présent en base, sert à stocker temporairement
	//les id metadata pour les enregistrer dans les tables d'association
	private Map<String, String> metadataIds;
	
	public Examen() {
		this.tags = new LinkedList<>();
		this.metadatas = new HashMap<>();
		this.tagsId = new LinkedList<>();
		this.metadataIds = new HashMap<>();
	}

	public Examen(UUID id_examen, Date date_import, UUID id_user,
			String nom_examen, String nom_usage, List<String> tags,
			Map<String, String> metadatas) {
		super();
		this.id_examen = id_examen;
		this.date_import = date_import;
		this.id_user = id_user;
		this.nom_examen = nom_examen;
		this.nom_usage = nom_usage;
		this.tags = tags;
		this.metadatas = metadatas;
	}


	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
	}

	public Date getDate_import() {
		return date_import;
	}

	public void setDate_import(Date date_import) {
		this.date_import = date_import;
	}

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Map<String, String> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(Map<String, String> metadatas) {
		this.metadatas = metadatas;
	}

	public String getNom_examen() {
		return nom_examen;
	}

	public void setNom_examen(String nom_examen) {
		this.nom_examen = nom_examen;
	}

	public String getNom_usage() {
		return nom_usage;
	}

	public void setNom_usage(String nom_usage) {
		this.nom_usage = nom_usage;
	}
	public List<String> getTagsId() {
		return tagsId;
	}

	public void setTagsId(List<String> tagsId) {
		this.tagsId = tagsId;
	}

	public Map<String, String> getMetadataIds() {
		return metadataIds;
	}

	public void setMetadataIds(Map<String, String> metadataIds) {
		this.metadataIds = metadataIds;
	}

	public String getNom_user() {
		return nom_user;
	}

	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	
}
