package com.mycompany.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import com.mycompany.Uio.MatchUio;
import com.mycompany.beans.Match;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.MatchDao;
import com.mycompany.dao.MatchDaoImpl;

public class MatchService {
	private MatchDao matchDao;

	public MatchService() {
		DaoFactory daoFactory=DaoFactory.getInstance();
		matchDao=new MatchDaoImpl(daoFactory);
	}
	
	//on a récupéré dans MatchdaoImpl toutes les données différentes qu'on a besoin pour un id donné
	//ici on va parcourir toutes les id de la table match_tennis pour récupérer les données de toutes les lignes de match tennis
	//sur chaque ligne de match_tennis (et donc chaque id) on effectue les requêtes pour récupérer tous les attributs de matchUIO et on consitue la liste de matchs UIO
	public List<MatchUio> getAllMacthsUio() throws SQLException{
		
		//on récupère toutes les entrées de la table match_tennis
		List<Match> matchs=matchDao.lister();
		//on initialise la liste matchUio qu'on va renvoyer à la fin quand elle sera complétée
		List<MatchUio> matchsUio=new ArrayList<MatchUio>();
		
		//on parcourt la table match_tennis
		for(Match match: matchs){
			//à chaque itération on récupère l'id=chaque ligne de la table match_tennis
			int idMatch=match.getId();
			//pour chaque itération, on enregistre une valeur pour chaque attribut de la classe MatchUio qui correspond à un match à afficher
			String tournoiNom=matchDao.getTournoiNom(idMatch);
			int annee=matchDao.getanneeEpreuve(idMatch);
			String type=matchDao.getType(idMatch);
			String finaliste=matchDao.getFinaliste(idMatch);
			String vainqueur=matchDao.getVainqueur(idMatch);
			String score=matchDao.getScore(idMatch);
			
			//pour chaque itération on crée une nouvelle instance de matchUio qu'on va à chaque itération ajouter à la liste de macthsUio
			MatchUio matchUio=new MatchUio();
			
			//on set les valeurs récupérées dans les attributs correspondant de la classe MatchUio 
			matchUio.setId(idMatch);
			matchUio.setNomTournoi(tournoiNom);
			matchUio.setAnnee(annee);
			matchUio.setType(type);
			matchUio.setFinaliste(finaliste);
			matchUio.setVainqueur(vainqueur);
			matchUio.setScore(score);
			
			//on ajoute chaque itération dans la liste matchsUio
			matchsUio.add(matchUio);
			
		}
		//on retourne la liste construite qu'on affichera à l'utilisateur après l'avoir récupéré dans la servlet et l'avoir set à la vue
		return matchsUio;
		
	}
	
	//on crée une autre méthode qui prend une liste en parametre pour transformer notre liste de matchs qu'on récupère aprés avoir utilisé la fonction recherche en liste de type match UIO pour afficher les matchs à l'utilisateur apres qu'il ait fait sa recherche
public List<MatchUio> getMacthsUiofromASpecificMatchsList(List <Match> matchs) throws SQLException{
		
		//on initialise la liste matchUio qu'on va renvoyer à la fin quand elle sera complétée
		List<MatchUio> matchsUio=new ArrayList<MatchUio>();
		
		//on parcourt la liste des matchs qu'on récupère en parametre pour la transformer en liste de type MatchUio
		for(Match match: matchs){
			//à chaque itération on récupère l'id=chaque ligne de la table match_tennis
			int idMatch=match.getId();
			//pour chaque itération, on enregistre une valeur pour chaque attribut de la classe MatchUio qui correspond à un match à afficher
			String tournoiNom=matchDao.getTournoiNom(idMatch);
			int annee=matchDao.getanneeEpreuve(idMatch);
			String type=matchDao.getType(idMatch);
			String finaliste=matchDao.getFinaliste(idMatch);
			String vainqueur=matchDao.getVainqueur(idMatch);
			String score=matchDao.getScore(idMatch);
			
			//pour chaque itération on crée une nouvelle instance de matchUio qu'on va à chaque itération ajouter à la liste de macthsUio
			MatchUio matchUio=new MatchUio();
			
			//on set les valeurs récupérées dans les attributs correspondant de la classe MatchUio 
			matchUio.setId(idMatch);
			matchUio.setNomTournoi(tournoiNom);
			matchUio.setAnnee(annee);
			matchUio.setType(type);
			matchUio.setFinaliste(finaliste);
			matchUio.setVainqueur(vainqueur);
			matchUio.setScore(score);
			
			//on ajoute chaque itération dans la liste matchsUio
			matchsUio.add(matchUio);
			
		}
		//on retourne la liste construite qu'on affichera à l'utilisateur après l'avoir récupéré dans la servlet et l'avoir set à la vue
		return matchsUio;
		
	}


}
