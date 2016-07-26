
package cnam.medimage.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "user") 
public class User implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id_user;
	@Index
	@Column
	private String nom;
	@Column
	private String prenom;
	@Column
	private String mail;
	@Column
	private String date_naissance;
	@Column
	private Date date_creation;
	
	public User(){}
	
	public User(UUID id_user, String nom, String prenom, String mail,
			String date_naissance, Date date_creation) {
		super();
		this.id_user = id_user;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.date_naissance = date_naissance;
		this.date_creation = date_creation;
	}

	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getDate_naissance() {
		return date_naissance;
	}


	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}


	public Date getDate_creation() {
		return date_creation;
	}


	public void setDate_creation(Date date_creation) {
		this.date_creation = date_creation;
	}

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}
	
}

