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

import org.easycassandra.Index;


@Entity(name = "dicom") 
public class Dicom implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id_dicom;
	@Column
	private UUID id_user;
	@Index
	@Column
	private UUID id_examen;
	@Index
	@Column
	private UUID id_usage;
	@Column
	private String nom_examen;
	@Column
	private String nom_usage;
	@Column(name = "public")
	private Boolean publique;
	@Column
	private Date date_import;
	@Column
	private String file_path;
	@Index
	@Column
	private String nom;
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
	
	public Dicom() {
		this.tags = new LinkedList<>();
		this.tagsId = new LinkedList<>();
		this.metadatas = new HashMap<>();
		this.metadataIds = new HashMap<>();
	}

	public UUID getId_dicom() {
		return id_dicom;
	}

	public void setId_dicom(UUID id_dicom) {
		this.id_dicom = id_dicom;
	}

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}

	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
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

	public Boolean getPublique() {
		return publique;
	}

	public void setPublique(Boolean publique) {
		this.publique = publique;
	}

	public Date getDate_import() {
		return date_import;
	}

	public void setDate_import(Date date_import) {
		this.date_import = date_import;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public Map<String, String> getMetadataIds() {
		return metadataIds;
	}

	public void setMetadataIds(Map<String, String> metadataIds) {
		this.metadataIds = metadataIds;
	}

	public List<String> getTagsId() {
		return tagsId;
	}

	public void setTagsId(List<String> tagsId) {
		this.tagsId = tagsId;
	}

	public UUID getId_usage() {
		return id_usage;
	}

	public void setId_usage(UUID id_usage) {
		this.id_usage = id_usage;
	}

	
}