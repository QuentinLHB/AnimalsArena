package Model.Animal.Behaviors.DefendBehavior.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Attack.Concrete.Attack;
import Model.Action.DoDamage.SimpleDoDamageBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import View.BufferedText;

public class MirrorDefenseBehavior extends Defend_Base implements IDefendBehavior {

//    private static final String MIROR_MOVE_NAME = "Mirror Defense";
    private final int fractionOfDamageMirrored;

    /**
     * Constructor of the Defense Behavior :
     * If the animal is defending, the foe will be given part of its damage in return.
     * @param animal Model.Animal concerned by this behavior
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
        int healthBeforeAttack = animal.getHealth();
        super.defend(attack, damage);
        var dmg = healthBeforeAttack-animal.getHealth();
        if(animal.getActMode().equals(ActMode.DEFENSE) && dmg > 0) {
//            animal.attack(attack.getAttackOwner(),
//                    new Attack(animal, null, 1f,
//                            new SimpleDoDamageBehavior(attack.getDamageBase()/ fractionOfDamageMirrored)));


            attack.getAttackOwner().hurt(dmg/fractionOfDamageMirrored);
            BufferedText.addBufferedText(String.format("%d damages were sent back to %s due to %s's ability.%n",
                    dmg,
                    attack.getAttackOwner(),
                    animal));
        }
    }

    @Override
    public DefendBehaviorEnum getDefendBhvEnum() {
        return DefendBehaviorEnum.MIRRORDEFEND_BEHAVIOR;
    }

    @Override
    public String getDescription() {
        return DefendBehaviorEnum.MIRRORDEFEND_BEHAVIOR.getDescription();
    }
}
