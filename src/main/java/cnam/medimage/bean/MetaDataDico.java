package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


public class MetaDataDico implements Serializable{
	
	private static final long serialVersionUID = 3L;

	private String id;
	

	private String keyword;

	private String vr;

	private String nom;
	
	
	
	public MetaDataDico() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MetaDataDico(String id, String keyword, String vr, String nom) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.vr = vr;
		this.nom = nom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getVr() {
		return vr;
	}

	public void setVr(String vr) {
		this.vr = vr;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
}
