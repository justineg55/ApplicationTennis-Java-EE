package com.mycompany.servlets.match;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
	}

}
