package game;

import Action.Attack.Concrete.AttackEnum;
import Action.Attack.Concrete.AttackFactory;
import Animal.Behaviors.BehaviorFactory;
import Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Animal.Creation.Concrete.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static game.DisplayTools.*;
import static game.Main.*;
import static game.Serialization.*;

public class AnimalCreation {


    /**
     * Make the user choose between customization or not.
     * @return the animal, whether customized or not.
     */
    static Animal pickAnimal(){
        int choice;
        System.out.println("How will your fight ?");
        System.out.println("1: Create an animal based on existing species and types (recommended)");
        System.out.println("2: Customize your own animal and stats");
        if(!isSaveEmpty()){
            System.out.println("3: Choose one of the customized animals");
            choice = getIntInputFromUser(1, 3);
        }
        else choice = getIntInputFromUser(1, 2);

        Animal pickedAnimal;
        switch (choice) {
            case 1 -> pickedAnimal = createAnimal();
            case 2 -> pickedAnimal = createCustomAnimal();
            case 3 -> pickedAnimal = chooseExistingCustomAnimal();
            default -> pickedAnimal = createAnimal();
        }
        return pickedAnimal;

    }

    /**
     * Create a customized animal based on the users' choices.
     * @return The created customized animal.
     */
    static Animal createCustomAnimal(){
        // 1. Choose stats
        EnumMap<StatID, Integer> chosenStats = new EnumMap<>(StatID.class);
        var scanner = new Scanner(System.in);
        System.out.println("Name your animal :");
        String name = scanner.nextLine();
        for(StatID statID: StatID.values()){
            System.out.printf("Choose the %s stat value (100 being the basis)%n", statID.name().toLowerCase(Locale.ROOT));
            var value = getIntInputFromUser(0, 200);
            chosenStats.put(statID, value);
        }
        var customAnimal = new Animal(
                name,
                chosenStats.get(StatID.MAX_HEALTH),
                (float)chosenStats.get(StatID.ATTACK)/100,
                (float)chosenStats.get(StatID.DEFENSE)/100,
                (float) chosenStats.get(StatID.SPEED)/100);

        customAnimal.printStats();

        //2. Add 4 attacks
        AttackFactory.addAttackToAnimal(customAnimal, AttackEnum.DEFEND);
        printAllAttacks();
        for (int i = 1; i < 5; i++) {
            System.out.println("Add attack n°" + i);
            int choice = getIntInputFromUser(1, AttackEnum.values().length);

            AttackFactory.addAttackToAnimal(customAnimal, AttackEnum.values()[choice]);
        }
        System.out.println("Good, you chose :");
        printAttacks(customAnimal);

        // 3. Add behaviors
        System.out.println("Customize behavior ?\n0: No\n1: Yes");
        int choice = getIntInputFromUser(0, 1);
        if(choice ==1){
            AttackBehaviorEnum chosenAttackBehavior;
            DefendBehaviorEnum chosenDefendBehavior;
            DieBehaviorEnum chosenDieBehavior;

            String displayFormat = "%d: %s - %s%n";

            var counter = 1;
            System.out.println("Attack behaviors :");
            for (AttackBehaviorEnum attackBehavior: AttackBehaviorEnum.values()) {
                System.out.printf(displayFormat, counter++, attackBehavior.getName(), attackBehavior.getDescription());
            }
            choice = getIntInputFromUser(1, AttackBehaviorEnum.values().length);
            chosenAttackBehavior = AttackBehaviorEnum.values()[choice-1];

            counter = 1;
            System.out.println("Defense behaviors :");
            for (DefendBehaviorEnum defendBehaviorEnum: DefendBehaviorEnum.values()) {
                System.out.printf(displayFormat, counter++, defendBehaviorEnum.getName(), defendBehaviorEnum.getDescription());

            }
            choice = getIntInputFromUser(1, DefendBehaviorEnum.values().length);
            chosenDefendBehavior = DefendBehaviorEnum.values()[choice-1];

            counter = 1;
            System.out.println("Death behaviors :");
            for (DieBehaviorEnum defendBehaviorEnum: DieBehaviorEnum.values()) {
                System.out.printf(displayFormat, counter++, defendBehaviorEnum.getName(), defendBehaviorEnum.getDescription());
            }
            choice = getIntInputFromUser(1, DieBehaviorEnum.values().length);
            chosenDieBehavior = DieBehaviorEnum.values()[choice-1];

            BehaviorFactory.addBehaviors(customAnimal, chosenAttackBehavior, chosenDefendBehavior, chosenDieBehavior);
        }
        else{
            BehaviorFactory.addBehaviors(customAnimal, AttackBehaviorEnum.SIMPLE_BEHAVIOR, DefendBehaviorEnum.SIMPLE_BEHAVIOR, DieBehaviorEnum.SIMPLE_BEHAVIOR);
        }
        addAnimalToSave(customAnimal);
        return customAnimal;
    }

    /**
     * Print the previously customized animals and make the user choose from them.
     * @return the picked customized animal.
     */
    static Animal chooseExistingCustomAnimal(){
        int count = 1;
        ArrayList<Animal> customAnimals = loadAnimals();
        for (Animal customAnimal : customAnimals
                ) {
            System.out.printf("%d: %s%n", count++, customAnimal.getName());
        }
        int choice = getIntInputFromUser(1, customAnimals.size());
        return customAnimals.get(choice-1);
    }

    /**
     * Make the user choose from animal kinds and types to create the animal.
     * @return
     */
    static Animal createAnimal(){
        var scanner = new Scanner(System.in);
        int chosenValue;
        AnimalKind animalKind;
        ElementType elementType;


        // Choose animal
        String displayFormat = "%d: %s [%s]%n";
        System.out.println("Please choose one of the following animals :");
        for (int i = 0; i < AnimalKind.values().length; i++) {
            System.out.printf(displayFormat, i+1, AnimalKind.values()[i], AnimalKind.values()[i].getDescription());
        }
        System.out.println("I'll take : ");
        chosenValue = getIntInputFromUser(1, AnimalKind.values().length);
        animalKind = AnimalKind.values()[chosenValue-1];

        // Choose type
        System.out.println("Good ! Now choose its element.");
        for (int i = 0; i < ElementType.values().length; i++) {
            System.out.printf("%d: %s%n", i+1, ElementType.values()[i]);
        }
        chosenValue = getIntInputFromUser(1, ElementType.values().length);
        elementType = ElementType.values()[chosenValue-1];

        // Choose nickname
        String nickname;
        System.out.printf("Nice, you created a %s %s. Would you like to rename it ?%n", elementType.name(), animalKind.name());
        System.out.println("Enter a name or press 0 : ");
        nickname = scanner.nextLine();

        Animal animal;

        if(!nickname.equals("0")){
            animal = AnimalFactory.CreateAnimal(animalKind, elementType, nickname);
        }
        else animal = AnimalFactory.CreateAnimal(animalKind, elementType);

        return animal;


    }

}
