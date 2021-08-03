package Controler;

import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.AnimalFactory;
import Model.Animal.Creation.Concrete.AnimalKind;
import Model.Animal.Creation.Concrete.ElementType;
import Model.playerAI.Concrete.Player;
import View.MenuFrame;

import javax.swing.*;
import java.util.ArrayList;

public class c_Menu {

    ArrayList<Player> players;
    ArrayList<Player> getPlayers(){return players;}
    public void addPlayer(Player player){
        players.add(player);
    }

    JFrame currentFrame;
    public c_Menu(){
        currentFrame = new MenuFrame(this);
        players = new ArrayList<>();
    }

    public IAnimal createAnimal(Player player, Player foe, AnimalKind animalKind, String nickname, ElementType... elementType) {
        Animal animal;
        if(nickname.equals("") || nickname == null) animal = AnimalFactory.CreateAnimal(animalKind, elementType);
        else  animal = AnimalFactory.CreateAnimal(animalKind, nickname, elementType);

        player.setAlly(animal);
        foe.setFoe(animal);
        players.add(player);
        return animal;
    }

    public IAnimal createRandomAnimal(Player player, Player foe){
        IAnimal animal = AnimalFactory.CreateRandomAnimal();
        player.setAlly(animal);
        foe.setFoe(animal);
        return animal;
    }
//
//    public IAnimal getAnimalA() {
//        return animalA;
//    }
//
//    public IAnimal getAnimalB() {
//        return animalB;
//    }

    public boolean areAnimalsInitiated(){
        for (Player player: players) {
            if(player.getAlly() == null) return false;
        }
        return true;
    }

    public Player getFoe(Player player) {
        return player.equals(players.get(0)) ? players.get(1):players.get(0);
    }
}
