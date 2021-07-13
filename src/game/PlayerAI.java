package game;

import Animal.Creation.Concrete.Animal;
import Animal.Creation.Concrete.AnimalFactory;

public class PlayerAI {
    Animal animal;

    public PlayerAI(Animal animal, Animal opponent){
        this.animal = animal;
    }

    private void chooseAnimal(){
        animal = AnimalFactory.CreateRandomAnimal();
    }

    private void chooseMove(){
        //todo
    }
}
