package Action.Attack.Abstract;

import Action.InflictStatus.Abstract.IInflictStatus;
import Action.Status.Abstract.IStatus;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;
import Animal.Creation.Concrete.StatID;

import java.util.Map;

public interface IAttack {
    IAnimal getAttackOwner();
    void setAttackOwner(IAnimal attackOwner);
    void performAttack(IAnimal target, float damage);
    String getAttackName();
    int getDamageBase();
    float getAccuracy();
    String getDescription();
    boolean isSelfInflicting();
    StatusID getStatusInflicted();
    Map<StatID, Float> getStatAlterations();
}
