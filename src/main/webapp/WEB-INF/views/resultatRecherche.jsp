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
	<link href="resources/css/footable.core.min.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/footable.metro.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="resources/css/medimage.css">
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="contenu">
	
		<div class="descriptionExamen">
		<h1 class="titreRecherche">${titrePage}</h1>
		<!-- Utiliser http://fooplugins.com/plugins/footable-jquery/#/ -->
			<table id="examens" class="footable tableauFoo">
				<thead>
					<tr>
						<th>
					    	Examen
						</th>
						<th>
							Usage
						</th>
						<th>
							Date
						</th>
						<th>
							Utilisateur
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="examen" items="${examens}">
						<tr onclick=
						"document.location = '/medimage/detailExamen?id_examen=${examen.id_examen}';">
							<td>${examen.nom_examen}</td>
							<td>${examen.nom_usage}</td>
							<td>${examen.date_import}</td>
							<td>${examen.nom_user}</td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4">
							<div class="pagination pagination-centered hide-if-no-paging"></div>
						</td>
					</tr>
				</tfoot>
			</table>
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