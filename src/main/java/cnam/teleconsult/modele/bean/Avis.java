package cnam.teleconsult.modele.bean;
// Generated 24 ao�t 2016 08:19:15 by Hibernate Tools 4.3.1.Final

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
 * Avis generated by hbm2java
 */
@Entity
@Table(name = "avis", catalog = "teleconsult")
public class Avis implements java.io.Serializable {

	private Integer dmpcsubmissionId;
	private Consultation consultation;
	private Dmpcpersonnelsante dmpcpersonnelsante;
	private String avis;
	private Integer flagFinal;
	private Set<Contributeur> contributeurs = new HashSet<Contributeur>(0);

	public Avis() {
	}

	public Avis(Consultation consultation, Dmpcpersonnelsante dmpcpersonnelsante, String avis) {
		this.consultation = consultation;
		this.dmpcpersonnelsante = dmpcpersonnelsante;
		this.avis = avis;
	}

	public Avis(Consultation consultation, Dmpcpersonnelsante dmpcpersonnelsante, String avis, Integer flagFinal,
			Set<Contributeur> contributeurs) {
		this.consultation = consultation;
		this.dmpcpersonnelsante = dmpcpersonnelsante;
		this.avis = avis;
		this.flagFinal = flagFinal;
		this.contributeurs = contributeurs;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "DMPCSUBMISSION_ID", unique = true, nullable = false)
	public Integer getDmpcsubmissionId() {
		return this.dmpcsubmissionId;
	}

	public void setDmpcsubmissionId(Integer dmpcsubmissionId) {
		this.dmpcsubmissionId = dmpcsubmissionId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONSULTATION_ID", nullable = false)
	public Consultation getConsultation() {
		return this.consultation;
	}

	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSONNELSANTE_ID", nullable = false)
	public Dmpcpersonnelsante getDmpcpersonnelsante() {
		return this.dmpcpersonnelsante;
	}

	public void setDmpcpersonnelsante(Dmpcpersonnelsante dmpcpersonnelsante) {
		this.dmpcpersonnelsante = dmpcpersonnelsante;
	}

	@Column(name = "AVIS", nullable = false, length = 500)
	public String getAvis() {
		return this.avis;
	}

	public void setAvis(String avis) {
		this.avis = avis;
	}

	@Column(name = "FLAG_FINAL")
	public Integer getFlagFinal() {
		return this.flagFinal;
	}

	public void setFlagFinal(Integer flagFinal) {
		this.flagFinal = flagFinal;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "avis")
	public Set<Contributeur> getContributeurs() {
		return this.contributeurs;
	}

	public void setContributeurs(Set<Contributeur> contributeurs) {
		this.contributeurs = contributeurs;
	}

}
