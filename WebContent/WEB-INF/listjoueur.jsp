<!doctype html>
<!-- bibliotheque JSTL : il faut aussi ajouter la librairie JSTL.jar dans WEB-INF lib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="fr">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link rel="stylesheet" href="starter-template.css">
<title>Hello, world!</title>
</head>
<body>
	<%@ include file="menu.jsp" %>

	<main role="main" class="container">

		<div class="starter-template">
			<h1>Liste des joueurs</h1>
			<p class="lead">  Bienvenue <c:out value="${ connectedUser.login }" />   Lorem ipsum dolor sit amet, consectetur
				adipiscing elit, sed do eiusmod tempor incididunt ut labore et
				dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
				exercitation ullamco laboris nisi ut aliquip ex ea commodo
				consequat. Duis aute irure dolor in reprehenderit in voluptate velit
				esse cillum dolore eu fugiat nulla pariatur.</p>
		</div>

	</main>
	<!-- /.container -->
	<div class="container">

		<div
			style="padding: 1.5rem; margin-right: 0; margin-left: 0; border-width: .2rem;">
			<a href="/Appljoueur/ajouterjoueur">
				<button type="button" class="btn btn-primary">Ajouter
					joueur</button>
			</a>
		</div>
		<table class="table">
			<thead>
				<tr>
					<!-- th les colonnes -->
					<th scope="col" style="width: 10%">#</th>
					<th scope="col" style="width: 25%">Nom</th>
					<th scope="col" style="width: 20%">Prenom</th>
					<th scope="col" style="width: 20%">Sexe</th>
					<th scope="col" style="width: 20%"></th>
				</tr>
			</thead>
			<tbody>
			
			<!-- on ajoute cette partie c:if pour indiquer aucune occurence si on n'a aucun résulat avec notre recherche -->
			<c:if test="${nboccurence == 0 }">
			<tr>
			<!-- colspan est là pour regrouper les 5 colonnes -->
			<td colspan="5" style="text-align:center">
			il n'y a aucune occurence trouvée
			</td>
			</tr>
			</c:if>
			
				<!-- items : on met le nom de la liste qui correspond au setAttribute dans la servlet et var : on met n'importe quoi, c'est le nom de chaque élément de la liste qu'on parcourt -->
				<c:forEach items="${joueurs}" var="joueur">
					<form action="modifierjoueur" method="get">
				<tr>
							<th><c:out value="${joueur.id}"></c:out></th>
							<td><c:out value="${joueur.nom}"></c:out></td>
							<td><c:out value="${joueur.prenom}" escapeXml="false" /></td>
							<%-- <td> ${joueur.prenom}"> </td> --%>
							<td><c:out value="${joueur.sexe}"></c:out></td>
							<td>
							<input type="hidden" name="idjoueur" value="${ joueur.id }" />
							<!-- on peut désactiver un bouton pour un certain type d'utilisateur : profil 2 dans la bdd -->
								<button type="submit"  name="action" value="Modifier" class="btn btn-outline-primary" <c:if test="${connectedUser.profil == '2'}" >disabled</c:if> >Modifier</button>
								<button type="submit" name="action" value="Supprimer" class="btn btn-outline-warning" <c:if test="${connectedUser.profil == '2'}" >disabled</c:if> >Supprimer</button>
							</td>
				</tr>
						 </form> 
						
					
				</c:forEach>

				<!-- <tr>
					td les lignes
					<th scope="row">1</th>
					<td>Mark</td>
					<td>Otto</td>
					<td>@mdo</td>
					<td>
						<button type="button" class="btn btn-outline-primary">Modifier</button>
						<button type="button" class="btn btn-outline-warning">Supprimer</button>
					</td>
				</tr>
				<tr>
					<th scope="row">2</th>
					<td>Jacob</td>
					<td>Thornton</td>
					<td>@fat</td>
					<td>
						<button type="button" class="btn btn-outline-primary">Modifier</button>
						<button type="button" class="btn btn-outline-warning">Supprimer</button>
					</td>
				</tr>
				<tr>
					<th scope="row">3</th>
					<td>Larry</td>
					<td>the Bird</td>
					<td>@twitter</td>
					<td>
						<button type="button" class="btn btn-outline-primary">Modifier</button>
						<button type="button" class="btn btn-outline-warning">Supprimer</button>
					</td>
				</tr> -->
			</tbody>
		</table>


		<!-- Optional JavaScript -->
		<!-- jQuery first, then Popper.js, then Bootstrap JS -->
		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
			integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
			crossorigin="anonymous"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
			integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
			crossorigin="anonymous"></script>
		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
			integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
			crossorigin="anonymous"></script>
</body>
</html>