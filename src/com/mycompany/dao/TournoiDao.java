package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Tournoi;

public interface TournoiDao {
	void ajouter(Tournoi tournoi);
	List<Tournoi> lister();
	void modifier(Tournoi tournoi);
	void supprimer(int id);
	Tournoi lecture(int id);
	List<Tournoi> rechercher(String chaine);
}
