package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "examen_dicom") 
public class ExamenDicom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private UUID id;
	@Index
	@Column
	private UUID id_dicom;
	@Index
	@Column
	private UUID id_examen;
	
	
	public ExamenDicom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamenDicom(UUID id, UUID id_dicom, UUID id_examen) {
		super();
		this.id = id;
		this.id_dicom = id_dicom;
		this.id_examen = id_examen;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getId_dicom() {
		return id_dicom;
	}

	public void setId_dicom(UUID id_dicom) {
		this.id_dicom = id_dicom;
	}

	public UUID getId_examen() {
		return id_examen;
	}

	public void setId_examen(UUID id_examen) {
		this.id_examen = id_examen;
	}
	
}
