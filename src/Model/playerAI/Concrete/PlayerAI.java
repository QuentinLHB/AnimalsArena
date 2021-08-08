package Model.playerAI.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Creation.Concrete.Animal;
import Model.playerAI.Abstract.IStrategy;
import Model.Util.Position;

public class PlayerAI extends Player {

    private IStrategy currentStrategy;

    public static int cpuId;


    public IAnimal getAllyAnimal() {
        return ally;
    }

    public IAnimal getFoesAnimal() {
        return foe;
    }


    public PlayerAI(Position position) {
        super(position, null, null);
    }

    public PlayerAI(Position position, Animal ally, Animal foe){
        super(position, ally, foe);
        this.currentStrategy = new WiseStrategy(this);
    }

    public void performMove(){
        IAttack chosenAttack = currentStrategy.chooseAttack();
        ally.attack(foe, chosenAttack);
    }

    public IAttack chooseAttack(){
        if(currentStrategy == null){
            currentStrategy = new WiseStrategy(this); //todo mauvaise pratique : changer la façon d'initialier PlayerAI dans la view
        }
        return currentStrategy.chooseAttack();
    }

    @Override
    public boolean isBot() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " (CPU)";
    }
}
