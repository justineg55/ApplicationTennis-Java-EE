package com.mycompany.dao;

import com.mycompany.beans.Score;

public interface ScoreDao {
	void ajouter(Score score);
	void modifier(Score score);
	void supprimer(int id);
	Score lecture(int id);

}
