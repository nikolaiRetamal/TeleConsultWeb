package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "examen_user")
public class ExamenUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private UUID id;
	@Column
	@Index
	private UUID id_examen;
	@Column
	@Index
	private UUID id_user;
		
	public ExamenUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamenUser(UUID id, UUID id_examen, UUID id_user) {
		super();
		this.id = id;
		this.id_examen = id_examen;
		this.id_user = id_user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
	}

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}
	
}
