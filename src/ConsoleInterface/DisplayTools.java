package ConsoleInterface;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.AttackFactory;
import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Concrete.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class DisplayTools {

    static void printHP(Animal animal){
        String statuses ="";
        for (IStatus status :
                animal.getStatuses()) {
            statuses += String.format(" [%s] ", status.getStatusName().toUpperCase(Locale.ROOT));
        }
        System.out.printf("%s %s : %d / %d%n", animal.getName(), statuses, animal.getHealth(),Math.round(animal.getMaxHealth()));
    }

    static void printStats(Animal animal, Animal target) {
        System.out.println(animal.getStatDisplay());
        System.out.println(target.getStatDisplay());
        pressKeyToGoBack();
    }





    static void printAttacks(Animal animal){
        ArrayList<IAttack> attacks = animal.getAttacks();
        for (var i = 0; i < attacks.size(); i++) {
            IAttack attaque = attacks.get(i);
            System.out.printf("%d: %s [%s]%n", i, attaque.getAttackName(), attaque.getDescription());
        }
    }



    static void separator(){
        System.out.println("---------------");
    }

    static void printAllAttacks() {
        ArrayList<IAttack> allAttacks = AttackFactory.getAllAttacks();
        for (int i = 1; i < allAttacks.size(); i++) {
            IAttack attack = allAttacks.get(i);
            System.out.printf("%d: %s [%s]%n", i, attack.getAttackName(), attack.getDescription());
        }
    }

    static void printAllAnimals(){
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

    static void printAllAnimalKind(){
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

    static void printAllElementalTypes(){
        for(ElementType elementType: ElementType.values()){
            var infos = String.format("** %s **%n", elementType.name());
            infos += String.format("Health variation : %d%s%n", Math.round(elementType.getHealthVariation()*100), "%");
            infos += String.format("Attack variation : %d%s%n", Math.round(elementType.getAttackVariation()*100), "%");
            infos += String.format("Defense variation : %d%s%n", Math.round(elementType.getDefenseVariation()*100), "%");
            infos += String.format("Speed variation : %d%s%n", Math.round(elementType.getSpeedVariation()*100), "%");
            System.out.println(infos);
        }
    }

    /**
     * Loops until a valid number is given by the user.
     * @param min Min value
     * @param max Max value
     * @return User input
     */
    static int getIntInputFromUser(int min, int max){
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

    static Animal whichIsFaster(){
        return getSpeed(Main.animalA) >= getSpeed(Main.animalB) ? Main.animalA : Main.animalB;

    }
    static float getSpeed(Animal animal){
        return (animal.getStats().get(StatID.SPEED));
    }

    static void clearConsole(){
        System.out.flush();
        System.out.print("\033[H\033[2J");
    }

    static void pressKeyToGoBack(){
        System.out.println("press any key to go back...");
        new Scanner(System.in).nextLine();
    }

    static void delay(){
        try{
            Thread.sleep(1500);
        } catch(InterruptedException e){

        }
    }

    static void nextLine(){
        System.out.println("");
    }


}
