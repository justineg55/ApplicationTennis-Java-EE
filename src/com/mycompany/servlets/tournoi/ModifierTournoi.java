package com.mycompany.servlets.tournoi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Tournoi;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class ModifierTournoi
 */
@WebServlet("/modifiertournoi")
public class ModifierTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDao tournoiDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierTournoi() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		tournoiDao = new TournoiDaoImpl(daoFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		if (session.getAttribute("connectedUser") == null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
//		on veut afficher la page modifiertournoi avec déjà les valeurs pré-remplies qu'on récupère de listetournoi donc on appelle pas directement la jsp
//		this.getServletContext().getRequestDispatcher("/WEB-INF/modifiertournoi.jsp").forward(request, response);^

		String id = request.getParameter("idtournoi");
		int id2 = Integer.parseInt(id);

		if (request.getParameter("action").equals("Modifier")) {
			Tournoi tournoi = tournoiDao.lecture(id2);
			request.setAttribute("tournoi", tournoi);
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifiertournoi.jsp").forward(request, response);
		} else {
			// si le bouton modifier n'a pas �t� cliqu� c'est qu'on a appuy� sur supprimer
			tournoiDao.supprimer(id2);
			response.sendRedirect("/Appljoueur/listtournoi");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("txtnom");
		String code = request.getParameter("txtcode");
		String id = request.getParameter("idtournoi");
		int id2 = Integer.parseInt(id);

		Tournoi tournoi = new Tournoi(id2, nom, code);

		// on appelle la methode modifier qui prend en parametre le joueur qu'on vient de creer
		tournoiDao.modifier(tournoi);
		// on redirige l'utilisateur vers la page listjoueurs
		response.sendRedirect("/Appljoueur/listtournoi");
	}

}
