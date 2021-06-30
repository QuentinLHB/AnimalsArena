package Animal;

import Action.Concrete.PoisonBite;
import Action.Concrete.SimpleBite;

public class AnimalFactory {

    public static Animal CreateAnimal(AnimalKind animalKind, Type type){
        Animal animal;

        switch (animalKind){
            case Dog:
                switch (type){
                    case Poison:
                        animal = new Animal("Poison Doggo", 100, 50, 15);
                        animal.addBiteBehavior(new PoisonBite());
                        break;
                    //Here other types
                    default:
                        animal = new Animal("Doggo", 100, 55, 15);
                        animal.addBiteBehavior(new SimpleBite());
                }
                break;

            // here other animals

            default:
                animal = new Animal("Generic Animal", 10, 10, 100);

        }

        return animal;
    }
}
