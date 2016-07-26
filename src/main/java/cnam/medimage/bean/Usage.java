
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

@Entity(name = "usage") 
public class Usage implements Serializable{

	private static final long serialVersionUID = 3L;
	
	@Id
	@Column
	private UUID id_usage;
	@Column
	@Index
	private Date date_creat;
	@Column
	private String nom;
	@ElementCollection
	@Column
	private List<UUID> dicoms;
	@ElementCollection
	@Column
	private Map<UUID, String> users;
	
	public Usage() {
		this.dicoms = new LinkedList<>();
		this.users = new HashMap<>();
	}

	public UUID getId_usage() {
		return id_usage;
	}

	public void setId_usage(UUID id_usage) {
		this.id_usage = id_usage;
	}

	public Date getDate_creat() {
		return date_creat;
	}

	public void setDate_creat(Date date_creat) {
		this.date_creat = date_creat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<UUID> getDicoms() {
		return dicoms;
	}

	public void setDicoms(List<UUID> dicoms) {
		this.dicoms = dicoms;
	}

	public Map<UUID, String> getUsers() {
		return users;
	}

	public void setUsers(Map<UUID, String> users) {
		this.users = users;
	}
}