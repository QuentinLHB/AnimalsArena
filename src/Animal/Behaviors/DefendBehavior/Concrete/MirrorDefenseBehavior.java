package Animal.Behaviors.DefendBehavior.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Attack.Concrete.Attack;
import Action.DoDamage.DoNoDamageBehavior;
import Action.DoDamage.SimpleDoDamageBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;

public class MirrorDefenseBehavior extends Defend_Base implements IDefendBehavior {

    private static final String MIROR_MOVE_NAME = "Mirror Defense";
    private final int fractionOfDamageMirrored;

    /**
     * Constructor of the Defense Behavior :
     * If the animal is defending, the foe will be given part of its damage in return.
     * @param animal Animal concerned by this behavior
     * @param fractionOfDamageMirrored fraction of the damage given back to the enemy
     */
    public MirrorDefenseBehavior(IAnimal animal, int fractionOfDamageMirrored){
        super(animal);
        this.fractionOfDamageMirrored = fractionOfDamageMirrored;
    }

    /**
     * Send back part of the damage to the foe if the ally is defending.
     * @param attack attack to defend
     * @param damage damage dealt.
     */
    @Override
    public void defend(IAttack attack, int damage) {

        super.defend(attack, damage);
        if(animal.getActMode().equals(ActMode.DEFENSE) && !attack.getAttackName().equals(MIROR_MOVE_NAME)) {
            animal.attack(attack.getAttackOwner(),
                    new Attack(animal, MIROR_MOVE_NAME, 1f,
                            new SimpleDoDamageBehavior(attack.getDamageBase()/ fractionOfDamageMirrored)));
        }
    }
}
