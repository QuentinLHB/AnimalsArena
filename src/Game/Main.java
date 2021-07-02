package Game;
import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.Attack.Concrete.AttackFactory;
import Animal.Concrete.Animal;
import Animal.Concrete.AnimalFactory;
import Animal.Concrete.AnimalKind;
import Animal.Concrete.ElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static int nbTour = 1;
    public static List<Animal> theAnimals = new ArrayList<Animal>();
    public static void main(String[] args) {

//        theAnimals.add(AnimalFactory.CreateRandomAnimal());
//        theAnimals.add(AnimalFactory.CreateRandomAnimal());

        StartPVP();


    }

    public static void StartPVP(){
        System.out.println("Player 1:\n");
        createAnimal();
        theAnimals.get(0).printStats();

        separator();

        System.out.println("Player 2:\n");
        createAnimal();
        theAnimals.get(1).printStats();

        separator();

        battle();
    }

    public static void createAnimal(){
        Scanner scanner = new Scanner(System.in);
        AnimalKind animalKind;
        ElementType elementType;

        // Choose animal
        System.out.println("Please choose one of the following animals :");
        for (int i = 0; i < AnimalKind.values().length; i++) {
            System.out.printf("%d: %s%n", i+1, AnimalKind.values()[i]);
        }
        System.out.println("I'll take : ");
        animalKind = AnimalKind.values()[scanner.nextInt()-1];

        // Choose type
        System.out.println("Good ! Now choose its type.");
        for (int i = 0; i < ElementType.values().length; i++) {
            System.out.printf("%d: %s%n", i+1, ElementType.values()[i]);
        }
        elementType = ElementType.values()[scanner.nextInt()-1];
        scanner.nextLine();

        // Choose nickname
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
            System.out.printf("%s wins ! ", animalA.getName());
        }
        else{
            System.out.printf("%s wins ! ", animalB.getName());
        }
    }

    public static void printLives(Animal animal){
        System.out.println(animal.getName() + " : " + animal.getHealth() + " / "+ animal.getMaxHealth());
    }

    public static void Turn(){

        System.out.println("Tour n°" + nbTour + "\n");

        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        if(animalA.canAct()){
            peformsAction(animalA, animalB);
        }
        else System.out.printf("%s can't act.%n%n", animalA.getName());

        if(animalB.canAct()){
            peformsAction(animalB, animalA);
        }
        else System.out.printf("%s can't act.%n%n", animalB.getName());

        for (Animal animal:theAnimals) {
            animal.endOfTurn();
        }

        printLives(animalA);
        printLives(animalB);
        separator();
        separator();
        System.out.println("");

        nbTour++;
    }

    public static void peformsAction(Animal animal, Animal target){
        ArrayList<IAttack> attacks = animal.getAttacks();
        var scanner = new Scanner(System.in);
        int choix;
        System.out.printf("%s's actions :%n", animal.getName());
        System.out.println("Defend [1/2 dmg]: 0");
        for (var i = 0; i < attacks.size(); i++) {
            IAttack attaque = attacks.get(i);
            System.out.printf("%s [%s] : %d%n", attaque.getAttackName(), attaque.getDescription(), i+1);
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

        separator();
    }

    public static void separator(){
        System.out.println("---------------");
    }

    public static void printAllAttacks() {
        ArrayList<Attack> allAttacks = AttackFactory.getAllAttacks();
        for (Attack attack : allAttacks) {
            System.out.printf("%s [%s]%n", attack.getAttackName(), attack.getDescription());
        }
    }
}

