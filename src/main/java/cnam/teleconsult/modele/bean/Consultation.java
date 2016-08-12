package cnam.teleconsult.modele.bean;
// Generated 12 ao�t 2016 14:40:39 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Consultation generated by hbm2java
 */
@Entity
@Table(name = "consultation", catalog = "teleconsult")
public class Consultation implements java.io.Serializable {

	private Integer consultationId;
	private Dmpcpatient dmpcpatient;
	private String traitement;
	private String historique;
	private Set<Resultat> resultats = new HashSet<Resultat>(0);
	private Set<Avis> avises = new HashSet<Avis>(0);

	public Consultation() {
	}

	public Consultation(Dmpcpatient dmpcpatient, String traitement, String historique) {
		this.dmpcpatient = dmpcpatient;
		this.traitement = traitement;
		this.historique = historique;
	}

	public Consultation(Dmpcpatient dmpcpatient, String traitement, String historique, Set<Resultat> resultats,
			Set<Avis> avises) {
		this.dmpcpatient = dmpcpatient;
		this.traitement = traitement;
		this.historique = historique;
		this.resultats = resultats;
		this.avises = avises;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "CONSULTATION_ID", unique = true, nullable = false)
	public Integer getConsultationId() {
		return this.consultationId;
	}

	public void setConsultationId(Integer consultationId) {
		this.consultationId = consultationId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SSN_ID", nullable = false)
	public Dmpcpatient getDmpcpatient() {
		return this.dmpcpatient;
	}

	public void setDmpcpatient(Dmpcpatient dmpcpatient) {
		this.dmpcpatient = dmpcpatient;
	}

	@Column(name = "TRAITEMENT", nullable = false, length = 500)
	public String getTraitement() {
		return this.traitement;
	}

	public void setTraitement(String traitement) {
		this.traitement = traitement;
	}

	@Column(name = "HISTORIQUE", nullable = false, length = 500)
	public String getHistorique() {
		return this.historique;
	}

	public void setHistorique(String historique) {
		this.historique = historique;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consultation")
	public Set<Resultat> getResultats() {
		return this.resultats;
	}

	public void setResultats(Set<Resultat> resultats) {
		this.resultats = resultats;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consultation")
	public Set<Avis> getAvises() {
		return this.avises;
	}

	public void setAvises(Set<Avis> avises) {
		this.avises = avises;
	}

}