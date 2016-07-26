<%@page import="org.springframework.context.annotation.Import"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	<!-- Imports CSS -->
	<link href="resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
	<link href="resources/css/footable.core.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/footable.metro.min.css" rel="stylesheet" type="text/css" />
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
		<div class="descriptionExamen">
		<div id="presentationExamen">
			<div class="blocPresentation">
				<hr>
				<div class="colonnePresentation">
					<span class="libelle">Examen</span><br>
					<span class="attribut">${dicom.nom_examen}</span>
				</div>
				<div class="colonnePresentation">
					<span class="libelle">Date de création</span><br>
					<span class="attribut">
						<fmt:formatDate value="${dicom.date_import}" 
						pattern="dd-MM-yyyy HH:mm:ss" />
					</span>
				</div>
			</div>
			<div class="blocPresentation">
				<hr>
				<div class="colonnePresentation">
					<span class="libelle">Usage</span><br>
					<span class="attribut">
						${dicom.nom_usage}
					</span>
				</div>
				<div class="colonnePresentation">
					<span class="libelle">Nom de l'image</span><br>
					<span class="attribut">
						${dicom.nom}
					</span>
				</div>
			</div>
			<div class="blocPresentation">
				<hr>
				<div class="colonnePresentation">
					<span class="libelle">Tags</span><br>
					<span class="attribut">
						<c:forEach var="tag" items="${dicom.tags}" varStatus="status">
							${tag}
							<c:if test="${not status.last}">
							, 
							</c:if>
						</c:forEach>
					</span>
				</div>
			</div>
			<div class="blocPresentation">
				<hr>
				<div class="colonnePresentation">
					<span class="libelle" onclick=
						"document.location = '/medimage/telecharger?id_dicom=${dicom.id_dicom}';">
						Telecharger
					</span><br>
				</div>
			</div>
		</div>
			
		<!-- Utiliser http://fooplugins.com/plugins/footable-jquery/#/ -->
		<table id="metadonnees" class="footable tableauFoo" data-page-size="20">
			<thead>
				<tr>
					<th>
				    	Nom Métadonnée
					</th>
					<th>
				    	Valeur
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="metadata" items="${dicom.metadatas}">
					<tr>
						<td>${metadata.key}</td>
						<td>${metadata.value}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="2">
						<div class="pagination pagination-centered hide-if-no-paging"></div>
					</td>
				</tr>
			</tfoot>
		</table>
		</div>
	</div>
	</div>
</body>
<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/footable.js" type="text/javascript"></script>
<script src="resources/js/footable.paginate.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () {
    $('.footable').footable();
});
</script>
</html>