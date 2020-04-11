<!doctype html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="fr">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="starter-template.css">
    <title>Hello, world!</title>
  </head>
  <body>
    <%@ include file="menu.jsp" %>

<main role="main" class="container">

  <div class="starter-template">
    <h1>Ajouter un match</h1>
    <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat..</p>
  </div>
   <div style="width:40%; margin:auto;"> 

<form class="needs-validation "  novalidate action="ajoutermatch" method=post>
  <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="validationCustom04">Tournoi</label>
      <select name="nomtournoi" class="custom-select" id="validationCustom01" style="width:400px;" required>
      <option selected disabled value="">Sélectionner...</option>
      	<c:forEach var="tournoi" items="${tournois}">
        	<option value="${tournoi.id}">${tournoi.nom}</option>
		</c:forEach>
      </select>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un tournoi!
      </div>
    </div>  
    </div>
  <div class="form-row">
    <div class="col-md-4 mb-3">
      <label for="validationCustom01">Année</label>
      <input name= "annee" type="number" max="2020" class="form-control" style="width:400px;" id="validationCustom02"  required>
		  <div class="valid-feedback">
        Très bien!
      </div>
      <div class="invalid-feedback">
        Veuillez choisir une année valide !
      </div>
    </div>
  </div> 
  <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="validationCustom04">Type</label>
      <select name="type" class="custom-select" id="validationCustom03" style="width:400px;" required>
        <option selected disabled value="">Sélectioner...</option>
        <option value="F">Femme</option>
		<option value="H">Homme</option>
      </select>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un type!
      </div>
    </div>  
    </div>
      <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="validationCustom04">Finaliste</label>
      <select name="finaliste" class="custom-select" id="validationCustom04" style="width:400px;" required>
      <option selected disabled value="">Sélectioner...</option>
      	<c:forEach var="joueur" items="${joueurs}">
        	
        	<option value="${joueur.id}">${joueur.nom}</option>
		</c:forEach>
      </select>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un finaliste!
      </div>
    </div>  
    </div>
    
       <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="validationCustom04">Vainqueur</label>
      <select name="vainqueur" class="custom-select" id="validationCustom05" style="width:400px;" required>
      <option selected disabled value="">Sélectioner...</option>
      	<c:forEach var="joueur" items="${joueurs}">
        	
        	<option value="${joueur.id}">${joueur.nom}</option>
		</c:forEach>
      </select>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un vainqueur!
      </div>
    </div>  
    </div>
    <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set1vainqueur" style="width:max-content;" >Set 1 : Nombre de jeux gagnés par le vainqueur :</label>
		<input type="number" name="set1vainqueur" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom06"  required>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
    <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set1finaliste" style="width:max-content;" >Set 1 : Nombre de jeux gagnés par le finaliste :</label>
		<input type="number" name="set1finaliste" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom07"  required>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
        <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set2vainqueur" style="width:max-content;" >Set 2 : Nombre de jeux gagnés par le vainqueur :</label>
		<input type="number" name="set2vainqueur" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom08"  required>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
    <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set2finaliste" style="width:max-content;" >Set 2 : Nombre de jeux gagnés par le finaliste :</label>
		<input type="number" name="set2finaliste" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom09"  required>
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
    <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set3vainqueur" style="width:max-content;" >Set 3 : Nombre de jeux gagnés par le vainqueur :</label>
		<input type="number" name="set3vainqueur" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom10" >
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
        <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set3finaliste" style="width:max-content;" >Set 3 : Nombre de jeux gagnés par le finaliste :</label>
		<input type="number" name="set3finaliste" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom11">
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
        <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set4vainqueur" style="width:max-content;" >Set 4 : Nombre de jeux gagnés par le vainqueur :</label>
		<input type="number" name="set4vainqueur" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom12">
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
            <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set4finaliste" style="width:max-content;" >Set 4 : Nombre de jeux gagnés par le finaliste :</label>
		<input type="number" name="set4finaliste" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom13">
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
           <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set5vainqueur" style="width:max-content;" >Set 5 : Nombre de jeux gagnés par le vainqueur :</label>
		<input type="number" name="set5vainqueur" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom14">
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
                <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="set5finaliste" style="width:max-content;" >Set 5 : Nombre de jeux gagnés par le finaliste :</label>
		<input type="number" name="set5finaliste" min="0" max="7" class="form-control" style="width:400px;" id="validationCustom15">
	   <div class="valid-feedback">
       Très bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un nombre entre 0 et 7!
      </div>
    </div>  
    </div>
  
  <button class="btn btn-primary center" type="submit">Submit form</button>
</form>

   <div>

</main><!-- /.container -->

<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>


