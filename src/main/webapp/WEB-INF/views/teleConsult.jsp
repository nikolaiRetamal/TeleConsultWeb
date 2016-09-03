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
	<link rel="stylesheet" href="resources/css/editeur.css">
	
</head>
<body>
	<%@ include file="header.jsp" %>
	<div class="conteneurContenu">
		<table>
			<tr>
				<td width="40%">
					<!-- -----------------					
					DONNEES PATIENT
					----------------- -->
					<div id="formulaire">
				
						<form action="." method="POST">
						
						
						<h3>		
							<span class="imageHeader">
								<img alt="user" src="resources/images/user.png">	
							</span>				
							${titrePage}
						</h3>
				
						
							<fieldset>
								<table>
									<tbody>
										<tr>
											<th>	
												Sexe
											</th>
											<td>
												<c:if test="${consultation.dmpcpatient.patientDmpcsexe == 1}">
													Masculin
												</c:if>
												<c:if test="${consultation.dmpcpatient.patientDmpcsexe != 1}">
													Féminin
												</c:if>
											</td>
										</tr>
										<tr>
											<th>	
												Date de naissance
											</th>
											<td>
												${consultation.dmpcpatient.patientDatenaissance} 
											</td>
										</tr>
										<tr>
											<th>	
												Traitement au long cours
											</th>
											<td>
												${consultation.traitement} 
											</td>
										</tr>
										<tr>
											<th>	
												Historique de la maladie
											</th>
											<td>
												${consultation.historique} 
											</td>
										</tr>
									</tbody>
								</table>
				
							</fieldset>
				
						</form>
					</div>
					
				</td>
				<td rowspan="2" valign="top">
				
						
							<!-- -----------------					
							AFFICHAGE DES RESULTATS
							----------------- -->
						<div id="formulaire" align="center">
								
							<h3>
							<span class="imageHeader">
								<img alt="Consult" src="resources/images/cross.png">
							</span>				
								${titreResultat}
							</h3>
							<fieldset>			
								<!--Controls for CSS filters via range input-->
								<div class="sliders">
									<form id="imageEditor">
										<p>
											<label for="gs">Teinte</label>
											<input id="gs" name="gs" type="range" min=0 max=100 value=0>
										</p>
										
										<p>
											<label for="br">Luminosité</label>
											<input id="br" name="br" type="range" min=0 max=200 value=100>
										</p>
					
										<p>
											<label for="ct">Contraste</label>
											<input id="ct" name="ct" type="range" min=0 max=200 value=100>
										</p>
					
										<p>
											<label for="invert">Inverser</label>
											<input id="invert" name="invert" type="range" min=0 max=100 value=0>
										</p>
							
										<input type="reset" form="imageEditor" id="reset" value="Reset" />
					
									</form>
								</div>	
					
								<!--container where image will be loaded-->
								<div id="imageContainer" class="center">
									<img src="${resultat.imagePath}/${resultat.imageNom} " />
								</div>
								<div style="display: block;  margin-left: auto; margin-right: auto">												
									 <form action="/teleconsult/afficheConsultation" method="POST">
										<fieldset>
											<input type="hidden" name="consulter" value="${consulter}" /> 
											<input type="hidden" name="aviser" value="${aviser}" /> 
											<input type="hidden" name="resultId" value="${(resultId+1)}" />
											<input type="submit" value="Examen suivant"/> 									
										</fieldset> 
									</form>
								</div>
							</fieldset>					
						</div>
				</td>
			</tr>
			<tr>
				<td>
					
					<!-- -----------------					
					TRAITEMENT DES AVIS
					----------------- -->
					<div id="formulaire">
										
						<h3>		
							<span class="imageHeader">
								<img alt="Consult" src="resources/images/cross.png">
							</span>		
							Rédiger un avis
						</h3>
							<fieldset>
								<table>
									<tbody>															
										<!-- -----------------					
										LIGNE VISIBLE UNIQUEMENT POUR LE REFERENT
										L'objet "CONSULTER" permet d'identifier le référent
										----------------- -->		
										<c:if test="${not empty consulter}">								
											<tr>
												<th>	
													Demander un avis à un confrère
												</th>
												<td>
													<c:if test="${empty confreres}">
														Vous n'avez pas de référent enregistré.
													</c:if>
													<c:if test="${not empty confreres}">
														<form action="/teleconsult/demandeAvis" method="POST">																
															<input type="hidden" name="consulter" value="${consulter}" /> 												
															<input type="hidden" name="aviser" value="${aviser}" /> 
															<input type="hidden" name="resultId" value="${resultId}" />
															<fieldset>
																<select name="confreres" id="confreres" value="${consultations.dmpcpersonnelsante.personnelsanteId}">
							           								<option value="-1"> - Choisissez un confrère - </option>
							       									<c:forEach var="listreferent" items="${confreres}">
							           								<option <c:if test="${consultations.dmpcpersonnelsante.personnelsanteId == listreferent.personnelsanteId}">
							           								 			selected="selected"
							           										</c:if> 
							           										value="${listreferent.personnelsanteId}"> 
							           										${listreferent.personnelsantePrenom}&nbsp;${listreferent.personnelsanteNom}&nbsp;(${listreferent.specialite.specialiteNom})
							           								</option>
							           								</c:forEach>
							      								</select> <br/><br/> <input type="submit" value="demander avis"/>
					      									</fieldset>
														</form>
													</c:if>
												</td>
											</tr>
										</c:if>
										<tr>
											<th>	
												Rédaction de l'avis
											</th>
											<td>
												<form action="/teleconsult/redactionAvis" method="POST">																
														<input type="hidden" name="consulter" value="${consulter}" /> 											
														<input type="hidden" name="aviser" value="${aviser}" /> 
														<input type="hidden" name="resultId" value="${resultId}" />
														<fieldset>
															<textarea name="avisRedige" id="avisRedige"></textarea>
															<c:set var="monAvis" value="${resultId}" /> 
															<c:forEach var="avis" items="${consultation.avises}"> 					
																	<c:forEach var="contributeur" items="${avis.contributeurs}"> 
																		<c:if test="${referent.personnelsanteId == contributeur.dmpcpersonnelsante.personnelsanteId}">								
																			<c:set var="monAvis" value="${avis.avis}" />  
																		</c:if>
																	</c:forEach>																									
																</c:forEach>
															<!-- Seul l'avis du référent peut être définitif -->
															<c:if test="${not empty consulter}">															
																<br/><br/> <label for="definitif">Rendre cet avis définitif&nbsp;&nbsp;</label><input type="checkbox" name="definitif" value="definitif"/>
															</c:if>
															<br/><br/> <input type="submit" value="Sauver l'avis"/>
				      									</fieldset>
													</form>
											</td>
										</tr>
										<tr>
											<th>	
												Historique des avis 
											</th>
											<td>
												<table>
													<c:forEach var="avis" items="${consultation.avises}">
														<tr>
															<td> 
																<c:forEach var="contributeur" items="${avis.contributeurs}"> 
																	${contributeur.dmpcpersonnelsante.personnelsantePrenom}&nbsp;${contributeur.dmpcpersonnelsante.personnelsanteNom}&nbsp;(${contributeur.dmpcpersonnelsante.specialite.specialiteNom})<br/>
																</c:forEach>
															</td>
															<td>
																${avis.avis}
															</td>
														</tr>
													</c:forEach>
													
												</table> 
											</td>
										</tr>
									</tbody>
								</table>
				
							</fieldset>
					</div>
					
				</td>
			</tr>
		
		</table>
		
		

	</div>
</body>
<!-- Imports JS -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<!-- Editeur d'image réalisé par Vikas Lalwani
	https://www.sitepoint.com/build-simple-image-editor-with-css-filters-jquery/
 -->
<script src="resources/js/editeur.js"></script>
<c:out value="${monAvis}" />
<Script>
	$('#avisRedige').val("${monAvis}"); 																			
</Script>

</html>