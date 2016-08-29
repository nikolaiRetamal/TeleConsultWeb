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
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="conteneurContenu">
								
				<h1 > Liste des consultations </h1>
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
								<select name="listreferent" id="listreferent">
       							<c:forEach var="listreferent" items="${listreferent}">
           						<option value="${listreferent.personnelsanteId}">${listreferent.personnelsanteNom}</option>
           						</c:forEach>
      							</select>
							</td>
							<td>
								
							</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
	

	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/accueil.js"></script>
</html>