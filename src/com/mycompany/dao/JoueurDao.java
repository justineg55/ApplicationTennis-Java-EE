package com.mycompany.dao;

import java.util.List;

import com.mycompany.beans.Joueur;

public interface JoueurDao {
	void ajouter(Joueur joueur);
	List<Joueur> lister();
	void modifier(Joueur joueur);
	void supprimer(Long id);
	Joueur lecture(Long id);
	List<Joueur> rechercher(String chaine);

}
