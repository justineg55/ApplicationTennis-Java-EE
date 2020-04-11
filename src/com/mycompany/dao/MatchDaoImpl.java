package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.Uio.MatchUio;
import com.mycompany.beans.Match;
import com.mycompany.beans.Tournoi;

//dans cette classe on va effectuer toutes nos requêtes permettant de récupérer les données des tables dont on a besoin
public class MatchDaoImpl implements MatchDao {
	private DaoFactory daoFactory;

	public MatchDaoImpl(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;
	}

	//permet de récupérer le nom du tournoi correspondant à un match
	public String getTournoiNom(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;
		String response;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "Select tournoi.NOM from match_tennis inner join epreuve on epreuve.ID = match_tennis.ID_EPREUVE inner join tournoi on epreuve.ID_TOURNOI = tournoi.ID where match_tennis .id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				response = rs.getString("nom");
			} else {
				response = null;
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}

		return response;

	}

	public int getanneeEpreuve(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select epreuve.ANNEE from match_tennis inner join epreuve on epreuve.ID = match_tennis.ID_EPREUVE where match_tennis .id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			return rs.next() ? rs.getInt("annee") : 0;

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}
	}

	public String getType(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select epreuve.TYPE_EPREUVE from match_tennis inner join epreuve on epreuve.ID = match_tennis.ID_EPREUVE where match_tennis.id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			return rs.next() ? rs.getString("type_epreuve") : null;

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}
	}

	public String getFinaliste(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select joueur.NOM from match_tennis inner join joueur on joueur.ID = match_tennis.ID_FINALISTE where match_tennis .id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			return rs.next() ? rs.getString("nom") : null;

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}
	}

	public String getVainqueur(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select joueur.NOM from match_tennis inner join joueur on joueur.ID = match_tennis.ID_VAINQUEUR where match_tennis .id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			return rs.next() ? rs.getString("nom") : null;

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}
	}

	public String getScore(int idMatch) throws SQLException {
		Connection connexion = null;
		PreparedStatement statement = null;

		String score;
		int set3Nb;
		int set4Nb;
		int set5Nb;
		String scoreSet3;
		String scoreSet4;
		String scoreSet5;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select score_vainqueur.SET_1, score_vainqueur.SET_2,score_vainqueur.SET_3,score_vainqueur.SET_4, score_vainqueur.SET_5 from match_tennis inner join score_vainqueur on score_vainqueur.ID_MATCH = match_tennis.ID where match_tennis .id=?";
			statement = connexion.prepareStatement(strSql);
			statement.setInt(1, idMatch);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int set1 = rs.getInt("set_1");
				int set2 = rs.getInt("set_2");
				String set3 = rs.getString("set_3");
				String set4 = rs.getString("set_4");
				String set5 = rs.getString("set_5");

				String scoreSet1 = getScoreSet(set1);
				String scoreSet2 = getScoreSet(set2);
				
				if (!(set3 == null)) {
					set3Nb = Integer.parseInt(set3);
					scoreSet3 = getScoreSet(set3Nb);
				} else {
					scoreSet3 = "";
				}
				
				if (!(set4 == null)) {
					set4Nb = Integer.parseInt(set4);
					scoreSet4 = getScoreSet(set4Nb);
				} else {
					scoreSet4 = "";

				}
				if (!(set5 == null)) {
					set5Nb = Integer.parseInt(set5);
					scoreSet5 = getScoreSet(set5Nb);
				} else {
					scoreSet5 = "";
				}
				score = scoreSet1 +" "+ scoreSet2 +" "+ scoreSet3 +" "+ scoreSet4 +" "+ scoreSet5;

			} else
				return null;

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		} finally {
			if (statement != null)
				statement.close();
			if (connexion != null)
				connexion.close();
		}

		return score;

	}

	@Override
	public void ajouter(Match match) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			// requete pour ajouter : insert into nomdelatable (nomcolonne,nomcolonne...) values (?,?,?), ? : parametres à remplacer avec un setType(numeroParam,objet.getType)
			String strSql = "INSERT INTO match_tennis (id_epreuve,id_vainqueur,id_finaliste) values (?,?,?)";
			
			//statement.RETURN_GENERATED_KEYS va permettre de récupérer l'id autogénéré de la nouvelle entrée de l'objet dans la table
			statement = connexion.prepareStatement(strSql,statement.RETURN_GENERATED_KEYS);

			
			statement.setInt(1, match.getId_Epreuve());
			statement.setInt(2,match.getId_Vainqueur());
			statement.setInt(3, match.getId_Finaliste());
			

			// pour un insert il faut faire un .execute
			statement.execute();
			
			//try pour récupérer l'id autogénéré par l'insert, id=generatedKeys.getInt(1) : on le set à l'objet epreuve(setId) pour pouvoir le récupérer dans notre servlet
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					match.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("Creating epreuve failed, no ID obtained.");
				}
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	

	}

	//méthode qui permet de récupérer le contenu de la table match_tennis
	@Override
	public List<Match> lister() {

		List<Match> matchs = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select * from match_tennis";
			statement = connexion.prepareStatement(strSql);

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Match match = new Match(rs.getInt("id"), rs.getInt("id_epreuve"), rs.getInt("id_vainqueur"),
						rs.getInt("id_finaliste"));
				matchs.add(match);

			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return matchs;
	}

	@Override
	public void modifier(Match match) {
		Connection connexion=null;
		PreparedStatement statement=null;
		
		try {
			connexion=daoFactory.getConnection();
			String strSql="Update match_tennis SET id_epreuve=?,id_vainqueur=?,id_finaliste=? where id=?";
			statement=connexion.prepareStatement(strSql);
			statement.setInt(1, match.getId_Epreuve());
			statement.setInt(2, match.getId_Vainqueur());
			statement.setInt(3, match.getId_Finaliste());
			statement.setInt(4, match.getId());
			
			statement.executeUpdate();
			
		}catch (Exception exception){
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void supprimer(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Match lecture(int id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			
			statement = connexion.prepareStatement("SELECT * FROM MATCH_TENNIS WHERE ID=?");
			
			statement.setInt(1, id);

			// il s'agit d'un select donc on fait un executeQuery()
			ResultSet rs = statement.executeQuery();
			// on fait un if car on r�cup�re une seule ligne car on fait une requete par l'id qui est unique
			if (rs.next()) {

				return new Match(
						// on r�cupere les donn�es dans les diff�rentes colonnes de la table donc on met le nom des colonnes en parametre
						rs.getInt("id"), rs.getInt("id_epreuve"), rs.getInt("id_vainqueur"), rs.getInt("id_finaliste"));
			} else {
				return null;
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public List<Match> rechercher(String chaine) {
		// TODO Auto-generated method stub
		return null;
	}

	// méthode pour afficher le score :
	public String getScoreSet(int set) {
		
		if (set >= 0 && set < 5) {
			return "6-" + set;

		} else if (set >= 5) {
			return "7-" + set;

		} else if (set < 0 && set > -5) {
			return -set + "-6";

		} else if (set <= -5) {
			return -set + "-7 ";
		} else
			return null;
	}
}
