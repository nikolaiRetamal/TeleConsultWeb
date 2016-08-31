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
					<img alt="Consult" src="resources/images/cross.png">
				</span>
				 Liste des consultations 
				 
			</h2>
				
				<fieldset>			
					<table id="consultations" >
						<thead>
							<tr>
								<th> Numéro du dossier </th>
								<th> Nom du patient </th>
								<th> Date </th>
								<th> Médecin   </th>
								<th> Référent   </th>
								<th> Statut </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="consultations" items="${consultations}">
								<tr >
									<td> ${consultations.consultationId} </td>
									<td> ${consultations.dmpcpatient.patientNom} </td>
									<td> ${consultations.consultationDate} </td>
									<td> ${consultations.dmpcpatient.dmpcpersonnelsante.personnelsanteNom}</td>
									<td>
										<c:if test="">
											
										</c:if>
										<c:if test="">
											<select name="listreferent" id="listreferent">
		       									<c:forEach var="listreferent" items="${listreferent}">
		           								<option value="${listreferent.personnelsanteId}">${listreferent.personnelsanteNom}</option>
		           								</c:forEach>
		      								</select>
										</c:if>
										
									</td>
										<c:if test="">
											Nouvelle
										</c:if>
										<c:if test="">
											En cours
										</c:if>
										<c:if test="">
											Avisée
										</c:if>
									<td>
										
									</td>
								</tr>
							</c:forEach>						
						</tbody>
					</table>
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