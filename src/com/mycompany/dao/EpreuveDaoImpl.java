package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.beans.Epreuve;
import com.mycompany.beans.Match;

public class EpreuveDaoImpl implements EpreuveDao {
	private DaoFactory daoFactory;

	public EpreuveDaoImpl(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Epreuve epreuve) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			// requete pour ajouter : insert into nomdelatable (nomcolonne,nomcolonne...)
			// values (?,?,?), ? : parametres à remplacer avec un
			// setType(numeroParam,objet.getType)
			String strSql = "INSERT INTO epreuve(annee,type_epreuve,id_tournoi) values (?,?,?)";
			//le return_GEnerated_keys est là pour pouvoir récupérer l'id de l'epreuve ajouté
			statement = connexion.prepareStatement(strSql, statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, epreuve.getAnnee());
			statement.setString(2, epreuve.getType());
			statement.setInt(3, epreuve.getIdTournoi());

			// pour un insert il faut faire un .execute
			statement.execute();

			//try pour récupérer l'id autogénéré par l'insert, id=generatedKeys.getInt(1) : on le set à l'objet epreuve(setId) pour pouvoir le récupérer dans notre servlet
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					epreuve.setId(generatedKeys.getInt(1));
					System.out.println(epreuve.getId());
				} else {
					throw new SQLException("Creating epreuve failed, no ID obtained.");
				}
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void modifier(Epreuve epreuve) {
		Connection connexion=null;
		PreparedStatement statement=null;
		
		try {
			connexion=daoFactory.getConnection();
			String strSql="Update epreuve SET annee=?,type_epreuve=?,id_tournoi=? where id=?";
			statement=connexion.prepareStatement(strSql);
			statement.setInt(1, epreuve.getAnnee());
			statement.setString(2, epreuve.getType());
			statement.setInt(3, epreuve.getIdTournoi());
			statement.setInt(4, epreuve.getId());
			
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
	public Epreuve lecture(int id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			
			statement = connexion.prepareStatement("SELECT * FROM EPREUVE WHERE ID=?");
			
			statement.setInt(1, id);

			// il s'agit d'un select donc on fait un executeQuery()
			ResultSet rs = statement.executeQuery();
			// on fait un if car on r�cup�re une seule ligne car on fait une requete par l'id qui est unique
			if (rs.next()) {

				return new Epreuve(
						// on r�cupere les donn�es dans les diff�rentes colonnes de la table donc on met le nom des colonnes en parametre
						rs.getInt("id"), rs.getInt("annee"), rs.getString("type_epreuve"), rs.getInt("id_tournoi"));
			} else {
				return null;
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
