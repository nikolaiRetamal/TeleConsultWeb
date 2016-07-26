package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "tag_examen") 
public class TagExamen implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id;
	@Index
	@Column
	private String nom;
	@Index
	@Column
	private UUID id_examen;
	
	public TagExamen() {
		super();
	}

	public TagExamen(UUID id, String nom, UUID id_examen) {
		super();
		this.id = id;
		this.nom = nom;
		this.id_examen = id_examen;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
	}

	
	
	
}
