package Animal;

import Damage.IDoDamage;
import Damage.IStatus;

import java.util.ArrayList;

public interface IAnimal {
    void attack(IAnimal target, IDoDamage attack);
    void defend();
    void attacked(int damage);
    void hurt(int damage);
    void die();
    void addStatus(IStatus status);
    void removeStatus(IStatus status);
    int getMaxHealth();
    ArrayList<IStatus> getStatuses();

}
