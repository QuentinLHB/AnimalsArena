package Animal.Concrete;

import Action.Attack.Concrete.*;

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
                animal.addAttack(AttackFactory.createAttack(AttackEnum.BITE));
                addElementalBite(animal, elementType);
                break;

            // here other animals
            case SNAKE:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.BITE));
                animal.addAttack(AttackFactory.createAttack(AttackEnum.HYPNOSIS));
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
                animal.addAttack(AttackFactory.createAttack(AttackEnum.FIRE_BITE));
                break;

            case POISON:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.POISON_BITE));
                break;

            default:
                break;
        }
    }
}
