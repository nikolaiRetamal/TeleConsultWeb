package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "metadata")
public class MetaData implements Serializable{
	
	private static final long serialVersionUID = 3L;
	@Id
	@Column
	private UUID id_metadata;
	
	@Column
	private UUID id_dicom;
	@Id
	@Column
	private String key;
	@Id
	@Column
	private String value;
	
	
	public MetaData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MetaData(UUID id_metadata, UUID id_dicom, String key, String value) {
		super();
		this.id_metadata = id_metadata;
		this.id_dicom = id_dicom;
		this.key = key;
		this.value = value;
	}
	
	public UUID getId_dicom() {
		return id_dicom;
	}
	public void setId_dicom(UUID id_dicom) {
		this.id_dicom = id_dicom;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public UUID getId_metadata() {
		return id_metadata;
	}

	public void setId_metadata(UUID id_metadata) {
		this.id_metadata = id_metadata;
	}
		
}
