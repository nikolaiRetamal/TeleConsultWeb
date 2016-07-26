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
	<link rel="stylesheet" href="resources/css/autocomplete.css">
	<link rel="stylesheet" href="resources/css/medimage.css">
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="conteneurContenu">
	<div class="contenu">
		<h1 class="titreRecherche">
			<img alt="dicom_logo" src="resources/images/dicom_official.jpg" id="dicomLogo">
			${titrePage}
		</h1>
		<div class="row">
			<form:form  id="recherche" action="/medimage/recherche"
			class="col-sm-12 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="form-group">
					<label for="usage">Usage</label>
					<input type="text" class="form-control" id="usage" name="usage" placeholder="Entrez un usage">
					<input type="hidden" id="usageConnu" name="usageConnu">
				</div>
				<div class="form-group">
					<label for="metaKey">Métadonnée</label>
					<input type="text" class="form-control" id="metaKey" name="metaKey" 
					placeholder="Entrez un nom de métadonnée">
					<label for="metaValue">Valeur</label>
					<input type="text" class="form-control" id="metaValue" name="metaValue" 
					placeholder="Entrez une valeur pour la métadonnée">
					<button type="button" class="btn btn-default" id="ajoutMeta">Ajouter Metadonnée</button>
				</div>
				<div class="form-group">
					<label>Métadonnées choisies</label>
				  	<div id="listeMetadatas" >
				  	</div>
				  	<input type="hidden" id="metadatas" name="metadatas">
				</div>
				<div class="form-group">
					<label for="tags">Mots-Clés</label>
					<input type="text" class="form-control" id="tags" name="tags" placeholder="Entrez des mots-clés">
				</div>				
				 <div class="form-group">
					<label>Mots-Clés choisis</label>
					<div id="chosenTags"></div>
					<div id="chosenTagsValue" style="display:none;"></div>
					<input type="hidden" name="chosenTagsValue">
				</div>
				<button type="submit" class="btn btn-default">Rechercher</button>
			</form:form>
		</div>
	</div>
	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/accueil.js"></script>
</html>