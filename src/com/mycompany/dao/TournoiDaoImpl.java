package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Tournoi;

public class TournoiDaoImpl implements TournoiDao {
	private DaoFactory daoFactory;

	public TournoiDaoImpl(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Tournoi tournoi) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			String strSql = "Insert into tournoi (nom,code) Values (?,?)";
			statement = connexion.prepareStatement(strSql);
			statement.setString(1, tournoi.getNom());
			statement.setString(2, tournoi.getCode());
			
			statement.execute();

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public List<Tournoi> lister() {
		Connection connexion = null;
		PreparedStatement statement = null;

		List<Tournoi> tournois = new ArrayList<>();

		try {
			connexion = daoFactory.getConnection();
			String strSql = "select * from tournoi";
			statement = connexion.prepareStatement(strSql);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Tournoi tournoi = new Tournoi(rs.getInt("id"), rs.getString("nom"), rs.getString("code"));
				tournois.add(tournoi);
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return tournois;
	}

	@Override
	public void modifier(Tournoi tournoi) {
		Connection connexion=null;
		PreparedStatement statement=null;
		
		try {
			connexion=daoFactory.getConnection();
			String strSql="Update tournoi SET nom=?,code=? where id=?";
			statement=connexion.prepareStatement(strSql);
			statement.setString(1, tournoi.getNom());
			statement.setString(2, tournoi.getCode());
			statement.setInt(3, tournoi.getId());
			
			statement.executeUpdate();
			
		}catch (Exception exception){
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void supprimer(int id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {

			connexion = daoFactory.getConnection();
			// requete pour supprimer : DELETE FROM NOMDELATABLE WHERE ID=?, on supprime par l'id
			statement = connexion.prepareStatement("DELETE FROM TOURNOI WHERE ID=?");
			statement.setInt(1, id);

			// quand il s'agit de supprimer, c'est aussi un executeUpdate()
			statement.executeUpdate();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	//cette méthode nous permet de récupérer les valeurs d'un objet dans la bdd en rentrant l'id
	@Override
	public Tournoi lecture(int id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			
			statement = connexion.prepareStatement("SELECT * FROM TOURNOI WHERE ID=?");
			
			statement.setInt(1, id);

			// il s'agit d'un select donc on fait un executeQuery()
			ResultSet rs = statement.executeQuery();
			// on fait un if car on r�cup�re une seule ligne car on fait une requete par l'id qui est unique
			if (rs.next()) {

				return new Tournoi(
						// on r�cupere les donn�es dans les diff�rentes colonnes de la table donc on met le nom des colonnes en parametre
						rs.getInt("id"), rs.getString("nom"), rs.getString("code"));
			} else {
				return null;
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	// méthode qui permet de faire une recherche par le nom et prénom dans la barre Search
	@Override
	public List<Tournoi> rechercher(String chaine) {
			List<Tournoi> tournois = new ArrayList<>();
			Connection connexion = null;
			PreparedStatement statement = null;

			try {
				connexion = daoFactory.getConnection();
				
				//le LIKE permet de faire une recherche par caractere
				String strSql="SELECT * FROM tournoi WHERE NOM LIKE ? OR CODE LIKE ?";
				statement=connexion.prepareStatement(strSql);
				
				//si on met un pourcentage avant et apres ceka veut dire qu'on cherche les caracteres sur toute la chaine, si on met juste devant, cela veut dire commencer par, si on met le % derriere cela veut dire finit par
				statement.setString(1, "%"+ chaine+ "%");
				statement.setString(2, "%"+ chaine+ "%");

				ResultSet rs = statement.executeQuery();

				while (rs.next()) {
					Tournoi tournoi = new Tournoi(rs.getInt("id"), rs.getString("nom"), rs.getString("code"));
					tournois.add(tournoi);
					
				}
			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
			return tournois;

		}
	}


