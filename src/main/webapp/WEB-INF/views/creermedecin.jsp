<%@page import="org.springframework.context.annotation.Import"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	<!-- Imports CSS -->
	<link href="resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="resources/css/teleconsult.css">
	<link rel="stylesheet" href="resources/css/formulaire.css">
	
</head>
<body>

	<%@ include file="header.jsp" %>
	
	<div class="conteneurContenu">

		<div id="formulaire">
	
	
			<form action="/teleconsult/controle_creationmedecin?id=${hopital.structuresanteId}" method="POST">
	
			<h2>	
				<span class="imageHeader">
					<img alt="user" src="resources/images/user.png">	
				</span>		
				${titrePage}
			</h2>
			
				<fieldset>
					
					
						<c:if test="${not empty erreurForm}">
							<p class="erreurForm">
									${erreurForm}
							</p>
						</c:if>
	
					
				 	<p>
						<label for="type"></label>
					
					<label> Médecin local  </label><input type="radio" name="type"  value="medecin">
					<label>   Référent  </label><input type="radio" name="type"  value="local">
					</p>
					<p>
						<label for="nom">Nom</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="text" id="nom" name="nom" placeholder="Nom">
					</p> 
					<!-- JS because of IE support; better: placeholder="mail@address.com" -->
					
					<p>
						<label for="prenom">Prénom</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="text" id="prenom" name="prenom" placeholder="Prénom">
					</p>
					
					<p>
						<label for="motDePasse">Mot de passe</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="password" id="motDePasse"
						placeholder="Mot de passe" name="motDePasse">
					</p> 
					<!-- JS because of IE support; better: placeholder="password" -->
					<p>
						<label for="confirmation">Confirmation du mot de passe</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="password" id="confirmation"
						placeholder="Confirmation du Mot de passe" name="confirmation">
					</p>
					
					<p>
						<label for="email">Email</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="text" id="email" name="email" placeholder="Email" size="20" maxlength="60">
					</p>
					
					<p>
						<label for="telephone">Téléphone</label>
					</p>
					<p>
						<input type="text" id="telephone" name="telephone" placeholder="Téléphone">
					</p>
					
					<p>
					<label for="idspecialite"> Spécialité: </label>
					<select name="idspecialite" id= "idspecialite">
						<c:forEach var="specialite" items="${specialite}"> 
							<option value="${specialite.specialiteId}"> ${specialite.specialiteNom} </option>
						</c:forEach>
					</select>
					</p>
					
   					
					<p>
						<label for="adeli">ADELI</label>
					</p>
					<p>
						<input type="text" id="adeli" name="adeli" placeholder="N° ADELI">
					</p>
					<p>
						<label for="rpps">RPPS</label>
					</p>
					<p>
						<input type="text" id="rpps" name="rpps" placeholder="N° RPPS">
					</p>
					
					<p>
						<label for="role">Role</label>
					</p>
					<p>
						<input type="text" id="role" name="role" placeholder="Role">
					</p>
					
															
					<p>
						<input type="submit" value="Valider">
					</p>
	
				</fieldset>
	
			</form>
	
		</div> <!-- end login -->

	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/accueil.js"></script>
</html>