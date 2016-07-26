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
	<link rel="stylesheet" href="resources/css/identification.css">
</head>
<body>

	<div id="login">

		<h2>
			<span class="lock">
				<img alt="verrou" src="resources/images/lock.png">
			</span>
			${titrePage}
		</h2>

		<form action="/medimage/controle_identification" method="POST">

			<fieldset>

				<p>
					<label for="user">Nom d'utilisateur</label>
				</p>
				<p>
					<input type="text" id="nom" name="nom"
					placeholder="Nom">
				</p> 
				<!-- JS because of IE support; better: placeholder="mail@address.com" -->

				<p>
					<label for="motDePasse">Mot de passe</label>
				</p>
				<p>
					<input type="password" id="motDePasse"
					placeholder="Mot de passe" name="motDePasse">
				</p> 
				<!-- JS because of IE support; better: placeholder="password" -->

				<p>
					<input type="submit" value="Valider">
				</p>

			</fieldset>

		</form>

	</div> <!-- end login -->

</body>	
</html>