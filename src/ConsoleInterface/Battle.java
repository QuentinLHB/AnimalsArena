package ConsoleInterface;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Concrete.PlayerAI;

import java.util.ArrayList;
import java.util.Scanner;

import static ConsoleInterface.DisplayTools.*;

public class Battle {


    static Animal battle(PlayerAI playerA, PlayerAI playerB){
        var animalA = Main.animalA;
        var animalB = Main.animalB;

        do {
            turn(playerA, playerB);
        }while (animalA.isAlive() && animalB.isAlive());

        if(animalA.isAlive()){
            System.out.printf("%s wins ! %n", animalA.getName());
            return animalA;
        }
        else if(animalB.isAlive()){
            System.out.printf("%s wins ! %n", animalB.getName());
            return animalB;
        }
        else System.out.println("Double KO !");
        return null;
    }


    static void turn(PlayerAI playerA, PlayerAI playerB){

        System.out.println("Tour n°" + Main.turns + "\n");

        var animalA = Main.animalA;
        var animalB = Main.animalB;

        if(whichIsFaster() == animalA){
            if(playerA == null) performAction(animalA, animalB);
            else playerA.performMove();
            nextLine();
            delay();
            if(playerB == null) performAction(animalB, animalA);
            else playerB.performMove();
            nextLine();
            delay();

        }
        else {
            if(playerB == null) performAction(animalB, animalA);
            else playerB.performMove();
            nextLine();
            delay();
            if(playerA == null) performAction(animalA, animalB);
            else playerA.performMove();
            nextLine();
            delay();
        }


        animalA.endOfTurn();
        animalB.endOfTurn();

        delay();
        nextLine();
        separator();
        nextLine();
        printHP(animalA);
        printHP(animalB);
        separator();
        separator();
        nextLine();
        delay();

        Main.turns++;
    }

    public static void performAction(Animal animal, Animal target){
        if(!animal.canAct()) {
            System.out.printf("%s can't act.%n", animal.getName());
            return;
        }
        ArrayList<IAttack> attacks = animal.getAttacks();
        var scanner = new Scanner(System.in);
        int choice;
        System.out.printf("%s's actions :%n", animal.getName());
        printAttacks(animal);
        System.out.println("(i: Display stats)");
        do {
            System.out.println(">> ");
            String stringChoice = scanner.nextLine();
            try{
                choice = Integer.parseInt(stringChoice);
            }catch (Exception e){
                choice = -1;
                if(stringChoice.equals("i") || stringChoice.equals("I")){
                    printStats(animal, target);
                    printAttacks(animal);
                }
            }

        }while (choice < 0 || choice > attacks.size()-1);

        animal.attack(target, animal.chooseAttack(choice));



    }

}
