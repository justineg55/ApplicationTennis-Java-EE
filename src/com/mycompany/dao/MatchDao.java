package com.mycompany.dao;

import java.sql.SQLException;
import java.util.List;

import com.mycompany.Uio.MatchUio;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;

public interface MatchDao {
	String getTournoiNom(int idMatch) throws SQLException;
	int getanneeEpreuve(int idMatch) throws SQLException;
	String getType(int idMatch) throws SQLException;
	String getFinaliste(int idMatch) throws SQLException;
	String getVainqueur(int idMatch) throws SQLException;
	String getScore(int idMatch) throws SQLException;
	void ajouter(Match match);
	List<Match> lister();
	void modifier(Match match);
	void supprimer(int id);
	Match lecture(int id);
	List<Match> rechercher(String chaine);
}

