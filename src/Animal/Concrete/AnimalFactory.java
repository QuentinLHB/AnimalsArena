package Animal.Concrete;

import Action.Attack.Concrete.*;
import Animal.Abstract.IAnimal;
import Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Animal.Behaviors.DieBehavior.Concrete.UndeadDieBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.UndeadAttackBehavior;

import javax.lang.model.type.TypeKind;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalFactory {

    private AnimalFactory(){}

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType... elementTypes){
        var maxHealthVariation = 1f;
        var attackVariation = 1f;
        var defenseVariation = 1f;
        var name = "";

        for (ElementType elementType: elementTypes) {
            maxHealthVariation += (1-elementType.getHealthVariation());
            attackVariation += (1-elementType.getAttackVariation());
            defenseVariation += (1-elementType.getDefenseVariation());
            name += elementType.name() + " ";
        }
        name += animalKind.name();

        var animal = new Animal(
                name,
                (int)Math.round(animalKind.getMaxHealth()* maxHealthVariation) ,
                (int)Math.round(animalKind.getAttack()* attackVariation),
                (int)Math.round(animalKind.getDefense()* defenseVariation)
                );

        for(ElementType elementType: elementTypes){
            setBehaviors(animal, animalKind, elementType);
        }

        for (ElementType elementType: elementTypes){
            addAttacks(animal, animalKind, elementType);
        }

        return animal;
    }

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType elementType, String name){
        var animal = CreateAnimal(animalKind, elementType);
        animal.setName(name);
        return animal;
    }

    public static Animal CreateRandomAnimal(){
        int rngAnimal = getRNG(AnimalKind.values().length);
        int rngType = getRNG(ElementType.values().length);

        CreateAnimal(AnimalKind.values()[rngAnimal], ElementType.values()[rngType]);

        return null;
    }

    private static void addAttacks(Animal animal, AnimalKind animalKind, ElementType elementType){
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
    }

    private static void setBehaviors(Animal animal, AnimalKind animalKind, ElementType elementType){
        if(elementType.equals(ElementType.UNDEAD)){
            animal.setAttackBehavior(new UndeadAttackBehavior(animal));
            animal.setDieBehavior(new UndeadDieBehavior(animal));
        }
        else{
            animal.setAttackBehavior(new SimpleAttackBehavior(animal));
            animal.setDieBehavior(new SimpleDieBehavior(animal));
        }
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
