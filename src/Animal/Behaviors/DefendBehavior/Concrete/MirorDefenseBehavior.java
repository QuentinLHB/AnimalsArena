package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class MirorDefenseBehavior extends Defend_Base implements IDefendBehavior {

    private static final String MIROR_MOVE_NAME = "MirorDefense";
    private final int fractionOfDamageMirored;

    public MirorDefenseBehavior(IAnimal animal, int fractionOfDamageMirored){
        super(animal);
        this.fractionOfDamageMirored = fractionOfDamageMirored;
    }

    @Override
    public void defend(IAttack attack, int damage) {

        super.defend(attack, damage);
        if(!attack.getAttackName().equals(MIROR_MOVE_NAME)){
            animal.attack(attack.getAttackOwner(), new Attack(MIROR_MOVE_NAME, attack.getDamageBase()/fractionOfDamageMirored));
        }


    }
}
