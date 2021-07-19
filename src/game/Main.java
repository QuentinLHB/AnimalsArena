package game;
import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.Attack.Concrete.AttackEnum;
import Action.Attack.Concrete.AttackFactory;
import Animal.Behaviors.BehaviorFactory;
import Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Animal.Creation.Concrete.*;
import playerAI.Concrete.PlayerAI;

import java.util.*;

public class Main {
    public static int turns = 1;

    protected static List<Animal> theAnimals = new ArrayList<>();
    protected static List<Animal> customAnimals = new ArrayList<>();

    public static void main(String[] args) {


        mainMenu();
//        theAnimals.add(AnimalFactory.CreateAnimal(AnimalKind.HEDGEHOG, ElementType.POISON));
//        theAnimals.add(AnimalFactory.CreateAnimal(AnimalKind.DOG, ElementType.NORMAL));
//
//        Animal a1 = theAnimals.get(0);
//        Animal a2 = theAnimals.get(1);
//        battle(null, null);
//        printAllAttacks();

    }

    public static void mainMenu(){

        var menuDisplay = """
                        Welcome in Animals Arena, where you can experience RPG-like fights. Please select the mode you'd like :                                
                        1: Player vs Player : Each player composes its animal for an intense fight !
                        2: Player vs AI : Compose your animal and fight against a computer !
                        4: AI vs AI : Spectate a fight between two AI !
                        5: Consult animals and attacks' statistics.
                        6: Create your custom animal.
                        0 : Exit the program.""";

        var value = 0;
        do{
            theAnimals.clear();
            turns = 1;
            System.out.println(menuDisplay);

            value = getIntInputFromUser(0, 4);

            switch (value){
                case 1 -> startPVP();
                case 2 -> startPVE();
                case 3 -> startAIvAI();
                case 4 -> consultInfos();
                case 5 -> createCustomAnimal();
                default -> System.exit(0);
            }
        }while (true);

    }

    private static void startAIvAI() {
        //Todo faire choisir l'user
        theAnimals.add(AnimalFactory.CreateRandomAnimal());
        theAnimals.add(AnimalFactory.CreateRandomAnimal());
//        theAnimals.add(AnimalFactory.CreateAnimal(AnimalKind.UNICORN, ElementType.FIRE));
//        theAnimals.add(AnimalFactory.CreateAnimal(AnimalKind.HEDGEHOG, ElementType.WATER));



        //todo choisir la difficulté de l'IA ?
        PlayerAI playerA = new PlayerAI(theAnimals.get(0), theAnimals.get(1));
        PlayerAI playerB = new PlayerAI(theAnimals.get(1), theAnimals.get(0));
        battle(playerA, playerB);
    }

    private static void consultInfos() {

        var consultInfoMenuDisplay = """
                1: How are statistics handled ?
                2: Consult animals' statistics
                3: Consult element types' statistics
                4: Consult attacks' statistics
                0: Go Back to the main menu""";

        int choice;
        do{
            System.out.println(consultInfoMenuDisplay);
            choice = getIntInputFromUser(0, 4);

            switch (choice){
                case 1->{
                    var explaination = """
                            Each animal has stats : Attack, Defense, Speed...
                            Each elemental type makes these stats vary.
                            Some attack and statuses make these stats vary too.
                            Damage taken is based on :
                                the animal's attack (influenced by its kind and type(s))
                                the attack's damage base
                                the foe's defense
                                the foe's action mode (Attack/Defense)
                            Ex: AttackingAnimal (1.1Atk, 0.9 Atk Variation) performs Bite (15 DMG)
                            The foe (1.3 Def) is in Defense Mode :
                            DmgBase*Atk*AtkVar*(1+(1-Defense))*DefenseMode = 15*1.1*0.9*0.7*0.5 = 5. Roundings along the way may produce a 1dmg difference.""";
                    System.out.println(explaination);
                    pressKeyToGoBack();
                }

                case 2-> {
                    printAllAnimalKind();
                    pressKeyToGoBack();
                }

                case 3 -> {
                    printAllElementalTypes();
                    pressKeyToGoBack();
                }

                case 4 -> {
                    printAllAttacks();
                    pressKeyToGoBack();
                }

                default -> choice =0;

            }
        }while (choice !=0);

    }

    private static void startPVE() {
        System.out.println("Player :\n");
        pickAnimal();
        theAnimals.get(0).printStats();

        theAnimals.add(AnimalFactory.CreateRandomAnimal());
        System.out.println("AI's animal :");
        theAnimals.get(1).printStats();

        PlayerAI playerAI = new PlayerAI(theAnimals.get(1), theAnimals.get(0));
        battle(null, playerAI);
    }

    public static void startPVP(){

        System.out.println("Player 1:\n");
        pickAnimal();
        theAnimals.get(0).printStats();

        clearConsole();

        System.out.println("Player 2:\n");
        pickAnimal();
        theAnimals.get(1).printStats();

        clearConsole();

        battle(null, null);
    }

    private static void pickAnimal(){
        int choice;
        System.out.println("How will your fight ?");
        System.out.println("1: Create an animal based on existing species and types (recommended)");
        System.out.println("2: Customize your own animal and stats");
        if(!customAnimals.isEmpty()){
            System.out.println("3: Choose one of the customized animals");
            choice = getIntInputFromUser(1, 3);
        }
        else choice = getIntInputFromUser(1, 2);

        switch (choice) {
            case 1 -> theAnimals.add(createAnimal());
            case 2 -> theAnimals.add(createCustomAnimal());
            case 3 -> theAnimals.add(chooseExistingCustomAnimal());
            default -> theAnimals.add(createAnimal());
        }


    }

    public static Animal createCustomAnimal(){
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
        customAnimals.add(customAnimal);
        return customAnimal;
    }

    private static Animal chooseExistingCustomAnimal(){
        int count = 1;
        for (Animal customAnimal :
                customAnimals) {
            System.out.printf("%d: %s%n", count++, customAnimal.getName());
        }
        int choice = getIntInputFromUser(1, customAnimals.size());
        return customAnimals.get(choice-1);
    }


    public static Animal createAnimal(){
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

    public static void battle(PlayerAI playerA, PlayerAI playerB){
        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        do {
            turn(playerA, playerB);
        }while (animalA.isAlive() && animalB.isAlive());

        if(animalA.isAlive()){
            System.out.printf("%s wins ! ", animalA.getName());
        }
        else{
            System.out.printf("%s wins ! ", animalB.getName());
        }
    }

    public static void printLives(Animal animal){
        System.out.println(animal.getName() + " : " + animal.getHealth() + " / "+ Math.round(animal.getStat(StatID.MAX_HEALTH)* animal.getStatAlteration(StatID.MAX_HEALTH)));
    }

    public static void turn(PlayerAI playerA, PlayerAI playerB){

        System.out.println("Tour n°" + turns + "\n");

        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        if(whichIsFaster() == animalA){
            if(playerA == null) performAction(animalA, animalB);
            else playerA.performMove();
            if(playerB == null) performAction(animalB, animalA);
            else playerB.performMove();
        }
        else {
            if(playerB == null) performAction(animalB, animalA);
            else playerB.performMove();
            if(playerA == null) performAction(animalA, animalB);
            else playerA.performMove();
        }

        for (Animal animal:theAnimals) {
            animal.endOfTurn();
        }

        delay();
        printLives(animalA);
        printLives(animalB);
        delay();
        separator();
        separator();
        System.out.println("");

        turns++;
    }

    public static void performAction(Animal animal, Animal target){
        if(!animal.canAct()) {
            System.out.printf("%s can't act.%n", animal.getName());
            return;
        }
        ArrayList<IAttack> attacks = animal.getAttacks();
        var scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.printf("%s's actions :%n", animal.getName());
            printAttacks(animal);
            System.out.println("(i: Display stats)");
            System.out.println(">> ");

            try{
                choice = scanner.nextInt();
            }catch (Exception e){
                choice = -1;
                if(scanner.nextLine().equals("i") || scanner.nextLine().equals("I")){
                    showStats(animal, target);
                }
            }

        }while (choice < 0 || choice > attacks.size());

        animal.attack(target, animal.chooseAttack(choice));
        delay();

        separator();
    }

    /**
     * Loops until a valid number is given by the user.
     * @param min Min value
     * @param max Max value
     * @return User input
     */
    public static int getIntInputFromUser(int min, int max){
        boolean isInt;
        int value = -1;
        var scanner = new Scanner(System.in);
        do {
            System.out.println(">>");
            var stringValue = scanner.nextLine();
            try{
                 value = Integer.parseInt(stringValue);
                isInt = true;
            }catch (Exception e){
                isInt = false;
                System.out.println("Please enter a valid number :");
            }
        }while (!isInt || (value < min || value > max));

        return value;
    }

    private static void showStats(Animal animal, Animal target) {
        animal.printStats();
        target.printStats();
        pressKeyToGoBack();
    }

    public static void printAttacks(Animal animal){
        ArrayList<IAttack> attacks = animal.getAttacks();
        for (var i = 0; i < attacks.size(); i++) {
            IAttack attaque = attacks.get(i);
            System.out.printf("%d: %s [%s]%n", i, attaque.getAttackName(), attaque.getDescription());
        }
    }



    public static void separator(){
        System.out.println("---------------");
    }

    public static void printAllAttacks() {
        ArrayList<IAttack> allAttacks = AttackFactory.getAllAttacks();
        for (int i = 1; i < allAttacks.size(); i++) {
            IAttack attack = allAttacks.get(i);
            System.out.printf("%d: %s [%s]%n", i, attack.getAttackName(), attack.getDescription());
        }
    }

    public static void printAllAnimals(){
        var allAnimals = new ArrayList<Animal>();
        for (AnimalKind animalKind:AnimalKind.values()) {
            for(ElementType elementType:ElementType.values()){
                allAnimals.add(AnimalFactory.CreateAnimal(animalKind, elementType));
            }
        }

        for(Animal animal : allAnimals){
            Map<StatID, Float> stats = animal.getStats();
            var infos = String.format("** %s **%nStats :%n", animal.getName());
            for (int i = 0; i < StatID.values().length; i++) {
                infos += String.format(" %s: %d%n", StatID.values()[i], Math.round(100*stats.get(StatID.values()[i])));
            }
            infos += "Attacks :";
            System.out.println(infos);
            printAttacks(animal);
            System.out.println();
        }
    }

    private static void printAllAnimalKind(){
        for (AnimalKind animalKind:AnimalKind.values()){
            var infos = String.format("** %s **%n", animalKind.name());
            infos += String.format("Descripton : %s%n", animalKind.getDescription());
            infos += String.format("Max Health : %d%n", Math.round(animalKind.getMaxHealth()));
            infos += String.format("Attack : %d%n", Math.round(100*animalKind.getAttack()));
            infos += String.format("Defense : %d%n", Math.round(100*animalKind.getDefense()));
            infos += String.format("Speed : %d%n", Math.round(100*animalKind.getSpeed()));
            System.out.println(infos);
        }
    }

    private static void printAllElementalTypes(){
        for(ElementType elementType: ElementType.values()){
            var infos = String.format("** %s **%n", elementType.name());
            infos += String.format("Health variation : %d%s%n", Math.round(elementType.getHealthVariation()*100), "%");
            infos += String.format("Attack variation : %d%s%n", Math.round(elementType.getAttackVariation()*100), "%");
            infos += String.format("Defense variation : %d%s%n", Math.round(elementType.getDefenseVariation()*100), "%");
            infos += String.format("Speed variation : %d%s%n", Math.round(elementType.getSpeedVariation()*100), "%");
            System.out.println(infos);
        }
    }

    private static Animal whichIsFaster(){
        var a1 = theAnimals.get(0);
        var a2 = theAnimals.get(1);
        return getSpeed(a1) >= getSpeed(a2) ? a1 : a2;

    }
    private static float getSpeed(Animal animal){
        return (animal.getStats().get(StatID.SPEED) * animal.getStatAlteration(StatID.SPEED));
    }

    private static void clearConsole(){
        System.out.flush();
        System.out.print("\033[H\033[2J");
    }

    private static void pressKeyToGoBack(){
        System.out.println("press any key to go back...");
        new Scanner(System.in).nextLine();
    }

    private static void delay(){
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){

        }
    }
}

