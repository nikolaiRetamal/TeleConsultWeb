package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "dicom_user") 
public class DicomUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private UUID id;
	@Column
	private UUID id_dicom;
	@Column
	private UUID id_user;
	
	public DicomUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DicomUser(UUID id, UUID id_dicom, UUID id_user) {
		super();
		this.id = id;
		this.id_dicom = id_dicom;
		this.id_user = id_user;
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

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}
		
}
