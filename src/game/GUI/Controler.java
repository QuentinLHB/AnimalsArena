package game.GUI;

import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.AnimalFactory;
import Animal.Creation.Concrete.AnimalKind;
import Animal.Creation.Concrete.ElementType;

import javax.swing.*;

public class Controler {

    IAnimal animalA;
    IAnimal animalB;

    JFrame currentFrame;
    public Controler(){
        currentFrame = new MenuFrame(this);
    }

    public void createAnimal(PlayersEnum player, AnimalKind animalKind, String nickname, ElementType... elementType) {
        Animal animal;
        if(nickname == null) animal = AnimalFactory.CreateAnimal(animalKind, elementType);
        else  animal = AnimalFactory.CreateAnimal(animalKind, nickname, elementType);

        switch (player){
            case playerA -> animalA = animal;
            case playerB -> animalB = animal;
        }
    }
}
