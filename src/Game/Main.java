package Game;
import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.Attack.Concrete.AttackEnum;
import Action.Attack.Concrete.AttackFactory;
import Animal.Behaviors.BehaviorFactory;
import Animal.Behaviors.DefendBehavior.Concrete.DefendBehaviorEnum;
import Animal.Behaviors.DieBehavior.Concrete.DieBehaviorEnum;
import Animal.Behaviors.PeformAttackBehavior.Concrete.AttackBehaviorEnum;
import Animal.Creation.Concrete.*;

import java.util.*;

public class Main {
    public static int nbTour = 1;

    public static List<Animal> theAnimals = new ArrayList<Animal>();
    public static List<Animal> customAnimals = new ArrayList<Animal>();

    public static void main(String[] args) {

        MainMenu();


    }

    public static void MainMenu(){
        String menuDisplay = """
                        Welcome in Animals Arena, where you can experience RPG-like fights. Please select the mode you'd like :                                
                        1: Player vs Player : Each player composes its animal for an intense fight !
                        2: Player vs AI : Compose your animal and fight against a computer ! (Soon)
                        3: Consult animals and attacks' statistics.
                        4: Create your custom animal.
                        0 : Exit the program.""";

        System.out.println(menuDisplay);

        int value = getIntInputFromUser(0, 4);
        
        switch (value){
            case 1 -> StartPVP();
            case 2 -> StartPVE();
            case 3 -> ConsultInfos();
            case 4 -> Customize();
            default -> System.exit(0);
        }
        
        
    }

    private static void Customize() {
        customAnimals.add(createCustomAnimal());
    }

    private static void ConsultInfos() {
    }

    private static void StartPVE() {
    }

    public static void StartPVP(){
        System.out.println("Player 1:\n");
        theAnimals.add(createAnimal());
        theAnimals.get(0).printStats();

        clearConsole();

        System.out.println("Player 2:\n");
        theAnimals.add(createAnimal());
        theAnimals.get(1).printStats();

        clearConsole();

        battle();
    }

    public static Animal createCustomAnimal(){
        // 1. Choose stats
        EnumMap<StatID, Integer> chosenStats = new EnumMap<>(StatID.class);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name your animal :");
        String name = scanner.nextLine();
        for(StatID statID: StatID.values()){
            System.out.printf("Choose the %s stat value (100 being the basis)%n", statID.name().toLowerCase(Locale.ROOT));
            int value = getIntInputFromUser(0, 200);
            chosenStats.put(statID, value);
        }
        Animal customAnimal = new Animal(
                name,
                chosenStats.get(StatID.MAX_HEALTH),
                (float)chosenStats.get(StatID.ATTACK)/100,
                (float)chosenStats.get(StatID.DEFENSE)/100,
                (float) chosenStats.get(StatID.SPEED));

        customAnimal.printStats();

        //2. Add 4 attacks
        printAllAttacks();
        for (int i = 1; i < 5; i++) {
            System.out.println("Add attack n°" + i);
            int choice = getIntInputFromUser(1, AttackEnum.values().length);

            customAnimal.addAttack(AttackFactory.createAttack(customAnimal, AttackEnum.values()[choice-1]));
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

            int counter = 1;
            System.out.println("Attack behaviors :");
            for (AttackBehaviorEnum attackBehavior: AttackBehaviorEnum.values()) {
                System.out.printf("%d: %s - %s%n", counter++, attackBehavior.getName(), attackBehavior.getDescription());
            }
            choice = getIntInputFromUser(1, AttackBehaviorEnum.values().length);
            chosenAttackBehavior = AttackBehaviorEnum.values()[choice-1];

            counter = 1;
            System.out.println("Defense behaviors :");
            for (DefendBehaviorEnum defendBehaviorEnum: DefendBehaviorEnum.values()) {
                System.out.printf("%d: %s - %s%n", counter++, defendBehaviorEnum.getName(), defendBehaviorEnum.getDescription());

            }
            choice = getIntInputFromUser(1, DefendBehaviorEnum.values().length);
            chosenDefendBehavior = DefendBehaviorEnum.values()[choice-1];

            counter = 1;
            System.out.println("Death behaviors :");
            for (DieBehaviorEnum defendBehaviorEnum: DieBehaviorEnum.values()) {
                System.out.printf("%d: %s - %s%n", counter++, defendBehaviorEnum.getName(), defendBehaviorEnum.getDescription());
            }
            choice = getIntInputFromUser(1, DieBehaviorEnum.values().length);
            chosenDieBehavior = DieBehaviorEnum.values()[choice-1];

            BehaviorFactory.addBehaviors(customAnimal, chosenAttackBehavior, chosenDefendBehavior, chosenDieBehavior);
        }
        return customAnimal;
    }


    public static Animal createAnimal(){
        Scanner scanner = new Scanner(System.in);
        int chosenValue;
        AnimalKind animalKind;
        ElementType elementType;

        // Choose animal
        System.out.println("Please choose one of the following animals :");
        for (int i = 0; i < AnimalKind.values().length; i++) {
            System.out.printf("%d: %s [%s]%n", i+1, AnimalKind.values()[i], AnimalKind.values()[i].getDescription());
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

        if(!nickname.equals("0")){
             return AnimalFactory.CreateAnimal(animalKind, elementType, nickname);
        }
        else return AnimalFactory.CreateAnimal(animalKind, elementType);


    }

    public static void battle(){
        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);

        do {
            Turn();
        }while (animalA.isAlive() && animalB.isAlive());

        if(animalA.isAlive()){
            System.out.printf("%s wins ! ", animalA.getName());
        }
        else{
            System.out.printf("%s wins ! ", animalB.getName());
        }
    }

    public static void printLives(Animal animal){
        System.out.println(animal.getName() + " : " + animal.getHealth() + " / "+ animal.getMaxHealth());
    }

    public static void Turn(){

        System.out.println("Tour n°" + nbTour + "\n");

        var animalA = theAnimals.get(0);
        var animalB = theAnimals.get(1);


        if(whichIsFaster() == animalA){
            performAction(animalA, animalB);
            performAction(animalB, animalA);
        }
        else {
            performAction(animalB, animalA);
            performAction(animalA, animalB);
        }

        for (Animal animal:theAnimals) {
            animal.endOfTurn();
        }

        printLives(animalA);
        printLives(animalB);
        separator();
        separator();
        System.out.println("");

        nbTour++;
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
            System.out.println("0: Defend");
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

        if(choice == 0) {
            animal.defend();
        }
        else{
            animal.attack(target, animal.chooseAttack(choice));
        }

        separator();
    }

    public static int getIntInputFromUser(int min, int max){
        boolean isInt;
        int value = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println(">>");
            String stringValue = scanner.nextLine();
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
        System.out.println("press any key to go back...");
        new Scanner(System.in).nextLine();
    }

    public static void printAttacks(Animal animal){
        ArrayList<IAttack> attacks = animal.getAttacks();
        for (var i = 0; i < attacks.size(); i++) {
            IAttack attaque = attacks.get(i);
            System.out.printf("%d: %s [%s]%n", i+1, attaque.getAttackName(), attaque.getDescription());
        }
    }

    public static void separator(){
        System.out.println("---------------");
    }

    public static void printAllAttacks() {
        ArrayList<Attack> allAttacks = AttackFactory.getAllAttacks();
        int counter = 1;
        for (Attack attack : allAttacks) {
            System.out.printf("%d: %s [%s]%n", counter++, attack.getAttackName(), attack.getDescription());
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
            String infos = String.format("** %s **%nStats :%n", animal.getName());
            for (int i = 0; i < StatID.values().length; i++) {
                infos += String.format(" %s: %d%n", StatID.values()[i], Math.round(100*stats.get(StatID.values()[i])));
            }
            infos += "Attacks :";
            System.out.println(infos);
            printAttacks(animal);
            System.out.println();
        }
    }

    private static Animal whichIsFaster(){
        var a1 = theAnimals.get(0);
        var a2 = theAnimals.get(1);
        return getSpeed(a1) >= getSpeed(a2) ? a1 : a2;

    }
    private static float getSpeed(Animal animal){
        return (animal.getStats().get(StatID.SPEED) * animal.getStatAlterations().get(StatID.SPEED));
    }

    private static void clearConsole(){
        System.out.flush();
        System.out.print("\033[H\033[2J");
    }

//    private static void waitHalfASec(){
//
//    }
}

