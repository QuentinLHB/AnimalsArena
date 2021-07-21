package Animal.Creation.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.*;
import Animal.Behaviors.DefendBehavior.Concrete.FullDefendBehavior;
import Animal.Behaviors.DefendBehavior.Concrete.HealDefendBehavior;
import Animal.Behaviors.DefendBehavior.Concrete.MirrorDefenseBehavior;
import Animal.Behaviors.DefendBehavior.Concrete.SimpleDefendBehavior;
import Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Animal.Behaviors.DieBehavior.Concrete.UndeadDieBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.UndeadAttackBehavior;
import Util.RNG;

public class AnimalFactory {

    private static final int MAX_ATTACKS = 5;
    private AnimalFactory(){}

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType... elementTypes){
        var maxHealthVariation = 1f;
        var attackVariation = 1f;
        var defenseVariation = 1f;
        var speedVariation = 1f;

        var name = "";

        for (ElementType elementType: elementTypes) {
            maxHealthVariation *= elementType.getHealthVariation();
            attackVariation *= elementType.getAttackVariation();
            defenseVariation *= elementType.getDefenseVariation();
            speedVariation *= elementType.getSpeedVariation();

            name += elementType.name() + " ";
        }
        name += animalKind.name();

        var animal = new Animal(
                name,
                Math.round(animalKind.getMaxHealth()* maxHealthVariation) ,
                animalKind.getAttack()* attackVariation,
                animalKind.getDefense()* defenseVariation,
                animalKind.getSpeed()* speedVariation
                );

        for(ElementType elementType: elementTypes){
            setBehaviors(animal, animalKind, elementType);
        }

        AttackFactory.addAttackToAnimal(animal, AttackEnum.DEFEND);

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
        int rngAnimal = RNG.GenerateNumber(0, AnimalKind.values().length-1);
        int rngType = RNG.GenerateNumber(0, ElementType.values().length-1);

        return CreateAnimal(AnimalKind.values()[rngAnimal], ElementType.values()[rngType]);
    }

    private static void addAttacks(Animal animal, AnimalKind animalKind, ElementType elementType){
        switch (animalKind){
            case DOG:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BITE);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.GROWL);
                addElementalBite(animal, elementType);
                addElementalGenericAttack(animal, elementType);
                break;

            case SNAKE:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BITE);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.HYPNOSIS);
                addElementalBite(animal, elementType);
                addElementalGenericAttack(animal, elementType);
                break;
            case CAT:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BITE);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.PURR);
                addElementalGenericAttack(animal, elementType);
                addElementalBite(animal, elementType);

                break;

            case UNICORN:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.TORNADO);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.STOMP);
                addElementalGenericAttack(animal, elementType);
                break;

            case CLAM:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.SPIT);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.HEALING_POWER);
                addElementalGenericAttack(animal, elementType);
                break;

            case HEDGEHOG:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.RAGE);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.INTIMIDATION);
                addElementalBite(animal, elementType);
                addElementalGenericAttack(animal, elementType);
                break;

            default:
                break;
        }
        if(animal.getAttacks().size() > MAX_ATTACKS){
            int amountAttacksToRemove = animal.getAttacks().size()-MAX_ATTACKS;
            for (int i = 0; i < amountAttacksToRemove; i++) {
                IAttack randomAttack = animal.getAttacks().get(RNG.GenerateNumber(1, animal.getAttacks().size()-1));
                animal.removeAttack(randomAttack);
            }
        }
    }

    private static void setBehaviors(Animal animal, AnimalKind animalKind, ElementType elementType){

        switch (elementType) {
            case UNDEAD -> {
                animal.setAttackBehavior(new UndeadAttackBehavior(animal));
                animal.setDieBehavior(new UndeadDieBehavior(animal));
            }

            case WATER -> animal.setDefendBehavior(new HealDefendBehavior(animal));
            default -> setSimpleBehaviors(animal);
        }

        switch (animalKind) {
            case HEDGEHOG -> animal.setDefendBehavior(new MirrorDefenseBehavior(animal, 2));
            case CLAM -> animal.setDefendBehavior(new FullDefendBehavior(animal));
            default -> setSimpleBehaviors(animal);
        }

        if(elementType.equals(ElementType.UNDEAD)){
            animal.setAttackBehavior(new UndeadAttackBehavior(animal));
            animal.setDieBehavior(new UndeadDieBehavior(animal));
        }
        else{
            animal.setAttackBehavior(new SimpleAttackBehavior(animal));
            animal.setDieBehavior(new SimpleDieBehavior(animal));
        }
    }

    private static void setSimpleBehaviors(Animal animal){
        animal.setAttackBehavior(new SimpleAttackBehavior(animal));
        animal.setDieBehavior(new SimpleDieBehavior(animal));
        animal.setDefendBehavior(new SimpleDefendBehavior(animal));
    }

    private static void addElementalBite(Animal animal, ElementType elementType){
        switch (elementType){
            case FIRE:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FIRE_BITE);
                break;

            case POISON:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.POISON_BITE);
                break;

            case UNDEAD:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.DEATH_BITE);

            default:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BITE);
                break;
        }
    }

    private static void addElementalGenericAttack(Animal animal, ElementType elementType){
        switch (elementType){

            case NORMAL:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.HEADBUTT);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FURY);
                break;

            case FIRE:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FLAMETHROWER);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BONFIRE);
                break;

            case POISON:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.PEWK);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.PERMASTINK);

                break;

            case WATER:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.TSUNAMI);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.ICESHIELD);
                break;
            case UNDEAD:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.GIFT_OF_LIFE);
                break;

            case ELETRIC:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.THUNDER);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FRY);

            default:
                break;
        }
    }
}
