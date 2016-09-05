package cnam.teleconsult.modele.bean;
// Generated 3 sept. 2016 15:21:31 by Hibernate Tools 4.3.1.Final

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Consultation generated by hbm2java
 */
@Entity
@Table(name = "consultation", catalog = "teleconsult")
public class Consultation implements java.io.Serializable {

	private Integer consultationId;
	private Dmpcpatient dmpcpatient;
	private Dmpcpersonnelsante dmpcpersonnelsante;
	private Date consultationDate;
	private String traitement;
	private String historique;
	private Set<Resultat> resultats = new HashSet<Resultat>(0);
	private Set<Avis> avises = new HashSet<Avis>(0);

	public Consultation() {
	}

	public Consultation(Dmpcpatient dmpcpatient, Date consultationDate, String traitement, String historique) {
		this.dmpcpatient = dmpcpatient;
		this.consultationDate = consultationDate;
		this.traitement = traitement;
		this.historique = historique;
	}

	public Consultation(Dmpcpatient dmpcpatient, Dmpcpersonnelsante dmpcpersonnelsante, Date consultationDate,
			String traitement, String historique, Set<Resultat> resultats, Set<Avis> avises) {
		this.dmpcpatient = dmpcpatient;
		this.dmpcpersonnelsante = dmpcpersonnelsante;
		this.consultationDate = consultationDate;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SSN_ID", nullable = false)
	public Dmpcpatient getDmpcpatient() {
		return this.dmpcpatient;
	}

	public void setDmpcpatient(Dmpcpatient dmpcpatient) {
		this.dmpcpatient = dmpcpatient;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERSONNELSANTE_ID")
	public Dmpcpersonnelsante getDmpcpersonnelsante() {
		return this.dmpcpersonnelsante;
	}

	public void setDmpcpersonnelsante(Dmpcpersonnelsante dmpcpersonnelsante) {
		this.dmpcpersonnelsante = dmpcpersonnelsante;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONSULTATION_DATE", nullable = false, length = 10)
	public Date getConsultationDate() {
		return this.consultationDate;
	}

	public void setConsultationDate(Date consultationDate) {
		this.consultationDate = consultationDate;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "consultation")
	@OrderBy("DMPCDOCUMENT_ID ASC")
	public Set<Resultat> getResultats() {
		return this.resultats;
	}

	public void setResultats(Set<Resultat> resultats) {
		this.resultats = resultats;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "consultation")
	public Set<Avis> getAvises() {
		return this.avises;
	}

	public void setAvises(Set<Avis> avises) {
		this.avises = avises;
	}

	
	

    //Fonctions de surchage afin de clarifier les m�thodes automatiquement g�n�r�es
	
	
	/**
	 * 
	 * Cette fonction d�tecte l'�tat de la consultation : 
	 *  - 0 : la consultation est "nouvelle" � dispatcher sur un r�f�rent
	 *  - 1 : la consultation est "en traitement", affect�e � un r�f�rent mais pas d�finitivement trait�e
	 *  - 2 : la consultation est "trait�e", elle a un avis d�finitif
	 * 
	 * @return
	 */
	@Transient
	public Integer getEtatConsultation() {
		
		Integer result = new Integer(0);
		

		Dmpcpersonnelsante referent = getDmpcpersonnelsante();
		//Si on trouve le personnel principal, la consultation est d�j� affect�e
		if(referent != null){
			//On est s�r que la consultation est au moins "en traitement"
			result = new Integer(1);
			
			Set<Avis> listeAvis = getAvises();
			
			if(listeAvis != null){					
				Iterator<Avis> it = listeAvis.iterator();					
				while(it.hasNext()){
					Avis avisEnCours = it.next();
					
					//Si on trouve un avis final dans les avis, la consultation est trait�e
					if(avisEnCours.getFlagFinal() != null && avisEnCours.getFlagFinal() > new Integer(0)){
						result = new Integer(2);	
					}
					
				}
				
			}
			

		}
		
		return result;
	}
	
	/**
	 * 
	 * Renvoie la date de consultation au format : "Day Month YYYY"
	 * 
	 * @return
	 */
	@Transient
	public String getConsultationDateLisible(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy",Locale.FRENCH);
		String dateLisible = sdf.format(getConsultationDate()); 
		
		return dateLisible;
	}
	
	
}
