package com.mycompany.Uio;

//cette classe correspond à la facon dont on veut que les matchs apparaissent à l'écran : avec quels attributs
public class MatchUio {
	private int id;
	private String nomTournoi;
	private int annee;
	private String type;
	private String finaliste;
	private String vainqueur;
	private String score;
	
	public MatchUio(int id, String nomTournoi, int annee, String type, String finaliste, String vainqueur, String score) {
		super();
		this.id = id;
		this.nomTournoi = nomTournoi;
		this.annee = annee;
		this.type = type;
		this.finaliste = finaliste;
		this.vainqueur = vainqueur;
		this.score=score;
	}

	public MatchUio() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomTournoi() {
		return nomTournoi;
	}

	public void setNomTournoi(String nomTournoi) {
		this.nomTournoi = nomTournoi;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFinaliste() {
		return finaliste;
	}

	public void setFinaliste(String finaliste) {
		this.finaliste = finaliste;
	}

	public String getVainqueur() {
		return vainqueur;
	}

	public void setVainqueur(String vainqueur) {
		this.vainqueur = vainqueur;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}



}
