package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.beans.User;

public class UserDaoImpl {
	private DaoFactory daoFactory;

    public UserDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
       
    }
    
    
	public  User isValidLogin( String login, String password ) {
		 Connection connexion = null;
	     PreparedStatement statement = null;
	     password=HashClass.sha1(password);
	             
		try{
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM connexion WHERE login=? AND password=?";
			statement  = connexion.prepareStatement( strSql );
				statement.setString( 1, login );
				statement.setString( 2, password );
				
				
					ResultSet rs= statement.executeQuery();
					//si il n'y a pas de login ni de password correspondant dans la base, rs.next() sera false car vide donc on entre dans le else		
					if ( rs.next() ) {
						//on encapsule cet utilisateur dans java pour pouvoir utiliser les donn�es
						return new User(
								rs.getInt( "id" ),
								rs.getString( "login" ),
								rs.getString( "password" ),
								rs.getInt( "profil" )
						);
					} else {
						return null;
					}
				
			}
		 catch ( Exception exception ) {
			throw new RuntimeException( exception );
		}
	}

}
