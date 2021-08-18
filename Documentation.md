# A)	Création d’animaux élémentaires :
Une seule classe Animal permet de composer des animaux uniques. Elle est composée de :
•	Behaviors, des interfaces pouvant être implémentées différemment (comportements d’attaque, de défense, de mort…)
•	Statistiques (Attaque, défense…) et d’altérations de ces statistiques
•	Statuts (Poison, Sommeil…) infligés par des attaques.
•	Attaques.

La constitution d’un animal est facilitée à travers la classe AnimalFactory, dont le but est de ne spécifier que le minimum d’information pour obtenir des animaux « prêts à l’emploi ». La factory prend une espèce et un ou des type(s) élémentaire(s), et applique les statistiques et attaques (certaines propres à l’espèce, certaines propres au(x) type(s). 

## Statistiques :
*	Elles sont calculées à partir de valeurs liées à l'espèce de l’animal, et variées par des multiplicateurs appliqués parles types élémentaires. Ainsi, si l’espèce a 1.2 d’attaque, et le type unique a 0.9, la statistique attaque sera de 1.08.
*	 Elles peuvent être influencées par des bonus et malus appliqués par les attaques et les statuts. Ces fluctuations sont enregistrées dans une liste distincte, et par défaut, au moment de l’envoi d’une attaque, la puissance est déterminée en fonction de la stat attaque et de la fluctuation de cette stat.
*	La manipulation d’une statistique se fait par l’enumérateur StatID. 

## Statuts :
*	Ils sont infligés par les attaques (cf paragraphe Attaques), pour un certain nombre de tours, qui selon l’attaque peut être fixe pour un statut, fixe pour une attaque, ou fluctuant aléatoirement. Ex : Le sommeil sera appliqué entre 1 et 3 tours.
*	Selon le statut, il peut prendre effet tout de suite (ex : un animal s’endort) ou à la fin du tour (ex : le poison enlève des PV à la fin du tour).
*	A chaque fin de tour, la durée du statut est décrémentée, et une fois arrivée à 0, les effets se dissipent et le statut est retiré de la liste.
*	Chaque statut a une classe implémentant une interface. Les effets sont très différents selon les statuts, mais leur exécution est la même pour l’objet Animal.

# B) Création d'attaques :
*	Comme la classe Animal, une classe est composée de Behaviors (un nombre illimité). Ces behaviors implémentent une interface commune, `IActionBehavior`, qui se décline par type de behaviors (voir plus bas) 
*	Les dégâts sont basés sur la statistique d’attaque de l’animal (compte-tenu de sa variation), et envoyés vers l’animal, qui pourra la réduire selon ses capacités de défense. La responsabilité des dégâts enlevés relève donc de l’animal attaqué et non de l’attaquant.
*	Les attaques sont créées, comme les animaux, par une AttackFactory. De l’extérieur, il suffit d’envoyer le nom de l’attaque souhaitée (qui est un énumérateur afin d’éviter toute erreur), et la Factory crée l’attaque, avec ses stats (attaque, précision) et ses éventuels effets additionnels. Un animal a une capacité maximale de 4 attaques + l'attaque commune Defend. En cas de surplus d'attaques, des attaques aléatoires sont enlevées.

## Behaviors :
Chacun de ces comportements sont abstraits et se concrétisent de plusieurs façons, dont la liste n'est pas exhaustive.
* Do Damage : Inflige des dégats : A l'adversaire, à l'attaquant, avec des dégats fixes ou aléatoires.
* Alter Stats : Altère une ou plusieurs statistiques, de l'adversaire ou de l'attaquant.
* Inflict Status : Inflige un statut à l'adversaire ou à l'attaquant, pour une durée fixe, aléatoire...
* Heal : Soigne une partie de la vie, selon un nombre fixe, un % de la vie...

# C) Intelligence Artificielle :
L'interface utilisateur permet de combattre contre une intelligence artificielle, qui fonctionne comme suit :
* Au premier tour, à chaque attaque est associée un score (explication plus bas) et une probabilité (100 au départ). Les attaques sont ensuite triées par score.
* Choix de l'attaque : Un nombre généré aléatoirement (RNG) vérifie, selon la probabilité de la première attaque (score le plus élevé), si l'attaque est choisie. L'opération est répétée jusqu'à ce que le test réussisse ou que la liste soit terminée, auquel cas la première attaque sera choisie.
* A chaque fois qu'une attaque est choisie, la probabilité qu'elle soit choisie de nouveau diminue, afin de garantir une diversité dans les choix de l'IA.
=> L'IA choisira les meilleures attaques selon le système de scoring, mais changera tout de même, donnant une impression plus "humaine".
Il n'y a tout de fois pas de calculs complexes de plusieurs coups en avance, ni de stratégies différenciées selon le stade de la partie.

## Scoring :
* Le score d'une attaque n'est pas fixe, il dépend du contexte (état des statistiques, des status appliqués, ...). Quelques exemples :
  * Une attaque infligeant un statut déjà appliqué sur l'ennemi ne rapportera pas de score.
  * Si une statistique a déjà été réhaussée, l'attaque l'augmentant de nouveau rapportera moins de score qu'une stat vierge d'altération.
* Le score est la somme des scores de chaque behaviors d'une attaque (dont la définition est imposée par leur interface commune).
