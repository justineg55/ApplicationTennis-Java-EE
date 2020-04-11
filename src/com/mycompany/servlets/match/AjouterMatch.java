package com.mycompany.servlets.match;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Epreuve;
import com.mycompany.beans.Match;
import com.mycompany.beans.Score;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.EpreuveDao;
import com.mycompany.dao.EpreuveDaoImpl;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.MatchDao;
import com.mycompany.dao.MatchDaoImpl;
import com.mycompany.dao.ScoreDao;
import com.mycompany.dao.ScoreDaoImpl;
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

/**
 * Servlet implementation class AjouterMatch
 */
@WebServlet("/ajoutermatch")
public class AjouterMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TournoiDao tournoiDao;
	private JoueurDao joueurDao;
	private EpreuveDao epreuveDao;
	private MatchDao matchDao;
	private ScoreDao scoreDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterMatch() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		DaoFactory daoFactory=DaoFactory.getInstance();
		tournoiDao=new TournoiDaoImpl(daoFactory);
		joueurDao=new JoueurDaoImpl(daoFactory);
		epreuveDao=new EpreuveDaoImpl(daoFactory);
		matchDao=new MatchDaoImpl(daoFactory);
		scoreDao=new ScoreDaoImpl(daoFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		if (session.getAttribute("connectedUser") == null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		
		//on souhaite récupérer la liste de tournois et la liste de joueurs pour pouvoir les afficher dans les select tournoi et joueurs de ajouter match
		//on appelle donc les méthodes lister de tournoi et joueur pour récupérer le contenu de la table tournoi et joueur
		request.setAttribute("tournois", tournoiDao.lister());
		request.setAttribute("joueurs", joueurDao.lister());
		
		//on affiche la vue du formulaire ajouter match
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutermatch.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//l'ajout d'un macth doit provoquer une insertion dans la table epreuve car l'id epreuve est présent dans la table match
		//on va donc récupérer les valeurs rentrées par l'utilisateur présentes dans la table épreuve afin de pouvoir les ajouter dans la table epreuve
		//il va falloir aussi récupérer l'id epreuve qu'on vient d'insérer car on en aura besoin pour l'insertion du match dans la table match
		
		
		//l'utilisateur a selectionné un tournoi dans le formulaire, on récupère l'id du tournoi choisi grâce au champ value du formulaire récupérable grâce à la méthode request.getParameter qui prend le nom du champ en parametre
		//request.getParameter prend un string en parametre donc on parse en int pour récupérer l'id en int
		String idTournoi1=request.getParameter("nomtournoi");
		int idTournoi=Integer.parseInt(idTournoi1);
		
		//on récupère la valeur rentrée par l'utilisateur dans le champ année
		String annee1=request.getParameter("annee");
		int annee=Integer.parseInt(annee1);
		
		//on récupère la valeur sélectionnée par l'utilisateur dans type
		String type=request.getParameter("type");
		
		//on instancie unn objet epreuve avec un constructeur vide
		Epreuve epreuve=new Epreuve();
		
		//on ajoute les données récupérées dans notre objet epreuve via les setters de la classe bean epreuve
		epreuve.setAnnee(annee);
		epreuve.setType(type);
		epreuve.setIdTournoi(idTournoi);
		
		//on appelle la méthode ajouter avec epreuve en parametre de la classe EpreuveDaoImpl, la méthode va permettre un insert avec dans la requete les valeurs qu'on vient d'ajouter dans l'objet epreuve , valeurs qui correspondent aux colonnes de la table epreuve
		epreuveDao.ajouter(epreuve);
		
		//on récupère l'id de la ligne dans la table epreuve qu'on vient d'ajouter car on en aura besoin pour insérer le match
		int idEpreuve=epreuve.getId();
		
		//on récupere les joueurs choisis par l'utilisateur grâce à value="${joueur.id}" qui nous fait directement récupéré l'id du joueur sélectionné
		//.getParameter récupère le champ par la variable name dans la jsp name="finaliste" dans le select de la jsp
		String idFinaliste1=request.getParameter("finaliste");
		int idFinaliste=Integer.parseInt(idFinaliste1);
		
		//même chose avec le vainqueur
		String idVainqueur1=request.getParameter("vainqueur");
		int idVainqueur=Integer.parseInt(idVainqueur1);
		
		//on instancie unn objet match avec un constructeur vide
		Match match= new Match();
		
		//on ajoute les données récupérées dans notre objet match (rappel : correspondant à la bdd) via les setters de la classe bean match
		match.setId_Epreuve(idEpreuve);
		match.setId_Vainqueur(idVainqueur);
		match.setId_Finaliste(idFinaliste);
		
		//on appelle la méthode ajouer de matchDaoImpl pour insert les données récupérées dans notre table match_tennis
		matchDao.ajouter(match);
		
		//une fois l'insert de match terminé , on va pouvoir ajouter le score correspondant à ce match inséré dans la table score
		//on s'attaque à la table score pour réaliser un insert des données concernant le score
		//on récupère idMatch qui est présent dans la table score
		int idMatch=match.getId();
		
		//puis il faut qu'on récupère le score de chaque set pour l'ajouter dans la table
		//il fajut interpréter les valeurs récupérées du formulaire pour ressortir une seule valeur de chaque set pour être cohérent avec la table score dans la bdd
		
		//on récupère les valeurs entrées par l'utilisateur
		String tempset1Vainqueur=request.getParameter("set1vainqueur");
		Integer set1Vainqueur=Integer.parseInt(tempset1Vainqueur);
		
		String tempset2Vainqueur=request.getParameter("set2vainqueur");
		Integer set2Vainqueur=Integer.parseInt(tempset2Vainqueur);
		
		
		
		String tempset1Finaliste=request.getParameter("set1finaliste");
		Integer set1Finaliste=Integer.parseInt(tempset1Finaliste);
		
		String tempset2Finaliste=request.getParameter("set2finaliste");
		Integer set2Finaliste=Integer.parseInt(tempset2Finaliste);
		
		//pour les set3, 4 et 5 qui peuvent être null on récupère en String la valeur dans le formulaire mais on la parse pas tout de suite en Integer
		//ici tres important, si l'utilisateur ne renseigne rien dans les champs (que j'ai expres mis en non requiered dans la jsp pour les set 3, set 4 et set 5) cela va récupéer la valeur "" en faisant getParameter
		String tempset3Vainqueur=request.getParameter("set3vainqueur");
		String tempset4Vainqueur=request.getParameter("set4vainqueur");
		String tempset5Vainqueur=request.getParameter("set5vainqueur");
		String tempset3Finaliste=request.getParameter("set3finaliste");
		String tempset4Finaliste=request.getParameter("set4finaliste");
		String tempset5Finaliste=request.getParameter("set5finaliste");
		
		//on appele la méthode pour convertir notre valeur du set en valeur en cohérence avec le format de la bdd
		Integer set1=getScoreSetForBDD(set1Vainqueur, set1Finaliste);
		Integer set2=getScoreSetForBDD(set2Vainqueur, set2Finaliste);
		Integer set3;
		Integer set4;
		Integer set5;
		
		//on va tester si l'utilisateur a ajouté qqchose pour les sets 3, 4 et 5
		//comme le formulaire renvoie un string vide on teste avec .equals, si c'est egal c'est qu'il n'y a pas eu de set renseigné
		//on pourrait prendre tempset3Vainqueur aussi
		
		//si pas de set 3, pas de set 4 ni 5
		if(tempset3Finaliste.equals("")) {
			set3=null;
			set4=null;
			set5=null;
			
		//ensuite si le set 3 n'était pas vide mais le set 4 l'est on rentre dans le cas suivant : on transforme en Integer les valeurs récupérées de set3 pour leur appliquer la méthode
		}else if(tempset4Finaliste.equals("")) {
			Integer set3Vainqueur=Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste=Integer.parseInt(tempset3Finaliste);
			set3=getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			set4=null;
			set5=null;
		
		//ensuite si le set 4 n'était pas vide mais le set 5 l'est on rentre dans le cas suivant : on transforme en Integer les valeurs récupérées de set 3 et set4 pour leur appliquer la méthode
		} else if(tempset5Finaliste.equals("")) {
			Integer set3Vainqueur=Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste=Integer.parseInt(tempset3Finaliste);
			set3=getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			Integer set4Vainqueur=Integer.parseInt(tempset4Vainqueur);
			Integer set4Finaliste=Integer.parseInt(tempset4Finaliste);
			set4=getScoreSetForBDD(set4Vainqueur, set4Finaliste);
			set5=null;
			
		//le else correspond au cas où tous les sets ont été renseignés
		}else {
			Integer set3Vainqueur=Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste=Integer.parseInt(tempset3Finaliste);
			set3=getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			Integer set4Vainqueur=Integer.parseInt(tempset4Vainqueur);
			Integer set4Finaliste=Integer.parseInt(tempset4Finaliste);
			set4=getScoreSetForBDD(set4Vainqueur, set4Finaliste);
			Integer set5Vainqueur=Integer.parseInt(tempset5Vainqueur);
			Integer set5Finaliste=Integer.parseInt(tempset5Finaliste);
			set5=getScoreSetForBDD(set5Vainqueur, set5Finaliste);
		}
		
		
		//on instancie unn objet score avec un constructeur vide
		Score score=new Score();
		
		//on ajoute les données récupérées dans notre objet score via les setters de la classe bean score
		score.setIdMatch(idMatch);
		score.setSet1(set1);
		score.setSet2(set2);
		
		//à partir du set3 on vérifie que la valeur est non nulle, si oui on ajoute la valeur à l'objet score, si elle est nulle on ne fait rien la valeur de l'attribut de la classe est nulle si elle n'a jamais été renseignée
		if(set3!=null)
			score.setSet3(set3);
		if(set4!=null)
			score.setSet4(set4);
		if(set5!=null)
			score.setSet5(set5);
		
		//on appelle la méthode ajouter avec epreuve en parametre de la classe EpreuveDaoImpl, la méthode va permettre un insert avec dans la requete les valeurs qu'on vient d'ajouter dans l'objet epreuve , valeurs qui correspondent aux colonnes de la table epreuve
		scoreDao.ajouter(score);
		
		//on redirige l'utilisateur vers la lsite des matchs
		response.sendRedirect("/Appljoueur/listmatch");
		
	}
	
	//méthode qui permet de récupérer la valeur du set à mettre dans la bdd (en cohérence) à partir du nb de jeux du finaliste et du vainqueur récupéré du formulaire
	public int getScoreSetForBDD(int setVainqueur,int setFinaliste) {
		return setVainqueur>setFinaliste?setFinaliste:-setVainqueur;
		
	}


}
