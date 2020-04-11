package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Joueur;

public class JoueurDaoImpl implements JoueurDao {

	private DaoFactory daoFactory;

	public JoueurDaoImpl(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Joueur joueur) {

		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			// requete pour ajouter : insert into nomdelatable (nomcolonne,nomcolonne...) values (?,?,?), ? : parametres à remplacer avec un setType(numeroParam,objet.getType)
			String strSql = "INSERT INTO joueur(nom,prenom,sexe) values (?,?,?)";
			statement = connexion.prepareStatement(strSql);

			// on remplace les ? par les diff�rents attributs du joueur � ajouter
			statement.setString(1, joueur.getNom());
			statement.setString(2, joueur.getPrenom());
			statement.setString(3, joueur.getSexe());

			// pour un insert il faut faire un .execute
			statement.execute();

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public List<Joueur> lister() {
		Connection connexion = null;
		PreparedStatement statement = null;
		// on cr�e la liste de joueurs qu'on va renvoyer
		List<Joueur> joueurs = new ArrayList<>();

		try {
			connexion = daoFactory.getConnection();
			// on fait une requete select * from nomdelatable pour s�lectionner l'ensemble
			// des �l�ments de la table
			String strSql = "SELECT * FROM joueur";
			statement = connexion.prepareStatement(strSql);
			// on r�cup�re le r�sultat de la requete dans la variable rs et on fait un
			// executeQuery(car on touche pas � la table, juste un select!!)
			ResultSet rs = statement.executeQuery();

			// on va parcourir tant qu'il y a des lignes dans la table le r�sultat de la
			// requete ligne par ligne
			while (rs.next()) {
				// on encapsule cet utilisateur dans java pour pouvoir utiliser les donn�es
				// on r�cupere les donn�es de chaque joueur=ligne de la bdd et on les sauvegarde
				// dans un objet joueur
				Joueur joueur = new Joueur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("sexe"));
				// chaque joueur r�cup�r� est ajout� � la liste
				joueurs.add(joueur);
			}

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		
		// on n'oublie pas de renvoyer la liste de joueurs
		return joueurs;

	}

	@Override
	public void modifier(Joueur joueur) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {

			connexion = daoFactory.getConnection();
			// quand il s'agit de modifier, la requete attendue est UPDATE NOMDELATABLE SET
			// NOMDELACOLONNE=?,.. WHERE ID=?, on update le joueur qui a l'id?
			statement = connexion.prepareStatement("UPDATE JOUEUR SET NOM=?,PRENOM=?, SEXE=? WHERE ID=?");

			// on r�cup�re les nouvelles donn�es qui remplacent les ? dans la requete pour
			// pourvoir mettre � jour le joueur
			statement.setString(1, joueur.getNom());
			statement.setString(2, joueur.getPrenom());
			statement.setString(3, joueur.getSexe());
			long idl = Long.valueOf(joueur.getId());
			statement.setLong(4, idl);

			// quand il s'agit d'un update on utilise executeUpdate()
			statement.executeUpdate();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	public void supprimer(Long id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {

			connexion = daoFactory.getConnection();
			// requete pour supprimer : DELETE FROM NOMDELATABLE WHERE ID=?, on supprime par
			// l'id
			statement = connexion.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");
			statement.setLong(1, id);

			// quand il s'agit de supprimer, c'est aussi un executeUpdate()
			statement.executeUpdate();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	@Override
	public Joueur lecture(Long id) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			// on s�lectionne la ligne par l'id dans la table joueur
			statement = connexion.prepareStatement("SELECT * FROM JOUEUR WHERE ID=?");
			// on remplace le ? dans la requete par l'id en faisant statement.set, le 1
			// correspond au 1er ?, parametre attendu dans la requete statement
			statement.setLong(1, id);

			// il s'agit d'un select donc on fait un executeQuery()
			ResultSet rs = statement.executeQuery();
			// on fait un if car on r�cup�re une seule ligne car on fait une requete par l'id qui est unique
			if (rs.next()) {

				return new Joueur(
						// on r�cupere les donn�es dans les diff�rentes colonnes de la table donc on met
						// le nom des colonnes en parametre des getInt
						rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("sexe"));
			} else {
				return null;
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}

	}

	
	// méthode qui permet de faire une recherche par le nom et prénom dans la barre Search
	@Override
	public List<Joueur> rechercher(String chaine) {
		List<Joueur> joueurs = new ArrayList<>();
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			
		//ne pas mettre directement le parametre dans la requete car ce n'est pas sécurisé
//			statement = connexion
//					.prepareStatement("SELECT * FROM joueur WHERE NOM OR PRENOM LIKE '%" + chaine + "'%'");
			
			//le LIKE permet de faire une recherche par caractere
			String strSql="SELECT * FROM joueur WHERE NOM LIKE ? OR PRENOM LIKE ?";
			statement=connexion.prepareStatement(strSql);
			
			//si on met un pourcentage avant et apres ceka veut dire qu'on cherche les caracteres sur toute la chaine, si on met juste devant, cela veut dire commencer par, si on met le % derriere cela veut dire finit par
			statement.setString(1, "%"+ chaine+ "%");
			statement.setString(2, "%"+ chaine+ "%");

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Joueur joueur = new Joueur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("sexe"));
				joueurs.add(joueur);
				
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return joueurs;

	}
}
