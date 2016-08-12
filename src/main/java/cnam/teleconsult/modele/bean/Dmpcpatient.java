package cnam.teleconsult.modele.bean;
// Generated 12 ao�t 2016 14:40:39 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Dmpcpatient generated by hbm2java
 */
@Entity
@Table(name = "dmpcpatient", catalog = "teleconsult")
public class Dmpcpatient implements java.io.Serializable {

	private String ssnId;
	private Dmpcadresse dmpcadresse;
	private Dmpcpersonnelsante dmpcpersonnelsante;
	private String patientNom;
	private String patientPrenom;
	private String patientDatenaissance;
	private String patientNomepouse;
	private String patientDmpcsexe;
	private String patientInsc;
	private Integer patientOrdredenaissance;
	private String patientEmail;
	private String patientTelephone;
	private Set<Consultation> consultations = new HashSet<Consultation>(0);

	public Dmpcpatient() {
	}

	public Dmpcpatient(String ssnId, Dmpcadresse dmpcadresse, Dmpcpersonnelsante dmpcpersonnelsante, String patientNom,
			String patientPrenom, String patientDatenaissance) {
		this.ssnId = ssnId;
		this.dmpcadresse = dmpcadresse;
		this.dmpcpersonnelsante = dmpcpersonnelsante;
		this.patientNom = patientNom;
		this.patientPrenom = patientPrenom;
		this.patientDatenaissance = patientDatenaissance;
	}

	public Dmpcpatient(String ssnId, Dmpcadresse dmpcadresse, Dmpcpersonnelsante dmpcpersonnelsante, String patientNom,
			String patientPrenom, String patientDatenaissance, String patientNomepouse, String patientDmpcsexe,
			String patientInsc, Integer patientOrdredenaissance, String patientEmail, String patientTelephone,
			Set<Consultation> consultations) {
		this.ssnId = ssnId;
		this.dmpcadresse = dmpcadresse;
		this.dmpcpersonnelsante = dmpcpersonnelsante;
		this.patientNom = patientNom;
		this.patientPrenom = patientPrenom;
		this.patientDatenaissance = patientDatenaissance;
		this.patientNomepouse = patientNomepouse;
		this.patientDmpcsexe = patientDmpcsexe;
		this.patientInsc = patientInsc;
		this.patientOrdredenaissance = patientOrdredenaissance;
		this.patientEmail = patientEmail;
		this.patientTelephone = patientTelephone;
		this.consultations = consultations;
	}

	@Id

	@Column(name = "SSN_ID", unique = true, nullable = false, length = 15)
	public String getSsnId() {
		return this.ssnId;
	}

	public void setSsnId(String ssnId) {
		this.ssnId = ssnId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADRESSE_ID", nullable = false)
	public Dmpcadresse getDmpcadresse() {
		return this.dmpcadresse;
	}

	public void setDmpcadresse(Dmpcadresse dmpcadresse) {
		this.dmpcadresse = dmpcadresse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSONNELSANTE_ID", nullable = false)
	public Dmpcpersonnelsante getDmpcpersonnelsante() {
		return this.dmpcpersonnelsante;
	}

	public void setDmpcpersonnelsante(Dmpcpersonnelsante dmpcpersonnelsante) {
		this.dmpcpersonnelsante = dmpcpersonnelsante;
	}

	@Column(name = "PATIENT_NOM", nullable = false, length = 20)
	public String getPatientNom() {
		return this.patientNom;
	}

	public void setPatientNom(String patientNom) {
		this.patientNom = patientNom;
	}

	@Column(name = "PATIENT_PRENOM", nullable = false, length = 20)
	public String getPatientPrenom() {
		return this.patientPrenom;
	}

	public void setPatientPrenom(String patientPrenom) {
		this.patientPrenom = patientPrenom;
	}

	@Column(name = "PATIENT_DATENAISSANCE", nullable = false, length = 20)
	public String getPatientDatenaissance() {
		return this.patientDatenaissance;
	}

	public void setPatientDatenaissance(String patientDatenaissance) {
		this.patientDatenaissance = patientDatenaissance;
	}

	@Column(name = "PATIENT_NOMEPOUSE", length = 20)
	public String getPatientNomepouse() {
		return this.patientNomepouse;
	}

	public void setPatientNomepouse(String patientNomepouse) {
		this.patientNomepouse = patientNomepouse;
	}

	@Column(name = "PATIENT_DMPCSEXE", length = 1)
	public String getPatientDmpcsexe() {
		return this.patientDmpcsexe;
	}

	public void setPatientDmpcsexe(String patientDmpcsexe) {
		this.patientDmpcsexe = patientDmpcsexe;
	}

	@Column(name = "PATIENT_INSC", length = 20)
	public String getPatientInsc() {
		return this.patientInsc;
	}

	public void setPatientInsc(String patientInsc) {
		this.patientInsc = patientInsc;
	}

	@Column(name = "PATIENT_ORDREDENAISSANCE")
	public Integer getPatientOrdredenaissance() {
		return this.patientOrdredenaissance;
	}

	public void setPatientOrdredenaissance(Integer patientOrdredenaissance) {
		this.patientOrdredenaissance = patientOrdredenaissance;
	}

	@Column(name = "PATIENT_EMAIL", length = 200)
	public String getPatientEmail() {
		return this.patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	@Column(name = "PATIENT_TELEPHONE", length = 20)
	public String getPatientTelephone() {
		return this.patientTelephone;
	}

	public void setPatientTelephone(String patientTelephone) {
		this.patientTelephone = patientTelephone;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dmpcpatient")
	public Set<Consultation> getConsultations() {
		return this.consultations;
	}

	public void setConsultations(Set<Consultation> consultations) {
		this.consultations = consultations;
	}

}
