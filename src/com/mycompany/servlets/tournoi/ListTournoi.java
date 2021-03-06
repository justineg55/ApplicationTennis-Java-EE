package com.mycompany.servlets.tournoi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class ListTournoi
 */
@WebServlet("/listtournoi")
public class ListTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TournoiDao tournoiDao;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListTournoi() {
		super();
		
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		DaoFactory daoFactory=DaoFactory.getInstance();
		tournoiDao=new TournoiDaoImpl(daoFactory);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// on teste si notre utilisateur s'est connecté, si non on le redirige sur la page login
		HttpSession session = request.getSession(true);
		if (session.getAttribute("connectedUser") == null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		request.setAttribute("tournois", tournoiDao.lister());
		this.getServletContext().getRequestDispatcher("/WEB-INF/listtournoi.jsp").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//on crée une session, si un utilisateur n'est pas créée (pas de connexion créé) alors on dirige l'utilisateur vers la page login pour qu'il puisse se loguer
				HttpSession session=request.getSession(true);
				//c'est dans la servlet login qu'on définit le connecteduser à null s'il n'est pas connecté ou s'il s'est déconnecté ou si ca contient le user alors elle ne sera pas nulle
				if(session.getAttribute("connectedUser")==null) {
					response.sendRedirect("/Appljoueur/login");
					//on met return car cela s'arrete là il ne va pas plus loin, il est redirigé vers la page login
					return;
				}
				
				if(request.getParameter("action1").equals("Rechercher")) {
					//on teste si la recherche a des résultats = taille de la liste retournée par la méthode recherchée à 0, si elle n'a pas de résultats on met occurence à 0
					if(tournoiDao.rechercher(request.getParameter("txtsearch")).size()==0) {
						request.setAttribute("nboccurence", 0);
					}
					//on récupère ce que l'utilisateur a inscrit dans la barre de recherche
					String search=request.getParameter("txtsearch");
					//on  modifie la liste joueurs du html par la liste qui est retournée par la méthode rechercher pour afficher le résultat de la recherche=les joueurs renvoyés par la requete
					request.setAttribute("tournois", tournoiDao.rechercher(search));
					//on fait pas un sendredirect ici car sinon ca rappelle la méthode lister() et donc tous les joueurs, on veut seulement afficher la liste qu'on a récupéré avec la recherche
					this.getServletContext().getRequestDispatcher("/WEB-INF/listtournoi.jsp").forward(request,response);
					
				} else if (request.getParameter("action1").equals("Deconnexion")){
		            session.setAttribute( "connectedUser",null );            
		            response.sendRedirect( "/Appljoueur/login" );
		            return;
		        
		     } 
	}

}
