# AnimalsArena

FR (EN below)
Un programme créé dans le but de tester les patrons de conception. 
Il simule des combats d'animaux élémentaires (Fire Dog, Poison Unicorn...), en PvP ou PvE. Le joueur choisit son animal, son élément, et selon ces deux facteurs, un animal possédant diverses statistiques,  attaques et comportements est créé. Ils s'affrontent ensuite dans un combat au tour par tour à la façon d'un RPG.

Inclut :
* Strategy Pattern +Composition over inheritance (Une seule classe Animal, dans laquelle on injecte des comportements issus de classes abstraites)
* Factory Pattern (création d'animaux avec la classe statique AnimalFactory)

EN
A test program intended for testing design patterns. 
The program simulates battles opposing elementary animals (Fire Dog, Poison Unicorn...), both in a PvP and a PvE setup. The player first chooses its animal and element, upon which is build an animal possessing diverse statistiques, attacks and behaviors. They then fight in an RPG-like fight.

Includes :
* Strategy Pattern
* Factory Pattern
