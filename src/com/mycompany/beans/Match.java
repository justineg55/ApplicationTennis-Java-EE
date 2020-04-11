package com.mycompany.beans;

public class Match {
	//le bean correspond aux différentes colonnes de la table match_tennis
	private int id;
	private int id_Epreuve;
	private int id_Vainqueur;
	private int id_Finaliste;
	
	
	
	public Match() {
		super();
	}
	public Match(int id, int id_Epreuve, int id_Vainqueur, int id_Finaliste) {
		super();
		this.id = id;
		this.id_Epreuve = id_Epreuve;
		this.id_Vainqueur = id_Vainqueur;
		this.id_Finaliste = id_Finaliste;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_Epreuve() {
		return id_Epreuve;
	}
	public void setId_Epreuve(int id_Epreuve) {
		this.id_Epreuve = id_Epreuve;
	}
	public int getId_Vainqueur() {
		return id_Vainqueur;
	}
	public void setId_Vainqueur(int id_Vainqueur) {
		this.id_Vainqueur = id_Vainqueur;
	}
	public int getId_Finaliste() {
		return id_Finaliste;
	}
	public void setId_Finaliste(int id_Finaliste) {
		this.id_Finaliste = id_Finaliste;
	}
	
	
}
