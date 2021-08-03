package Controler;

import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.Animal.Creation.Concrete.AnimalFactory;
import Model.Animal.Creation.Concrete.AnimalKind;
import Model.Animal.Creation.Concrete.ElementType;
import View.MenuFrame;
import View.PlayersEnum;

import javax.swing.*;

public class c_Menu {

    private IAnimal animalA;
    private IAnimal animalB;

    JFrame currentFrame;
    public c_Menu(){
        currentFrame = new MenuFrame(this);
    }

    public IAnimal createAnimal(PlayersEnum player, AnimalKind animalKind, String nickname, ElementType... elementType) {
        Animal animal;
        if(nickname.equals("") || nickname == null) animal = AnimalFactory.CreateAnimal(animalKind, elementType);
        else  animal = AnimalFactory.CreateAnimal(animalKind, nickname, elementType);

        switch (player){
            case playerA -> animalA = animal;
            case playerB -> animalB = animal;
        }
        return animal;
    }

    public IAnimal createRandomAnimal(PlayersEnum player){
        IAnimal animal = AnimalFactory.CreateRandomAnimal();
        switch (player){
            case playerA -> animalA = animal;
            case playerB -> animalB = animal;
        }
        return animal;
    }

    public IAnimal getAnimalA() {
        return animalA;
    }

    public IAnimal getAnimalB() {
        return animalB;
    }

    public boolean areAnimalsInitiated(){
        return (animalA != null && animalB != null);
    }
}
