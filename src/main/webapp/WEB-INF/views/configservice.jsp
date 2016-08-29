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
	<link rel="stylesheet" href="resources/css/identification.css">
	
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="conteneurContenu">

		<div id="login">
	
			<h2>
				
				${titrePage}
			</h2>
	
			<form action="/teleconsult/controle_configuration" method="POST">
	
				<fieldset>
					
					<p>
						<label for="nom">Nom</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="text" id="nom" name="nom" placeholder="Nom">
					</p> 
					
					<p>
						<label for="type">Type de forfait</label>
						<span class="requis">*</span>
					</p>
					<p>
						<input type="text" id="type" name="type" placeholder="Type de forfait">
					</p>
					
				 	<p>
						<label for="Prix"></label>
					
					<label> Débit  </label> <input type="radio" name="type" onclick="toggle_div();" value='debit'>
					<label>   Mensuel  </label><input type="radio" name="type" onclick="toggle_div();" value='mensuel'>
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