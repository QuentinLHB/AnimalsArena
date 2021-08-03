package Controler;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.StatID;

public class c_Battle {

    private IAnimal animalA;
    private IAnimal animalB;


    public c_Battle(c_Menu menuControler){
        animalA = menuControler.getAnimalA();
        animalB = menuControler.getAnimalB();
    }

    public String getHP(IAnimal animal){
        return String.format("%d / %d", animal.getHealth(), Math.round(animal.getMaxHealth()*animal.getStatAlteration(StatID.MAX_HEALTH)));
    }

    public IAnimal getAnimalA() {
        return animalA;
    }

    public IAnimal getAnimalB() {
        return animalB;
    }

    public void executeAttack(IAttack attack, IAnimal animalB) {
        //...
    }
}
