package cnam.medimage.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;


@Entity(name = "tagmesh") 
public class TagMesh implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	@Id
	@Index
	@Column(name = "id_tag")
	private String idTag;

	@Index
	@Column(name = "nom")
	private String nom;

	
	public String getIdTag() {
		return idTag;
	}
	public void setIdTag(String idTag) {
		this.idTag = idTag;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	@ElementCollection
	@Column(name = "synonymes")
	private List<String> synonymes;

	@ElementCollection
	@Column(name = "categories")
	private List<String> categories;
	
	public TagMesh(String idTag, String nom) {
		this.synonymes = new ArrayList<String>();
		this.categories = new ArrayList<String>();
	}

	public List<String> getSynonymes() {
		return synonymes;
	}

	public void setSynonymes(List<String> synonymes) {
		this.synonymes = synonymes;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
