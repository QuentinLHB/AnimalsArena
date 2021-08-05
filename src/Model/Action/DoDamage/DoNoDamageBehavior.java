package Model.Action.DoDamage;

import Model.Action.Attack.Abstract.IAttack;
import Model.Animal.Creation.Abstract.IAnimal;

public class DoNoDamageBehavior implements IDoDamageBehavior{

    IAttack attack;
    public DoNoDamageBehavior(){
            }

    @Override
    public IAttack getAttack() {
        return null;
    }

    @Override
    public void execute(IAnimal foe) {
    }

    @Override
    public int score(IAnimal target) {
        return 0;
    }

    @Override
    public int getDamageBase() {
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
