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
	
			<form action="/teleconsult/controle_identification" method="POST">
	
			<h2>
				<span class="imageHeader">
					<img alt="verrou" src="resources/images/lock.png">
				</span>
				${titrePage}
			</h2>
	
				<fieldset>
					
					
						<c:if test="${not empty erreurIdent}">
							<p class="erreurIdent">
									${erreurIdent}
							</p>
						</c:if>
	
	
					<p>
						<label for="user">Identifiant</label>
					</p>
					<p>
						<input type="text" id="nom" name="nom" placeholder="Nom">
					</p> 
					<!-- JS because of IE support; better: placeholder="mail@address.com" -->
	
					<p>
						<label for="motDePasse">Mot de passe</label>
					</p>
					<p>
						<input type="password" id="motDePasse"
						placeholder="Mot de passe" name="motDePasse">
					</p> 
					<!-- JS because of IE support; better: placeholder="password" -->
	
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