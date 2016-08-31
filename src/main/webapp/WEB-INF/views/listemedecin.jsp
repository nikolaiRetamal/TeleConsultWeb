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
		
			<form>
			
				<fieldset>
									
					<h2> 
						<span class="imageHeader">
							<img alt="user" src="resources/images/user.png">	
						</span>				
						Liste des médecins 
					
					</h2>
					
					<table id="medecins" >
						<thead>
							<tr>
								<th> Nom </th>
								<th> Prénom </th>
								<th> Spécialité   </th>
								<th> Rôle   </th>
								<th> Actions </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="listmedecin" items="${listmedecin}">
								<tr>
									<td> ${listmedecin.personnelsanteNom} </td>
									<td> ${listmedecin.personnelsantePrenom} </td>
									<td> ${listmedecin.specialite.specialiteNom} </td>
									<td> médecin </td>
									<td>
										<a href="modifier?id=${listmedecin.personnelsanteId}">Modifier /</a>
										<a href="delete?id=${listmedecin.personnelsanteId}"> Supprimer</a>
									</td>
								</tr>
							</c:forEach>
							<c:forEach var="listreferent" items="${listreferent}">
								<tr>
									<td> ${listreferent.personnelsanteNom} </td>
									<td> ${listreferent.personnelsantePrenom} </td>
									<td> ${listreferent.specialite.specialiteNom} </td>
									<td> référent </td>
									<td>
										<a href="modifier?id=${listreferent.personnelsanteId}">Modifier /</a>
										<a href="delete?id=${listreferent.personnelsanteId}"> Supprimer</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<br/>
					<input type="button"   onClick="window.location.href='creermedecin'" value="Ajouter un nouveau médecin"/>
				</fieldset>
			</form>
		</div>
	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/accueil.js"></script>
</html>