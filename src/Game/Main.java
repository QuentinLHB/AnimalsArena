package Game;
import Animal.*;
import Damage.IAttack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int nbTour = 1;
    public static List<Animal> theAnimals = new ArrayList<Animal>();
    public static void main(String[] args) {


        System.out.println("Player 1:\n");
        createAnimal();
        theAnimals.get(0).printStats();

        System.out.println("Player 2:\n");
        createAnimal();
        theAnimals.get(1).printStats();

        battle();

    }

    public static void createAnimal(){
        Scanner scanner = new Scanner(System.in);
        AnimalKind animalKind;
        ElementType elementType;
        System.out.println("Please choose one of the following animals :");
        System.out.println("Dog.......1");
        System.out.println("Snake.....2");
        //Autres animaux
        System.out.println("I'll take : ");
        String choice = scanner.nextLine();

        switch (choice){
            case "1":
                animalKind = AnimalKind.DOG;
                break;
            case "2":
                animalKind = AnimalKind.SNAKE;
                break;
            default:
                animalKind = AnimalKind.DOG;
                break;
        }

        System.out.println("Good ! Now choose its type.");
        System.out.println("Normal....1");
        System.out.println("Fire......2");
        System.out.println("Poison....3");

        //Autres types
        System.out.println("It's going to be : ");
        choice = scanner.nextLine();
        switch (choice){
            case "1":
                elementType = ElementType.NORMAL;
                break;
            case "2":
                elementType = ElementType.FIRE;
                break;

            case "3":
                elementType = ElementType.POISON;
                break;

            default:
                elementType = ElementType.NORMAL;
                break;
        }

        String nickname;
        System.out.printf("Nice, you created a %s %s. Would you like to rename it ?%n", elementType.name(), animalKind.name());
        System.out.println("Enter a name or press 0 : ");
        nickname = scanner.nextLine();

        if(!nickname.equals("0")){
            theAnimals.add(AnimalFactory.CreateAnimal(animalKind, elementType, nickname));
        }
        else theAnimals.add(AnimalFactory.CreateAnimal(animalKind, elementType));


    }

    public static void battle(){
        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        do {
            Turn();
        }while (animalA.isAlive() && animalB.isAlive());

        if(animalA.isAlive()){
            System.out.printf("%s wins ! ", animalA);
        }
        else{
            System.out.printf("%s wins ! ", animalB);
        }
    }

    public static void printLives(Animal animal){
        System.out.println(animal.getName() + " : " + animal.getHealth() + " / "+ animal.getMaxHealth());
    }

    public static void Turn(){

        System.out.println("Tour n°" + nbTour + "\n");

        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        peformsAction(animalA, animalB);
        peformsAction(animalB, animalA);

        for (Animal animal:theAnimals) {

            animal.endOfTurn();
        }

        printLives(animalA);
        printLives(animalB);
        System.out.println("");

        nbTour++;
    }

    public static void peformsAction(Animal animal, Animal target){
        ArrayList<IAttack> attacks = animal.getAttacks();
        var scanner = new Scanner(System.in);
        int choix;
        System.out.println("Defend [1/2 dmg]: 0");
        for (var i = 0; i < attacks.size(); i++) {
            IAttack attaque = attacks.get(i);
            System.out.printf("%s : %d%n-->%s%n", attaque.getAttackName(), i+1,  attaque.getDescription());
        }

        do {
            System.out.println("Chosen attack : ");
            choix = scanner.nextInt();
        }while (choix < 0 || choix > attacks.size());

        if(choix == 0) {
            animal.defend();
        }
        else{
            animal.attack(target, animal.chooseAttack(choix));
        }
    }
}
