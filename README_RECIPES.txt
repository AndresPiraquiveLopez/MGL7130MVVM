README.txt

PROJET DÉVELOPPEMENT D'APPLICATION MOBILE
APPAREILS EN CHARGE : Téléphone Samsung et Tablette Nexus 7.


UQAM

MGL7130

Nom : World Recipes

1.0) Repos sur GitHub au besoin : https://github.com/AndresPiraquiveLopez/MGL7130.git

1) Pour ouvrir l'application cliquer sur l'icon de cuillière et fourchette

2.0 ) Assuerez vous d'avoir une connexion sur Internet pour vous connecter.

2)Gestion d'identification
	2.1)Pour vous connecter
			Nom d'utilisateur : prof@ens.uqam.ca
			Mot de passe : qwerty
			Cliquer sur Login
	2.2)Pour créer un compte cliquer sur Register!
			Compléter le formulaire
3)En entrant dans l'application vous êtes dans la page principale. Elle se nomme "Wolrd Recipes". Elle affiche toutes les recettescontenu dans la base de donnée.

4)Les 3 petits points en haut représentent le menu de l'application.
	4.1)Options :
		"Recipes"
			Est la page d'accueil ou l'on trouve toute les recettes de l'application(Tout ce qui se trouve dans la base de donnée Firestore)
		"Add Recipes"
			Formulaire pour ajouter une recette. TOUS LES CHAMPS SON OBLIGATOIRES. Les données seront sauvegardées dans la base de donnée Firestore
		"Favoris"
			Cette option pemet de faire afficher vos recette favorite. Celle que vous avez choisi en cliquant sur l'étoile dansla page détail du recette. Ces recettes sont enregistré localement sur l'appareil dans une bd SQLLite.
		"Search Recipes"
			Permet de faire une recherche de recettes dansla base de donnée cloud "FireStore". Une rechherche par titre/nom de recette.(Le nom doit être exacte) 
		"User Settings"
			Page qui affiche les options en lien avec le compte utilisateur. Supprimer le compte et Fermer la session.

Parcourir l'application

5)À partir de la page d'accueil "World Recipes"
	Cliquer sur une recette pour ouvrir le détail

6)À partir du détail du recette "Recipe"
	Cliquersur l'étoile pour ajouter au "Favoris"
	Donnez une note de 1 - 5, puis clicuer sur le menu "Recipes" pour mettre à jour la liste de recettes
	
7)Pour ajouter une nouvelle recette
	Cliquer sur le 3 points en haut à droite et sur "Add recipes"
	Remplir complètement le formulaire et cliquer sur "Save"

8)Faire une recherche de recettes dans toutes les recettes.
	Cliquer sur le 3 points en haut à droite et sur "Search Recipes"
	Inscrire dans la ligne le titre (exacte) du nom de la recette et cliquer sur "Search"
	Dans la liste, cliquer sur la recette pour avoir le détail.

9)Pour afficher vos favoris
	Cliquer sur le 3 points en haut à droite et sur "Favoris".
	La liste de vos favoris sera affiché.
	Cliquer sur une recette pour voir le détail.
