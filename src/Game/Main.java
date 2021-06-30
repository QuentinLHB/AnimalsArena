package Game;
import Animal.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static int nbTour = 1;
    public static void main(String[] args) {
        ArrayList<Animal> theAnimals = new ArrayList<Animal>();
        Animal normalDog = AnimalFactory.CreateAnimal(AnimalKind.Dog, Type.Normal);
        Animal poisonDog = AnimalFactory.CreateAnimal(AnimalKind.Dog, Type.Poison);
        theAnimals.add(normalDog);
        theAnimals.add(poisonDog);

        printLives(poisonDog);
        printLives(normalDog);

        Turn(theAnimals, 1, 1);
        Turn(theAnimals, 1, 1);
        Turn(theAnimals, 1, 1);
        Turn(theAnimals, 1, 1);

    }

    public static void printLives(Animal animal){
        System.out.println(animal.getName() + " : " + animal.getHealth() + " / "+ animal.getMaxHealth());
    }

    public static void Turn(ArrayList<Animal> animals, int indexAttackAnimalA, int indexAttackAnimalB){

        System.out.println("Tour n°" + nbTour + "\n");

        Animal animalA = animals.get(0);
        Animal animalB = animals.get(1);

        animalA.attack(animalB, animalA.chooseAttack(indexAttackAnimalA));
        animalB.attack(animalA, animalB.chooseAttack(indexAttackAnimalB));

        for (Animal animal:animals) {
            animal.endOfTurn();
        }

        printLives(animalA);
        printLives(animalB);

        nbTour++;
    }
}
