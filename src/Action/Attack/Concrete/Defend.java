package Action.Attack.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Status.Concrete.StatusID;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

public class Defend implements IAttack {

    private IAnimal attackOwner;

    public Defend(){

    }
    @Override
    public IAnimal getAttackOwner() {
        return attackOwner;
    }

    @Override
    public void setAttackOwner(IAnimal attackOwner) {
        this.attackOwner = attackOwner;
    }

    @Override
    public void performAttack(IAnimal target, float damage) {
        if(attackOwner.canDefend()) attackOwner.setActMode(ActMode.DEFENSE);
    }

    @Override
    public String getAttackName() {
        return "Defend";
    }

    @Override
    public int getDamageBase() {
        return 0;
    }

    @Override
    public float getAccuracy() {
        return 100;
    }

    @Override
    public String getDescription() {
        return "Sets the current mode to Defense.";
    }

    @Override
    public boolean isSelfInflicting() {
        return true;
    }

    @Override
    public StatusID getStatusInflicted() {
        return null;
    }

    @Override
    public Map<StatID, Float> getStatAlterations() {
        return null;
    }
}
