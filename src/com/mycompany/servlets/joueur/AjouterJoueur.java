package com.mycompany.servlets.joueur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Joueur;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class AjouterJoueur
 */
@WebServlet("/ajouterjoueur")
public class AjouterJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JoueurDao joueurDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterJoueur() {
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
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
		
		if(session.getAttribute("connectedUser")==null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterjoueur.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//on cr�e un nouveau joueur, celui qu'on utilisera pour mettre en parametre de la m�thode ajouter joueur
		Joueur joueur=new Joueur();
		//on r�cupere la valeur des diff�rents champs remplis par l'utilisateur dans la page ajouter joueur via la m�thode getParameter
		String nom=request.getParameter("nom");
		String prenom=request.getParameter("prenom");
		String sexe=request.getParameter("sexe");
		
		//on set ces valeurs r�cup�r�es du html dans notre objet joueur
		joueur.setNom(nom);
		joueur.setPrenom(prenom);
		joueur.setSexe(sexe);
		//on appelle la m�thode ajouter
		joueurDao.ajouter(joueur);
		
		//ce n'est pas utile car le sendRedirect fait appel � la m�thode doget de listjoueur o� la m�thode lister est appel�e
//		request.setAttribute("joueurs", joueurDao.lister());
		
		//une fois qu'on a submit et r�cup�rer les donn�es on redirige l'utilisateur vers la page listjoueur
		response.sendRedirect("/Appljoueur/listjoueur");
		
		
	}

}
