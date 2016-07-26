package cnam.medimage.bean;

public class ImportForm {

	private String nom_examen;
	private String nom_usage;
	private boolean publique;
	private String usageConnu;
	private User user;
	
	public String getNom_examen() {
		return nom_examen;
	}
	public void setNom_examen(String nom_examen) {
		this.nom_examen = nom_examen;
	}
	public String getNom_usage() {
		return nom_usage;
	}
	public void setNom_usage(String nom_usage) {
		this.nom_usage = nom_usage;
	}
	public boolean getPublique() {
		return publique;
	}
	public void setPublique(boolean publique) {
		this.publique = publique;
	}
	public String getUsageConnu() {
		return usageConnu;
	}
	public void setUsageConnu(String usageConnu) {
		this.usageConnu = usageConnu;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
