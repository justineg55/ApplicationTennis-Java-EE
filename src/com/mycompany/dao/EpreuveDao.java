package com.mycompany.dao;


import com.mycompany.beans.Epreuve;

public interface EpreuveDao {
	void ajouter(Epreuve epreuve);
	void modifier(Epreuve epreuve);
	void supprimer(int id);
	Epreuve lecture(int id);

}
