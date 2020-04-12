package com.mycompany.servlets.match;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Match;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.MatchDao;
import com.mycompany.dao.MatchDaoImpl;
import com.mycompany.services.MatchService;

/**
 * Servlet implementation class ListMatch
 */
@WebServlet("/listmatch")
public class ListMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MatchService matchService;
	private MatchDao matchDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMatch() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	matchService=new MatchService();
    	DaoFactory daoFactory=DaoFactory.getInstance();
    	matchDao=new MatchDaoImpl(daoFactory);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(true);
		if(session.getAttribute("connectedUser")==null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		// on souhaite afficher les matchs comme on l'a défini dans matchUio 
		//c'est dans matchService qu'on construit notre liste de matchs qu'on va afficher à l'utilisateur
		//on fait un foreach dans la jsp pour afficher la variable "matchs" qui est la liste retournée par la méthode getAllMatchsUio
		try {
			request.setAttribute("matchs", matchService.getAllMacthsUio());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//on affiche la jsp listmatch mise à jour avec la variable matchs
		this.getServletContext().getRequestDispatcher("/WEB-INF/listmatch.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if(matchDao.rechercher(request.getParameter("txtsearch")).size()==0) {
				request.setAttribute("nboccurence", 0);
			}
			//on récupère ce que l'utilisateur a inscrit dans la barre de recherche
			String search=request.getParameter("txtsearch");
			List <Match> matchsAfterSearchFilter=matchDao.rechercher(search);
			try {
				request.setAttribute("matchs", matchService.getMacthsUiofromASpecificMatchsList(matchsAfterSearchFilter));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//on fait pas un sendredirect ici car sinon ca rappelle la méthode lister() et donc tous les joueurs, on veut seulement afficher la liste qu'on a récupéré avec la recherche
			this.getServletContext().getRequestDispatcher("/WEB-INF/listmatch.jsp").forward(request,response);
			
		} else if (request.getParameter("action1").equals("Deconnexion")){
            session.setAttribute( "connectedUser",null );            
            response.sendRedirect( "/Appljoueur/login" );
            return;
        
     } 
	}

}
