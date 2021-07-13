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
    private int turn = 1;

    //public PlayerAI(IStrategy strategy){
//        this(strategy, null, null);
//    }

    public PlayerAI(IAnimal ally, IAnimal foe){
        this.allyAnimal = ally;
        this.foesAnimal = foe;
        this.currentStrategy = new WiseStrategy(this);
    }

    public void setStrategy(IStrategy strategy){
        currentStrategy =  strategy;
    }

    public void performMove(){
        changeStrategy();
        IAttack chosenAttack = currentStrategy.chooseAttack();
        allyAnimal.attack(foesAnimal, chosenAttack);
        turn++;
    }

    private void changeStrategy() {
        //todo
//
//        currentStrategy = new WiseStrategy(this);
    }




}
