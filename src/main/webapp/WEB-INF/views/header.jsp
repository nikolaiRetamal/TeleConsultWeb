	<div id="header" class="">
		<div class="gauche">
			<ul class="menu">
				<c:if test="${not empty user}">
					<li class="nomUser">
						<img alt="user" src="resources/images/user.png">
							<span>${user.nom}</span>
					</li> 
					<li class="options">
						<ul>
							<li>
								<a href="mesExamens">Mes examens</a>
							</li>
							<li>
								<a href="mesUsages">Mes usages</a>
							</li>
						</ul>
					</li>
					<li class="import">
						<a href="import">
							<img alt="importer" src="resources/images/importer.png">
							<span>Importer des images</span>
						</a>
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
						<c:when test="${empty user}">
								<a href="connexion">
									<img alt="connexion" src="resources/images/connexion.png">									
									<span class="connexion">Connexion</span>
								</a>
						</c:when>
						<c:otherwise>
								<a href="deconnexion">
									<img alt="deconnexion" src="resources/images/deconnexion.png">									
									<span class="deconnexion"> Déconnexion </span>
								</a>
						</c:otherwise>
					</c:choose>
				</li>  
			</ul>
		</div>
	</div>