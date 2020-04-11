package com.mycompany.beans;

public class Joueur {
	private int id;
	private String nom;
	private String prenom;
	private String sexe;
	
	public Joueur(int id, String nom, String prenom, String sexe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
	}
	
	public Joueur() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	
	public void setNom(String nom) {
		//code pour limiter le nombre de caractères
//		if(nom.length()>20) {
//			throw new BeanException("Le nom est trop grand (20 caractères maximum");
//		}
//		else 
//			this.nom=nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		//code pour limiter le nombre de caractères
//		if (prenom.length() > 20) {
//            throw new BeanException("Le prenom est trop grand ! (20 caractères maximum)");
//        }
//        else
//        this.prenom = prenom;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	

}
