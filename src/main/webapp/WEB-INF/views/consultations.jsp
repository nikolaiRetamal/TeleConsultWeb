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
				
			
				<!--  Dans le cas d'un hôpital on remonte une seule liste permettant d'affecter les consultations -->
				<c:if test="${not empty sessionScope.hopital}">
		
					<div id="formulaire">	
					
						<form action="/teleconsult/hopitalModifieConsultation" method="POST" id="listeConsultationHopital">	
						
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
											<th> N° </th>
											<th> Nom du patient </th>
											<th> Date </th>
											<th> Médecin   </th>
											<th> Référent   </th>
											<th> Statut </th>
											<th> &nbsp; </th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="consultations" items="${consultations}">
											<tr >
												<td> ${consultations.consultationId} </td>
												<td> ${consultations.dmpcpatient.patientPrenom}&nbsp;${consultations.dmpcpatient.patientNom} </td>
												<td> ${consultations.consultationDate} </td>
												<td> ${consultations.dmpcpatient.dmpcpersonnelsante.personnelsantePrenom}&nbsp;${consultations.dmpcpatient.dmpcpersonnelsante.personnelsanteNom}</td>
												<td>
													<c:if test="${empty listreferent}">
														Vous n'avez pas de référent enregistré.
													</c:if>
													<c:if test="${not empty listreferent}">
														<select name="listreferent" id="listreferent_${consultations.consultationId}" value="${consultations.dmpcpersonnelsante.personnelsanteId}">
					           								<option value="-1"> - Choisissez un référent - </option>
					       									<c:forEach var="listreferent" items="${listreferent}">
					           								<option <c:if test="${consultations.dmpcpersonnelsante.personnelsanteId == listreferent.personnelsanteId}">
					           								 			selected="selected"
					           										</c:if> 
					           										value="${listreferent.personnelsanteId}"> 
					           										${listreferent.personnelsantePrenom}&nbsp;${listreferent.personnelsanteNom}&nbsp;(${listreferent.specialite.specialiteNom})
					           								</option>
					           								</c:forEach>
					      								</select>
													</c:if>													
												</td>
												<td>
													<c:if test="${consultations.etatConsultation == 0}">
														Nouvelle
													</c:if>
													<c:if test="${consultations.etatConsultation == 1}">
														En cours
													</c:if>
													<c:if test="${consultations.etatConsultation == 2}">
														Avisée
													</c:if>												
												</td>						
												<td>	
													<c:if test="${consultations.etatConsultation == 0}">
														<input type="submit" onclick="javascript: selectModificationHopital(${consultations.consultationId});" value="Affecter le référent"/>
													</c:if>
													<c:if test="${consultations.etatConsultation > 0}">
														<input type="submit" onclick="javascript: selectModificationHopital(${consultations.consultationId}); " value="Changer le référent"/>
													</c:if>
	 											</td>
											</tr>
										</c:forEach>						
									</tbody>
								</table>
								
													<input type="hidden" id="consultationChangement" name="consultationChangement"/>
													<input type="hidden" id="referentChangement" name="referentChangement"/>
							</fieldset>
						</form>
					</div>
				</c:if>	<!--  FIN HOPITAL -->	
				
				
				
				<!--  Dans le cas d'un hôpital on remonte une seule liste permettant d'affecter les consultations -->
				<c:if test="${not empty sessionScope.referent}">
								
		
					<div id="formulaire">	
					
						<!-- Liste des consultations affectées -->
						<form action="/teleconsult/afficheConsultation" method="POST">	
						
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
											<th> N° </th>
											<th> Nom du patient </th>
											<th> Date </th>
											<th> Médecin   </th>
											<th> Statut </th>
											<th> &nbsp; </th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="consultations" items="${consultations}">
											<tr >
												<td> ${consultations.consultationId} </td>
												<td> ${consultations.dmpcpatient.patientPrenom}&nbsp;${consultations.dmpcpatient.patientNom} </td>
												<td> ${consultations.consultationDate} </td>
												<td> ${consultations.dmpcpatient.dmpcpersonnelsante.personnelsantePrenom}&nbsp;${consultations.dmpcpatient.dmpcpersonnelsante.personnelsanteNom}</td>
												
												<td>
													<c:if test="${consultations.etatConsultation == 0}">
														Nouvelle
													</c:if>
													<c:if test="${consultations.etatConsultation == 1}">
														En cours
													</c:if>
													<c:if test="${consultations.etatConsultation == 2}">
														Avisée
													</c:if>												
												</td>			
															
												<td>	
													<input type="submit" onclick="javascript: updateConsulter(${consultations.consultationId});" value="Consulter"/>
												
	 											</td>
											</tr>
										</c:forEach>						
									</tbody>
								</table>
								
													<input type="hidden" id="consulter" name="consulter"/>
							</fieldset>
						</form>
						
					</div>
		
					<div id="formulaire">	
					
						<!-- Liste des demandes d'avis -->
						<form action="/teleconsult/afficheConsultation" method="POST">	
						<h2> 
							<span class="imageHeader">
								<img alt="Consult" src="resources/images/cross.png">
							</span>
							 Demandes d'avis
							 
						</h2>
													
							<fieldset>			
								<table id="avis" >
									<thead>
										<tr>
											<th> N° </th>
											<th> Nom du patient </th>
											<th> Date </th>
											<th> Médecin   </th>
											<th> Statut </th>
											<th> &nbsp; </th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="consultations" items="${avis}">
											<tr >
												<td> ${consultations.consultationId} </td>
												<td> ${consultations.dmpcpatient.patientPrenom}&nbsp;${consultations.dmpcpatient.patientNom} </td>
												<td> ${consultations.consultationDate} </td>
												<td> ${consultations.dmpcpatient.dmpcpersonnelsante.personnelsantePrenom}&nbsp;${consultations.dmpcpatient.dmpcpersonnelsante.personnelsanteNom}</td>
												
												<td>
													<c:if test="${consultations.etatConsultation == 0}">
														Nouvelle
													</c:if>
													<c:if test="${consultations.etatConsultation == 1}">
														En cours
													</c:if>
													<c:if test="${consultations.etatConsultation == 2}">
														Avisée
													</c:if>												
												</td>			
															
												<td>	
													<input type="submit" onclick="javascript: updateAviser(${consultations.consultationId});" value="Consulter"/>
												
	 											</td>
											</tr>
										</c:forEach>						
									</tbody>
								</table>
								
													<input type="hidden" id="aviser" name="aviser"/>
							</fieldset>
						</form>
				
					</div>
				</c:if>
				
		</div>

	</div>
	
	
	
</body>


<!-- Imports JS -->
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="resources/bootstrap/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="resources/js/accueil.js"></script>

<script type="text/javascript">

	function selectModificationHopital(consultationId){
			$('#referentChangement').val($( '#listreferent_'+consultationId ).val() ); 
			$('#consultationChangement').val(consultationId);		
		}

	function updateConsulter(consultationId){
			$('#consulter').val(consultationId);		
		}

	function updateAviser(consultationId){
			$('#aviser').val(consultationId);		
		}
		
	$("#listeConsultationHopital").submit(function( event ) {
		 if ($('#referentChangement').val() ==  null || $('#referentChangement').val() == "-1") {
			alert("Vous n'avez pas choisi de référent");
			event.preventDefault();
		  }
	});
		
</script>
	

</html>