package Model.Action.Heal;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

public class NoHealBehavior implements IHealBehavior{
    private IAttack attack;

    public NoHealBehavior(){

    }
    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }

    @Override
    public void execute(IAnimal target) {
    }

    @Override
    public int score(IAnimal target) {
        return 0;
    }
}
