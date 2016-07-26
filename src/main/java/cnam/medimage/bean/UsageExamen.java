package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "usage_examen") 
public class UsageExamen implements Serializable{

	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id;
	@Index
	@Column
	private UUID id_usage;
	@Index
	@Column
	private UUID id_examen;
	
	public UsageExamen() {
		super();
	}
	
	public UsageExamen(UUID id, UUID id_usage, UUID id_examen) {
		super();
		this.id = id;
		this.id_usage = id_usage;
		this.id_examen = id_examen;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId_usage() {
		return id_usage;
	}

	public void setId_usage(UUID id_usage) {
		this.id_usage = id_usage;
	}

	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
	}

	
}
