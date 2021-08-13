package Model.Animal.Creation.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.*;
import Model.Animal.Behaviors.DefendBehavior.Concrete.FullDefendBehavior;
import Model.Animal.Behaviors.DefendBehavior.Concrete.HealDefendBehavior;
import Model.Animal.Behaviors.DefendBehavior.Concrete.MirrorDefenseBehavior;
import Model.Animal.Behaviors.DefendBehavior.Concrete.SimpleDefendBehavior;
import Model.Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Model.Animal.Behaviors.DieBehavior.Concrete.UndeadDieBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.SelfHarmingAttackBehavior;
import Model.Util.RNG;

import java.util.ArrayList;

public class AnimalFactory {

    private static final int MAX_ATTACKS = 5;
    private AnimalFactory(){}

    public static Animal CreateAnimal(AnimalKind animalKind, ElementType... elementTypes){
        var maxHealthVariation = 1f;
        var attackVariation = 1f;
        var defenseVariation = 1f;
        var speedVariation = 1f;

        var name = "";

        elementTypes = removeDuplicate(elementTypes);


        for (ElementType elementType: elementTypes) {
            if(elementType == null || elementType.equals(ElementType.NORMAL)) continue;
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
            if(elementType == null) break;
            setBehaviors(animal, animalKind, elementType);
        }

        AttackFactory.addAttackToAnimal(animal, AttackEnum.DEFEND);

        for (ElementType elementType: elementTypes){
            if(elementType == null) break;
            addAttacks(animal, animalKind, elementType);
        }

        return animal;
    }

    /**
     * Removes potential elemental types duplicates.
     * @param elementTypes Array of elemental types.
     * @return New array without any duplicate.
     */
    private static ElementType[] removeDuplicate(ElementType[] elementTypes) {
        ArrayList<ElementType> alreadyExistingTypes = new ArrayList();
        boolean exists;
        for (ElementType elementType : elementTypes){
            exists = false;
            for (ElementType alreadyExistingType: alreadyExistingTypes) {
                if(elementType.equals(alreadyExistingType)) {
                    exists = true;
                    break;
                }
            }
            if(!exists) alreadyExistingTypes.add(elementType);
        }
        return alreadyExistingTypes.toArray(new ElementType[alreadyExistingTypes.size()]);
    }

    public static Animal CreateAnimal(AnimalKind animalKind, String name, ElementType... elementTypes){
        var animal = CreateAnimal(animalKind, elementTypes);
        animal.setName(name);
        return animal;
    }

    public static Animal CreateRandomAnimal(){
        int rngAnimal = RNG.GenerateNumber(0, AnimalKind.values().length-1);

        int nbTypes = RNG.GenerateNumber(1, 2);
        ElementType[] types = new ElementType[nbTypes];
        for (int i = 0; i < nbTypes; i++) {
            int rngType = RNG.GenerateNumber(0, ElementType.values().length-1);
            types[i] = ElementType.values()[rngType];
        }

        return CreateAnimal(AnimalKind.values()[rngAnimal], types);
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
                animal.setAttackBehavior(new SelfHarmingAttackBehavior(animal));
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
            animal.setAttackBehavior(new SelfHarmingAttackBehavior(animal));
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
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FIRE_BITE, elementType);
                break;

            case POISON:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.POISON_BITE, elementType);
                break;

            case UNDEAD:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.DEATH_BITE, elementType);

            default:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BITE, elementType);
                break;
        }
    }

    private static void addElementalGenericAttack(Animal animal, ElementType elementType){
        switch (elementType){

            case NORMAL:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.HEADBUTT, elementType);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FURY, elementType);
                break;

            case FIRE:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FLAMETHROWER, elementType);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.BONFIRE, elementType);
                break;

            case POISON:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.PEWK, elementType);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.PERMASTINK, elementType);

                break;

            case WATER:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.TSUNAMI, elementType);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.ICESHIELD, elementType);
                break;
            case UNDEAD:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.GIFT_OF_LIFE, elementType);
                break;

            case ELETRIC:
                AttackFactory.addAttackToAnimal(animal, AttackEnum.THUNDER, elementType);
                AttackFactory.addAttackToAnimal(animal, AttackEnum.FRY, elementType);

            default:
                break;
        }
    }
}
