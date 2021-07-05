package Animal.Concrete;

import Action.Attack.Concrete.*;

import javax.lang.model.type.TypeKind;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalFactory {

    private AnimalFactory(){}

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType elementType){
        String name = elementType.name() + " " + animalKind.name();

        var animal = new Animal(
                name,
                (int)Math.round(animalKind.getMaxHealth()* elementType.getHealthVariation()) ,
                (int)Math.round(animalKind.getAttack()* elementType.getAttackVariation()),
                (int)Math.round(animalKind.getDefense()* elementType.getDefenseVariation())
                );

        switch (animalKind){
            case DOG:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.BITE));
                addElementalBite(animal, elementType);
                break;

            case SNAKE:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.BITE));
                animal.addAttack(AttackFactory.createAttack(AttackEnum.HYPNOSIS));
                addElementalBite(animal, elementType);
                break;
            case CAT:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.BITE));
                animal.addAttack((AttackFactory.createAttack(AttackEnum.GROWL)));
                addElementalBite(animal, elementType);

                break;

            case UNICORN:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.TORNADO));
                addElementalGenericAttack(animal, elementType);
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

    public static Animal CreateRandomAnimal(){
        int rngAnimal = getRNG(AnimalKind.values().length);
        int rngType = getRNG(ElementType.values().length);

        CreateAnimal(AnimalKind.values()[rngAnimal], ElementType.values()[rngType]);

        return null;
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

    private static void addElementalGenericAttack(Animal animal, ElementType elementType){
        switch (elementType){
            case FIRE:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.FLAMETHROWER));
                break;

            case POISON:
                animal.addAttack(AttackFactory.createAttack(AttackEnum.PEWK));
                break;

            default:
                break;
        }
    }

    private static int getRNG(int max){
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }
}
