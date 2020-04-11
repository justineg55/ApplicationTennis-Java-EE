package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.beans.Score;
import com.mycompany.beans.Tournoi;

public class ScoreDaoImpl implements ScoreDao{
	private DaoFactory daoFactory;

	public ScoreDaoImpl(DaoFactory daoFactory) {

		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Score score) {
		Connection connexion = null;
		PreparedStatement statement = null;

		try {
			connexion = daoFactory.getConnection();
			// requete pour ajouter : insert into nomdelatable (nomcolonne,nomcolonne...) values (?,?,?), ? : parametres à remplacer avec un setType(numeroParam,objet.getType)
			String strSql = "";
			
			//en focntion du nombre de sets renseignés on va faire une requete différente car on ne peut pas insert avec des getSet avec la valeur null
			//on attribue donc notre variable strSql en fonction de la requete voulue
			if(score.getSet3()==null) {
				strSql="INSERT INTO score_vainqueur(id_match,set_1,set_2) values (?,?,?)";
			}else if(score.getSet4()==null) {
				strSql="INSERT INTO score_vainqueur(id_match,set_1,set_2,set_3) values (?,?,?,?)";
			}else if(score.getSet5()==null){
				strSql="INSERT INTO score_vainqueur(id_match,set_1,set_2,set_3,set_4) values (?,?,?,?,?)";
			}else {
				strSql = "INSERT INTO score_vainqueur(id_match,set_1,set_2,set_3,set_4,set_5) values (?,?,?,?,?,?)";
			}
			
			//on effectue notre demande de reqûete avec la varibale strSql attribuée avec la bonne requete en fonction des if précédents
			statement = connexion.prepareStatement(strSql);

			//ce sont les valeurs qui sont obligatoirement renseignées dans la bdd: 
			statement.setInt(1, score.getIdMatch());
			statement.setInt(2,score.getSet1());
			statement.setInt(3, score.getSet2());
			
			//en focntion du nb de valeurs présentes dans notre requête
			if(!(score.getSet3()==null)) 
				statement.setInt(4, score.getSet3());
			if(!(score.getSet4()==null)) 
				statement.setInt(5, score.getSet4());
			if(!(score.getSet5()==null))
				statement.setInt(6, score.getSet5());
			
			// pour un insert il faut faire un .execute
			statement.execute();

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		
	}

	@Override
	public void modifier(Score score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void supprimer(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Score lecture(int id) {
		Connection connexion = null;
		PreparedStatement statement = null;
		Score score=new Score();

		try {
			connexion = daoFactory.getConnection();
			
			statement = connexion.prepareStatement("SELECT * FROM score_vainqueur WHERE ID_MATCH=?");
			
			statement.setInt(1, id);

			// il s'agit d'un select donc on fait un executeQuery()
			ResultSet rs = statement.executeQuery();
			// on fait un if car on r�cup�re une seule ligne car on fait une requete par l'id qui est unique
			if (rs.next()) {
				
				//les valeurs de set1 et set2 ne peuvent pas être nulles donc on récupère direct un int de la base
				score.setSet1(rs.getInt("set_1"));
				score.setSet2(rs.getInt("set_2"));
				
				//à partir de set3, on va récupérer les valeurs en String car elles peuvent être nulles (car rs.getInt récupère 0 d'une valeur inscrite nulle dans la base) donc on passe par rs.getString
				//à partir du set3 on chack si sa valeur est null dans la base, si la valeur de set3 est nulle dans la base, set4 et set5 forcément aussi
				//attention quand la valeur à gauche est nulle il faut mettre == et non .equals car sinon ca fera un nullpointerException
				if(rs.getString("set_3")==null) {
					score.setSet3(null);
					score.setSet4(null);
					score.setSet5(null);
				} else if(rs.getString("set_4")==null){
					score.setSet3(rs.getInt("set_3"));
					score.setSet4(null);
					score.setSet5(null);
				}else if(rs.getString("set_5")==null){
					score.setSet3(rs.getInt("set_3"));
					score.setSet4(rs.getInt("set_4"));
					score.setSet5(null);
				}else {
					score.setSet3(rs.getInt("set_3"));
					score.setSet4(rs.getInt("set_4"));
					score.setSet5(rs.getInt("set_5"));
				}
				System.out.println("set3 "+ score.getSet3()+"set4"+score.getSet4()+"set5"+score.getSet5());
				
				
			}else {
				return null;
			}
			
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return score;
	}

}
