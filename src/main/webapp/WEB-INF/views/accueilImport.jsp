<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Import</title>
	<!-- Imports CSS -->
	<link href="resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="resources/css/dropzone.css">
	<link rel="stylesheet" href="resources/css/basic.css">
	<link rel="stylesheet" href="resources/css/autocomplete.css">
	<link rel="stylesheet" href="resources/css/medimage.css">
</head>
<body>
	<!-- Import Header -->
	<%@ include file="headerImport.jsp" %>
	<div class="conteneurContenu">
	<div class="contenu">
		<h1 class="titreRecherche">
			<img alt="dicom_logo" src="resources/images/dicom_official.jpg" id="dicomLogo">
			Import d'images
		</h1>
		<div class="row">
			<form:form  modelAttribute="form" id="dropzone-form" action="/medimage/upload"
			class="col-sm-12 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3">
				<div class="fallback">
			    	<input name="file" type="file" multiple />
				</div>
				<div class="form-group">
					<label for="usage">Usage</label>
					<input type="text" class="form-control" id="usage" name="nom_usage" placeholder="Entrez un usage">
					<input type="hidden" id="usageConnu" name="usageConnu">
				</div>
				<div class="form-group">
					<label for="examen">Examen</label>
					<input type="text" class="form-control" id="examen" name="nom_examen" placeholder="Entrez le nom de l'examen">
				</div>
				<div class="form-group">
					<label for="tags">Mots-Clés</label>
					<input type="text" class="form-control" id="tags" name="tags" placeholder="Entrez des mots-clés">
				</div>				
				 <div class="form-group">
					<label for="chosenTags">
				  	Mots-Clés affectés à l'examen :</label>
					<textarea class="form-control" id="chosenTags" name="chosenTags" disabled="disabled"></textarea>
					<textarea class="form-control" id="chosenTagsValue" name="chosenTagsValue" disabled="disabled" style="display:none;"></textarea>
					<input type="hidden" id="chosenTagsValueForDicom" name="chosenTagsValueForDicom">
				</div>
				<form:checkbox path="publique" /> Publique
				<button type="submit" class="btn btn-default">Envoyer</button>
				<div class="dz-default dz-message file-dropzone text-center well col-sm-12">
					<span class="glyphicon glyphicon-paperclip"></span> 
					<span>
						Glissez ici les fichiers .dcm
					</span><br>
					<span>OU</span><br>
					<span>Cliquez pour parcourir</span>
                </div>
				<div class="dropzone-previews"></div>
				
			</form:form>
		</div>
	</div>
	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/dropzone.js"></script>
<script src="resources/js/import.js"></script>
</html>
