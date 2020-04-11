package com.mycompany.servlets.tournoi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Tournoi;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class AjouterTournoi
 */
@WebServlet("/ajoutertournoi")
public class AjouterTournoi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	TournoiDao tournoiDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterTournoi() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	DaoFactory daoFactory=DaoFactory.getInstance();
    	tournoiDao=new TournoiDaoImpl(daoFactory);
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		if (session.getAttribute("connectedUser") == null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutertournoi.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom=request.getParameter("txtnom");
		String code=request.getParameter("txtcode");
		
		Tournoi tournoi=new Tournoi();
		
		tournoi.setNom(nom);
		tournoi.setCode(code);
		
		tournoiDao.ajouter(tournoi);
		response.sendRedirect("/Appljoueur/listtournoi");
		
		
	}

}
