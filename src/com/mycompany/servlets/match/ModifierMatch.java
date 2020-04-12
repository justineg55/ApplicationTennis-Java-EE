package com.mycompany.servlets.match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Epreuve;
import com.mycompany.beans.Joueur;
import com.mycompany.beans.Match;
import com.mycompany.beans.Score;
import com.mycompany.beans.Tournoi;
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
 * Servlet implementation class ModifierMatch
 */
@WebServlet("/modifiermatch")
public class ModifierMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TournoiDao tournoiDao;
	private JoueurDao joueurDao;
	private EpreuveDao epreuveDao;
	private MatchDao matchDao;
	private ScoreDao scoreDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifierMatch() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		tournoiDao = new TournoiDaoImpl(daoFactory);
		joueurDao = new JoueurDaoImpl(daoFactory);
		epreuveDao = new EpreuveDaoImpl(daoFactory);
		matchDao = new MatchDaoImpl(daoFactory);
		scoreDao = new ScoreDaoImpl(daoFactory);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session=request.getSession(true);
		if(session.getAttribute("connectedUser")==null) {
			response.sendRedirect("/Appljoueur/login");
			return;
		}
		
		// on récupère l'id caché du match que l'utilisateur souhaite modifier
		// balise <input type="hidden" name="idmatch" value="${ match.id }" /> à ajouter
		// dans la jsp listmatch
		String tempIdMatch = request.getParameter("idmatch");
		// on la transforme en int
		int idMatch = Integer.parseInt(tempIdMatch);
		// on enferme cette valeur récupérée dans une variable afin de la récupérer
		// quand on en aura besoin en faisant le submit et en appelant le doPost
		request.setAttribute("idMatch", idMatch);
		System.out.println("idmatch récupéré dans le doGet" + idMatch);

		if (request.getParameter("action").equals("Modifier")) {

			// avec l'idmatch récupéré on veut récupérer toute la ligne correspondante à cet
			// id dans la table match, on enferme cette ligne dans un objet Match retourné
			// par la méthode lecture
			Match match = matchDao.lecture(idMatch);
			// on récupère le contenu de la colonne id_Epreuve de la ligne sélectionée match
			// précédente
			int idEpreuve = match.getId_Epreuve();

			// maintenant qu'on a l'id epreuve correspondant au macth sélectionné, on peut
			// récupérer la ligne dans la table epreuve correspondante à l'id epreuve
			// récupéré
			Epreuve epreuve = epreuveDao.lecture(idEpreuve);
			// les infos contenues dans cette ligne sont l'année,le type et l'idtournoi du
			// macth sélectionnné
			// on attribue l'objet epreuve à une variable afin de pouvoir afficher l'année
			// récupérée dans la jsp avec value='${epreuve.annee}'
			// pour le type, la varibale epreuve va permettre d'afficher le type
			// correspondant au macth en préselection : <option value="F" <c:if
			// test="${epreuve.type == 'F'}" >selected</c:if>>Femme</option>
			request.setAttribute("epreuve", epreuve);

			// on va vouloir afficher le nom du tournoi correspondant donc on récupère
			// l'id_tournoi via l'objet epreuve
			int idTournoi = epreuve.getIdTournoi();

			// on récupère la liste de tous les tournois à afficher dans le champ nomtournoi
			// via un foreach <c:forEach var="tournoi" items="${tournois}">
			request.setAttribute("tournois", tournoiDao.lister());

			// on récupère la ligne dans la table tournoi qui correspond au match
			// sélectionné
			Tournoi tournoi = tournoiDao.lecture(idTournoi);
			// c'est cette id qui va nous permettre de sélectionner le bon nom du tournoi à
			// afficher quand l'utilisateur arrivera sur la page modifier grâce à la
			// comparaison de cette id avec toutes les id de la liste
			// <option value="${tournoi.id}" <c:if test="${tournoi.id == tournoiIdSelected}"
			// >selected</c:if>>${tournoi.nom}</option>
			request.setAttribute("tournoiIdSelected", tournoi.getId());

			// on s'occupe du champ joueur à présent
			// on commence par récupérer l'id vainqueur et l'id finaliste du match à
			// modifier
			long idVainqueur = match.getId_Vainqueur();
			long idFinaliste = match.getId_Finaliste();
			System.out.println("idvainqueur est " + idVainqueur);
			System.out.println("idfinaoiste est " + idFinaliste);

			// on appelle la méthode joueur.lecture pour récupérer la ligne correspondante à
			// l'idVainqueur dans la table joueur et on fait de meme pour l'idFinalsite
			Joueur joueurVainqueur = joueurDao.lecture(idVainqueur);
			Joueur joueurFinaliste = joueurDao.lecture(idFinaliste);

			// on récupère la liste de tous les joueurs à afficher dans le champ finaliste
			// et vainqueur via un foreach <c:forEach var="joueur" items="${joueurs}">
			request.setAttribute("joueurs", joueurDao.lister());

			// c'est cette id qui va nous permettre de sélectionner le bon nom du joueur à
			// afficher quand l'utilisateur arrivera sur la page modifier grâce à la
			// comparaison de cette id avec toutes les id de la liste
			// <option value="${joueur.id}" <c:if test="${joueur.id == vainqueurIdSelected}"
			// >selected</c:if>>${joueur.nom}</option>
			request.setAttribute("vainqueurIdSelected", joueurVainqueur.getId());
			request.setAttribute("finalisteIdSelected", joueurFinaliste.getId());

			// next step : afficher le score du match à modifier
			// on récupère la ligne de la table score correspondant au match à modifier
			// (donc avec l'idmatch) et la méthode lecture
			Score score = scoreDao.lecture(idMatch);

			// on crée une méthode pour récupérer le nombre de jeux gagnés par les joueurs
			// pour chaque set à partir du score récupéré de la bdd pour chaque set
			// cette méthode nous renvoie une liste avec en premier parametre le nombre de
			// jeux gagnés par le vainqueur et en deuxieme parametre le nb de jeux gagnés
			// par le finaliste
			Integer nbJeuxSet1Vainqueur = getNbJeuxFromTableScore(score.getSet1()).get(0);
			Integer nbJeuxSet1Finaliste = getNbJeuxFromTableScore(score.getSet1()).get(1);
			// on attribue cette valeur à une variable pour l'afficher dans la jsp avec
			// value="${nbjeuxSet1Finaliste}"
			request.setAttribute("nbjeuxSet1Vainqueur", nbJeuxSet1Vainqueur);
			request.setAttribute("nbjeuxSet1Finaliste", nbJeuxSet1Finaliste);

			Integer nbJeuxSet2Vainqueur = getNbJeuxFromTableScore(score.getSet2()).get(0);
			Integer nbJeuxSet2Finaliste = getNbJeuxFromTableScore(score.getSet2()).get(1);
			// on attribue cette valeur à une variable pour l'afficher dans la jsp avec
			// value="${nbjeuxSet1Finaliste}"
			request.setAttribute("nbjeuxSet2Vainqueur", nbJeuxSet2Vainqueur);
			request.setAttribute("nbjeuxSet2Finaliste", nbJeuxSet2Finaliste);

			if (!(score.getSet3() == null)) {
				Integer nbJeuxSet3Vainqueur = getNbJeuxFromTableScore(score.getSet3()).get(0);
				Integer nbJeuxSet3Finaliste = getNbJeuxFromTableScore(score.getSet3()).get(1);
				request.setAttribute("nbjeuxSet3Vainqueur", nbJeuxSet3Vainqueur);
				request.setAttribute("nbjeuxSet3Finaliste", nbJeuxSet3Finaliste);
			} else if (!(score.getSet4() == null)) {
				Integer nbJeuxSet4Vainqueur = getNbJeuxFromTableScore(score.getSet4()).get(0);
				Integer nbJeuxSet4Finaliste = getNbJeuxFromTableScore(score.getSet4()).get(1);
				request.setAttribute("nbjeuxSet4Vainqueur", nbJeuxSet4Vainqueur);
				request.setAttribute("nbjeuxSet4Finaliste", nbJeuxSet4Finaliste);
			} else if (!(score.getSet5() == null)) {
				Integer nbJeuxSet5Vainqueur = getNbJeuxFromTableScore(score.getSet5()).get(0);
				Integer nbJeuxSet5Finaliste = getNbJeuxFromTableScore(score.getSet5()).get(1);
				request.setAttribute("nbjeuxSet4Vainqueur", nbJeuxSet5Vainqueur);
				request.setAttribute("nbjeuxSet4Finaliste", nbJeuxSet5Finaliste);
			}

			// on affiche la vue mise à jour avec les variables
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifiermatch.jsp").forward(request, response);
			
			 //si le bouton modifier n'a pas �t� cliqu� c'est qu'on a appuy� sur supprimer
		} else {
			//on appelle la méthode lecture pour récupéréer les id de la table match tennis correspondant à l'épreuve et aux joueurs
			Match match = matchDao.lecture(idMatch);
			
			//on récupère l'id Epreuve qui correspond au match à supprimer (colonne idEpreuve de match)
			int idEpreuve=match.getId_Epreuve();
			
		
			//il faut supprimer dans la table score la ligne qui contient l'id match du match à supprimer
			//il faut supprimer le score avant le match car score est lié à la table match par idmatch, on ne pourra pas supprimer le match avant le score car id match n'existerait plus dans score
			scoreDao.supprimer(idMatch);
			
			//ensuite on peut supprimer le match car on a supprimé la ligne score en rapport avec ce match dans la table score 
			//on supprime pas les joueurs du match à supprimmer car ils peuvent être utilisés pour plusieurs matchs
			//on appelle la méthode supprimer de matchDaoImpl
			matchDao.supprimer(idMatch);
			
			//c'est seulement après avoir supprimé le match qu'on supprime l'epreuve car idepreuve est contenue dans match donc on ne pourra pas supprimer epreuve avant d'avoir supprimé le match  
			epreuveDao.supprimer(idEpreuve);
			
			response.sendRedirect("/Appljoueur/listmatch");
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// on récupère l'id du match sélectionné pour modification grâce à la variable
		// créée dans le doGet value="${idMatch}"
		String tempIdMatch = request.getParameter("idmatch");
		// on la transforme en int
		int idMatch = Integer.parseInt(tempIdMatch);
		System.out.println("idmatch récupéré dans le doPost en int" + idMatch);

		System.out.println("id macth est" + idMatch);
		// on souhaute récupérer l'idepreuve correspondant au match pour pouvoir
		// modifier la ligne epreuve dans la table epreuve
		Match matchSelected = matchDao.lecture(idMatch);

		// on récupère l'idEpreuve grâce à l'objet match constitué à partir de l'id
		// match récupéré, on lit la ligne dans la table match et on récupère
		// l'idEpreuve
		int idEpreuve = matchSelected.getId_Epreuve();

		// on récupère le contenu de tous les champs correspondant à la table epreuve
		// pour modifier la ligne epreuve correspondante au macth sélectionné
		String idTournoi1 = request.getParameter("nomtournoi");
		int idTournoi = Integer.parseInt(idTournoi1);

		String annee1 = request.getParameter("annee");
		int annee = Integer.parseInt(annee1);

		String type = request.getParameter("type");

		// on crée notre objet epreuve avec les valeurs modifiées par l'utilisateur
		Epreuve epreuve = new Epreuve(idEpreuve, annee, type, idTournoi);
		// on update la ligne epreuve modifiée avec les novuelles valeurs rentrées par
		// l'utilisateur via la méthode modifier de daoImpl
		epreuveDao.modifier(epreuve);

		// next step : update la ligne du match sélectionné avec les joueurs rentrés par
		// l'utilisateur : correspond aux champs id finaliste et idvainqueur de la table
		// match_tennis

		// on récupere les id correspondant aux joueurs sélectionnés par l'utilisateur
		// grâce à value="${joueur.id}" dans la jsp
		String tempidVainqueur = request.getParameter("vainqueur");
		int idVainqueur = Integer.parseInt(tempidVainqueur);

		String tempidFinaliste = request.getParameter("finaliste");
		int idFinaliste = Integer.parseInt(tempidFinaliste);

		Match match = new Match(idMatch, idEpreuve, idVainqueur, idFinaliste);
		matchDao.modifier(match);

		// next step : modifier la ligne de la table score avec l'idMatch récupéré et
		// update la table score avec les scores modifiés
		// on récupère les scores sous forme de jeux on doit donc transformer les scores
		// récupérés pour les adapter au format dans lequel ils sont dans la bdd

		// on récupère les valeurs modifiées par l'utilisateur
		String tempset1Vainqueur = request.getParameter("set1vainqueur");
		Integer set1Vainqueur = Integer.parseInt(tempset1Vainqueur);

		String tempset2Vainqueur = request.getParameter("set2vainqueur");
		Integer set2Vainqueur = Integer.parseInt(tempset2Vainqueur);

		String tempset1Finaliste = request.getParameter("set1finaliste");
		Integer set1Finaliste = Integer.parseInt(tempset1Finaliste);

		String tempset2Finaliste = request.getParameter("set2finaliste");
		Integer set2Finaliste = Integer.parseInt(tempset2Finaliste);

		// pour les set3, 4 et 5 qui peuvent être null on récupère en String la valeur
		// dans le formulaire mais on la parse pas tout de suite en Integer
		// ici tres important, si l'utilisateur ne renseigne rien dans les champs (que
		// j'ai expres mis en non requiered dans la jsp pour les set 3, set 4 et set 5)
		// cela va récupéer la valeur "" en faisant getParameter
		String tempset3Vainqueur = request.getParameter("set3vainqueur");
		String tempset4Vainqueur = request.getParameter("set4vainqueur");
		String tempset5Vainqueur = request.getParameter("set5vainqueur");
		String tempset3Finaliste = request.getParameter("set3finaliste");
		String tempset4Finaliste = request.getParameter("set4finaliste");
		String tempset5Finaliste = request.getParameter("set5finaliste");

		// on appele la méthode pour convertir notre valeur du set en valeur en
		// cohérence avec le format de la bdd
		Integer set1 = getScoreSetForBDD(set1Vainqueur, set1Finaliste);
		Integer set2 = getScoreSetForBDD(set2Vainqueur, set2Finaliste);
		Integer set3;
		Integer set4;
		Integer set5;

		// on va tester si l'utilisateur a ajouté qqchose pour les sets 3, 4 et 5
		// comme le formulaire renvoie un string vide on teste avec .equals, si c'est
		// egal c'est qu'il n'y a pas eu de set renseigné
		// on pourrait prendre tempset3Vainqueur aussi

		// si pas de set 3, pas de set 4 ni 5
		if (tempset3Finaliste.equals("")) {
			set3 = null;
			set4 = null;
			set5 = null;

			// ensuite si le set 3 n'était pas vide mais le set 4 l'est on rentre dans le
			// cas suivant : on transforme en Integer les valeurs récupérées de set3 pour
			// leur appliquer la méthode
		} else if (tempset4Finaliste.equals("")) {
			Integer set3Vainqueur = Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste = Integer.parseInt(tempset3Finaliste);
			set3 = getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			set4 = null;
			set5 = null;

			// ensuite si le set 4 n'était pas vide mais le set 5 l'est on rentre dans le
			// cas suivant : on transforme en Integer les valeurs récupérées de set 3 et
			// set4 pour leur appliquer la méthode
		} else if (tempset5Finaliste.equals("")) {
			Integer set3Vainqueur = Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste = Integer.parseInt(tempset3Finaliste);
			set3 = getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			Integer set4Vainqueur = Integer.parseInt(tempset4Vainqueur);
			Integer set4Finaliste = Integer.parseInt(tempset4Finaliste);
			set4 = getScoreSetForBDD(set4Vainqueur, set4Finaliste);
			set5 = null;

			// le else correspond au cas où tous les sets ont été renseignés
		} else {
			Integer set3Vainqueur = Integer.parseInt(tempset3Vainqueur);
			Integer set3Finaliste = Integer.parseInt(tempset3Finaliste);
			set3 = getScoreSetForBDD(set3Vainqueur, set3Finaliste);
			Integer set4Vainqueur = Integer.parseInt(tempset4Vainqueur);
			Integer set4Finaliste = Integer.parseInt(tempset4Finaliste);
			set4 = getScoreSetForBDD(set4Vainqueur, set4Finaliste);
			Integer set5Vainqueur = Integer.parseInt(tempset5Vainqueur);
			Integer set5Finaliste = Integer.parseInt(tempset5Finaliste);
			set5 = getScoreSetForBDD(set5Vainqueur, set5Finaliste);
		}

		// on instancie unn objet score avec un constructeur vide
		Score score = new Score();

		// on ajoute les données récupérées dans notre objet score via les setters de la
		// classe bean score
		score.setIdMatch(idMatch);
		score.setSet1(set1);
		score.setSet2(set2);

		// à partir du set3 on vérifie que la valeur est non nulle, si oui on ajoute la
		// valeur à l'objet score, si elle est nulle on ne fait rien la valeur de
		// l'attribut de la classe est nulle si elle n'a jamais été renseignée
		if (set3 != null)
			score.setSet3(set3);
		if (set4 != null)
			score.setSet4(set4);
		if (set5 != null)
			score.setSet5(set5);

		// on appelle la méthode modifier pour update la table score avec les valeurs
		// insérées dans notre objet score
		scoreDao.modifier(score);

		// on redirige l'utilisateur vers la lsite des matchs
		response.sendRedirect("/Appljoueur/listmatch");

	}

	public List<Integer> getNbJeuxFromTableScore(Integer scoreSet) {
		List<Integer> listeNbJeuxJoueurs = new ArrayList<>();
		Integer nbJeuxVainqueur;
		Integer nbJeuxFinaliste;

		if (scoreSet >= 0 && scoreSet < 5) {
			nbJeuxVainqueur = 6;
			nbJeuxFinaliste = scoreSet;
			listeNbJeuxJoueurs.add(nbJeuxVainqueur);
			listeNbJeuxJoueurs.add(nbJeuxFinaliste);
		} else if (scoreSet >= 0 && scoreSet >= 5) {
			nbJeuxVainqueur = 7;
			nbJeuxFinaliste = scoreSet;
			listeNbJeuxJoueurs.add(nbJeuxVainqueur);
			listeNbJeuxJoueurs.add(nbJeuxFinaliste);
		} else if (scoreSet < 0 && scoreSet > -5) {
			nbJeuxVainqueur = -scoreSet;
			nbJeuxFinaliste = 6;
			listeNbJeuxJoueurs.add(nbJeuxVainqueur);
			listeNbJeuxJoueurs.add(nbJeuxFinaliste);
		} else if (scoreSet < 0 && scoreSet <= -5) {
			nbJeuxVainqueur = -scoreSet;
			nbJeuxFinaliste = 7;
			listeNbJeuxJoueurs.add(nbJeuxVainqueur);
			listeNbJeuxJoueurs.add(nbJeuxFinaliste);
		}
		return listeNbJeuxJoueurs;
	}

	// méthode qui permet de récupérer la valeur du set à mettre dans la bdd (en
	// cohérence) à partir du nb de jeux du finaliste et du vainqueur récupéré du
	// formulaire
	public int getScoreSetForBDD(int setVainqueur, int setFinaliste) {
		return setVainqueur > setFinaliste ? setFinaliste : -setVainqueur;

	}

}
