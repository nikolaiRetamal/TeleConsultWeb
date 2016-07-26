package cnam.medimage.bean;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.easycassandra.Index;

@Entity(name = "usage_user")
public class UsageUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private UUID id;
	@Column
	private UUID id_usage;
	@Column
	@Index
	private UUID id_user;
		
	public UsageUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsageUser(UUID id, UUID id_usage, UUID id_user) {
		super();
		this.id = id;
		this.id_usage = id_usage;
		this.id_user = id_user;
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

	public UUID getId_user() {
		return id_user;
	}

	public void setId_user(UUID id_user) {
		this.id_user = id_user;
	}
	
}
