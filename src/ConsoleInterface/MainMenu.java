package ConsoleInterface;

import Model.Animal.Creation.Concrete.Animal;
import Model.Util.Position;
import Model.playerAI.Concrete.PlayerAI;

import static ConsoleInterface.AnimalCreation.chooseMethodCreationMenu;
import static ConsoleInterface.AnimalCreation.createCustomAnimal;
import static ConsoleInterface.Battle.battle;
import static ConsoleInterface.DisplayTools.*;
import static ConsoleInterface.DisplayTools.clearConsole;

public class MainMenu {

    static int turns = 1;

    static Animal animalA;
    static Animal animalB;

    public static void startMainMenu(){

        var menuDisplay = """
                        Welcome in Animals Arena, where you can experience RPG-like fights. Please select the mode you'd like :                                
                        1: Player vs Player : Each player composes its animal for an intense fight !
                        2: Player vs AI : Compose your animal and fight against a computer !
                        3: AI vs AI : Spectate a fight between two AI !
                        4: Consult animals and attacks' statistics.
                        5: Create your custom animal.
                        0 : Exit the program.""";

        var value = 0;
        do{
            turns = 1;
            System.out.println(menuDisplay);

            value = getIntInputFromUser(0, 5);

            switch (value){
                case 1 -> startPVP();
                case 2 -> startPVE();
                case 3 -> startAIvAI();
                case 4 -> consultInfos();
                case 5 -> createCustomAnimal();
                default -> System.exit(0);
            }
        }while (true);

    }

    private static void startAIvAI() {


        animalA = chooseMethodCreationMenu("AI 1");
        animalB = chooseMethodCreationMenu("AI 2");
        System.out.printf("%s VS %s%n", animalA, animalB);

        PlayerAI playerA = new PlayerAI(Position.BOTTOM, animalA, animalB);
        PlayerAI playerB = new PlayerAI(Position.TOP, animalB, animalA);
        battle(playerA, playerB);
    }

    private static void consultInfos() {

        var consultInfoMenuDisplay = """
                1: How are statistics handled ?
                2: Consult animals' statistics
                3: Consult element types' statistics
                4: Consult attacks' statistics
                0: Go Back to the main menu""";

        int choice;
        do{
            System.out.println(consultInfoMenuDisplay);
            choice = getIntInputFromUser(0, 4);

            switch (choice){
                case 1->{
                    var explaination = """
                            Each animal has stats : Attack, Defense, Speed...
                            Each elemental type makes these stats vary.
                            Some attack and statuses make these stats vary too.
                            Damage taken is based on :
                                the animal's attack (influenced by its kind and type(s))
                                the attack's damage base
                                the foe's defense
                                the foe's action mode (Attack/Defense)
                            Ex: AttackingAnimal (1.1Atk, 0.9 Atk Variation) performs Bite (15 DMG)
                            The foe (1.3 Def) is in Defense Mode :
                            DmgBase * Atk * AtkVar * (1+(1-Defense)) * DefenseMode = 15*1.1*0.9*0.7*0.5 = 5. 
                            Roundings along the way may produce a 1dmg difference.""";
                    System.out.println(explaination);
                    pressKeyToGoBack();
                }

                case 2-> {
                    printAllAnimalKind();
                    pressKeyToGoBack();
                }

                case 3 -> {
                    printAllElementalTypes();
                    pressKeyToGoBack();
                }

                case 4 -> {
                    printAllAttacks();
                    pressKeyToGoBack();
                }

                default -> choice =0;

            }
        }while (choice !=0);

    }

    private static void startPVE() {
        System.out.println("Player :\n");
        animalA = chooseMethodCreationMenu("Player 1");
        animalA.getStatDisplay();


        System.out.println("AI's animal :");
        animalB = chooseMethodCreationMenu("the AI");
        animalB.getStatDisplay();

        PlayerAI playerAI = new PlayerAI(Position.TOP, animalB, animalA);
        battle(null, playerAI);
    }

    public static void startPVP(){

        String playerName = "Player 1";
        System.out.printf("%s:%n%n", playerName);
        animalA = chooseMethodCreationMenu(playerName);
        animalA.getStatDisplay();

        clearConsole();

        playerName = "Player 2";
        System.out.printf("%s:%n%n", playerName);
        animalB = chooseMethodCreationMenu(playerName);
        animalB.getStatDisplay();

        clearConsole();

        battle(null, null);
    }
}
