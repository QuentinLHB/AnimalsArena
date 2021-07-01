package Animal;

import Action.Concrete.FireBite;
import Action.Concrete.Hypnosis;
import Action.Concrete.PoisonBite;
import Action.Concrete.SimpleBite;

public class AnimalFactory {

    private AnimalFactory(){}

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType elementType){
        String name = elementType.name() + " " + animalKind.name();

        var animal = new Animal(
                name,
                (int)Math.round(animalKind.getMaxHealth()* elementType.getHealthVariation()) ,
                (int)Math.round(animalKind.getAttack()* elementType.getDefenseVariation()),
                (int)Math.round(animalKind.getDefense()* elementType.getDefenseVariation())
                );

        switch (animalKind){
            case DOG:
                animal.addAttack(new SimpleBite());
                addElementalBite(animal, elementType);
                break;

            // here other animals
            case SNAKE:
                animal.addAttack(new SimpleBite());
                animal.addAttack(new Hypnosis());
                addElementalBite(animal, elementType);

                break;

            default:
                break;
        }
        return animal;
    }

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType elementType, String name){
        Animal animal = CreateAnimal(animalKind, elementType);
        animal.setName(name);
        return animal;
    }

    private static void addElementalBite(Animal animal, ElementType elementType){
        switch (elementType){
            case FIRE:
                animal.addAttack(new FireBite());
                break;

            case POISON:
                animal.addAttack(new PoisonBite());
                break;

            default:
                break;
        }
    }
}
