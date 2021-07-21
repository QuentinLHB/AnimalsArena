package game;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Concrete.Animal;
import playerAI.Concrete.PlayerAI;

import java.util.ArrayList;
import java.util.Scanner;

import static game.DisplayTools.*;

public class Battle {


    static void battle(PlayerAI playerA, PlayerAI playerB){
        var animalA = Main.animalA;
        var animalB = Main.animalB;

        do {
            turn(playerA, playerB);
        }while (animalA.isAlive() && animalB.isAlive());

        if(animalA.isAlive()){
            System.out.printf("%s wins ! %n", animalA.getName());
        }
        else if(animalB.isAlive()){
            System.out.printf("%s wins ! %n", animalB.getName());
        }
        else System.out.println("Double KO !");
    }


    static void turn(PlayerAI playerA, PlayerAI playerB){

        System.out.println("Tour n°" + Main.turns + "\n");

        var animalA = Main.animalA;
        var animalB = Main.animalB;

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

        animalA.endOfTurn();
        animalB.endOfTurn();

        delay();
        printHP(animalA);
        printHP(animalB);
        delay();
        separator();
        separator();
        System.out.println("");

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
                    printStats(animal, target);
                }
            }

        }while (choice < 0 || choice > attacks.size()-1);

        animal.attack(target, animal.chooseAttack(choice));
        delay();

        separator();
    }

}
