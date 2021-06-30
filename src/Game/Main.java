package Game;
import Animal.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Animal> theAnimals = new ArrayList<Animal>();
        Animal normalDog = AnimalFactory.CreateAnimal(AnimalKind.Dog, Type.Normal);
        Animal poisonDog = AnimalFactory.CreateAnimal(AnimalKind.Dog, Type.Poison);
        theAnimals.add(normalDog);
        theAnimals.add(poisonDog);

        normalDog.attack(poisonDog);
        poisonDog.attack(normalDog);

        System.out.println("Normal Dog : " + normalDog.getHealth() + " / "+ normalDog.getMaxHealth());
        System.out.println("Poison Dog : " + poisonDog.getHealth() + " / "+ poisonDog.getMaxHealth());








    }
}
