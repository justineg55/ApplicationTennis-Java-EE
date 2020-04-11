package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.User;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.HashClass;
import com.mycompany.dao.UserDaoImpl;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDaoImpl userDaoImpl;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    //ne pas oublier de cr�er la m�thode init en instanciant les trucs dont on a besoin
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	DaoFactory daoFactory=DaoFactory.getInstance();//je cr�e une instance de daofactory
    	userDaoImpl = new UserDaoImpl(daoFactory);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //cette m�thode est appel�e d�s qu'on entre l'url
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//afficher la page jsp correspondante = vue
		this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String login=request.getParameter("txtLogin");
		String password=request.getParameter("txtPassword");
//		
//		System.out.println(HashClass.sha1(password));
//		
//		System.out.println(login);
//		System.out.println(password);
		User connectedUser=userDaoImpl.isValidLogin(login, password);
		
		if(connectedUser !=null) {
			//création de session
			HttpSession session=request.getSession(true);
			session.setAttribute("connectedUser", connectedUser);
			//on choisit la page à afficher après s'être logué
			response.sendRedirect("/Appljoueur/listjoueur");
		} else {
			//échec de la tentative de lo, on redirige vers la jsp login
			this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
		
		
		
	}

}
