package Model.Action.AlterStats;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

public class AlterNoStatBehavior implements IAlterStatsBehavior {

    private IAttack attack;

    public AlterNoStatBehavior(){
    }

    @Override
    public IAttack getAttack() {
        return attack;
    }

    @Override
    public void execute(IAnimal target) {

    }

    @Override
    public int score(IAnimal target) {
        return 0;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void setAttack(IAttack attack) {
        this.attack = attack;
    }
}
