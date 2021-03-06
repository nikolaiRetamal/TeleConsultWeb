	<div id="header" class="">
		<div class="gauche">
			<ul class="menu">
				<c:if test="${not empty sessionScope.referent}">
					<li>
						<img alt="user" src="resources/images/user.png">
							<span>${sessionScope.referent.personnelsanteNom}</span>
					</li> 
					<li class="options">
						<ul>
							<li>
								<a href="medecinConsultations?type='historique'">Mes consultations</a>
							</li>
							<li>
								<a href="medecinConsultations?type='enCours'">Consultations en cours</a>
							</li>
						</ul>
					</li>
				</c:if>
				<c:if test="${not empty sessionScope.hopital}">
					<li>
						<img alt="user" src="resources/images/cross.png">
							<span>${sessionScope.hopital.structuresanteNom}</span>
					</li> 
					<li class="options">
						<ul>
							<li>
								<a href="configservice">Configuration du service</a>
							</li>
							<li>
								<a href="listemedecin">Liste des médecins</a>
							</li>
							<li>
								<a href="hopitalConsultations">Consultations</a>
							</li>
						</ul>
					</li>
				</c:if>
				<li class="import">
					<a href=".">
						<img alt="rechercher" src="resources/images/ic_launcher.png">
						<span>Accueil </span>
					</a>
				</li>
			</ul> 
		</div>
		<div class="droite">
			<ul class="menu">
				<li class="etatConnexion">
					<c:choose>
						<c:when test="${not empty sessionScope.referent}">
								<a href="deconnexion">
									<img alt="deconnexion" src="resources/images/deconnexion.png">									
									<span class="deconnexion"> Déconnexion </span>
								</a>
						</c:when>
						<c:when test="${not empty sessionScope.hopital}">
								<a href="deconnexion">
									<img alt="deconnexion" src="resources/images/deconnexion.png">									
									<span class="deconnexion"> Déconnexion </span>
								</a>
						</c:when>
						<c:otherwise>
								<a href="connexion">
									<img alt="connexion" src="resources/images/connexion.png">									
									<span class="connexion">Connexion</span>
								</a>
						</c:otherwise>
					</c:choose>
				</li>  
			</ul>
		</div>
	</div>