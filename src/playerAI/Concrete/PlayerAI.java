package playerAI.Concrete;

import Action.Attack.Abstract.IAttack;
import Animal.Creation.Abstract.IAnimal;
import playerAI.Abstract.IStrategy;

public class PlayerAI {

    private IStrategy currentStrategy;

    public IAnimal getAllyAnimal() {
        return allyAnimal;
    }

    private IAnimal allyAnimal;

    public IAnimal getFoesAnimal() {
        return foesAnimal;
    }

    private IAnimal foesAnimal;

    public PlayerAI(IAnimal ally, IAnimal foe){
        this.allyAnimal = ally;
        this.foesAnimal = foe;
        this.currentStrategy = new WiseStrategy(this);
    }

    public void performMove(){
        IAttack chosenAttack = currentStrategy.chooseAttack();
        allyAnimal.attack(foesAnimal, chosenAttack);
    }




}
