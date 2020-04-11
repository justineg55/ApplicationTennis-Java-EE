package com.mycompany.servlets.joueur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.beans.BeanException;
import com.mycompany.beans.Joueur;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class Modifierjoueur
 */
@WebServlet("/modifierjoueur")
public class Modifierjoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JoueurDao joueurDao;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Modifierjoueur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	DaoFactory daoFactory=DaoFactory.getInstance();    
    	//pour pouvoir acc�der � la m�thode lister
    	joueurDao=new JoueurDaoImpl(daoFactory);
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on récupère l'id pour savoir quel joueur veut être modifié, on met un input hidden dans le jsp
		String id=request.getParameter("idjoueur");
		long id1=Long.parseLong(id);
		//on compare les 2 values des boutons pour savoir sur lequel l'utilisateur a appuy�
		if(request.getParameter("action").equals("Modifier")) {
			//on r�cup�re les donn�es du joueur s�lectionn� dans la bdd
			Joueur joueur=joueurDao.lecture(id1);
			//on met � jour la valeur des champs en modifiant la valeur de la variable joueur qui est appelée dans value(très important) des input de la jsp modifierjoueur pour qu'il affiche les données récupérées de la bdd quand on sera sur la page modifier joueur
			request.setAttribute("joueur", joueur);
			//on appelle la page modifier joueur, donc avec les nouvelles données de joueur qui peuvent apparaître
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifierjoueur.jsp").forward(request, response);
		}else {
			//si le bouton modifier n'a pas �t� cliqu� c'est qu'on a appuy� sur supprimer
			joueurDao.supprimer(id1);
			response.sendRedirect("/Appljoueur/listjoueur");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//on recupere les donnees inscrites dans les champs via le nom des champs inscrits dans le html avec la m�thode request.getParameter
		String nom=request.getParameter("txtNom");
		String prenom=request.getParameter("txtPrenom");
		String sexe=request.getParameter("opSexe");
		String id=request.getParameter("idjoueur");
		int id2=Integer.parseInt(id);
		
		//on ajoute ces donnees à l'objet joueur
		Joueur joueur=new Joueur(id2,nom,prenom,sexe);
		
		//on appelle la methode modifier qui prend en parametre le joueur qu'on vient de cr�er
		joueurDao.modifier(joueur);
		//on redirige l'utilisateur vers la page listjoueurs
		response.sendRedirect("/Appljoueur/listjoueur");
		
		//pour limiter le nombre de caractères , code de la prof: 
//		Joueur joueur = new Joueur();        
//        try {
//                joueur.setNom(request.getParameter("txtNom"));
//                joueur.setPrenom(request.getParameter("txtPrenom"));        
//                joueur.setSexe(request.getParameter("opSexe"));
//                int id=Integer.parseInt(request.getParameter("idjoueur"))  ;
//                joueur.setId(id); 
//                joueurDao.modifier(joueur);
//                response.sendRedirect("/Appljoueur/listjoueur");
//            } 
//        catch (BeanException e) 
//            {
//                request.setAttribute("erreur",e.getMessage());
//                this.getServletContext().getRequestDispatcher("/WEB-INF/modifierjoueur.jsp").forward(request, response);
//            }      
	}

}
