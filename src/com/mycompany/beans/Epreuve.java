package com.mycompany.beans;

public class Epreuve {
	private int id;
	private int annee;
	private String type;
	private int idTournoi;
	
	
	public Epreuve() {
		super();
	}
	public Epreuve(int id, int annee, String type, int idTournoi) {
		super();
		this.id = id;
		this.annee = annee;
		this.type = type;
		this.idTournoi = idTournoi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getIdTournoi() {
		return idTournoi;
	}
	public void setIdTournoi(int idTournoi) {
		this.idTournoi = idTournoi;
	}
	
	

}
