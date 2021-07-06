package Animal.Creation.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Status.Abstract.IStatus;
import Animal.Creation.Abstract.IAnimal;
import Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Animal.Behaviors.DefendBehavior.Concrete.SimpleDefendBehavior;
import Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;
import Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Animal implements IAnimal {

    // ** Behaviors **
    IPerformAttackBehavior attackBehavior;

    /**
     * Set the attack behavior, prioritizing any behavior that is not the standard one.
     * @param newAttackBehavior AttackBehavior to add.
     */
    public void setAttackBehavior(IPerformAttackBehavior newAttackBehavior){
        if(this.attackBehavior == null ||
        (this.attackBehavior.getClass() == SimpleAttackBehavior.class && (newAttackBehavior.getClass() != SimpleAttackBehavior.class))){
            this.attackBehavior = newAttackBehavior;
        }

    }
    IDieBehavior dieBehavior;

    /**
     * Set the die behavior, prioritizing any behavior that is not the standard one.
     * @param newDieBehavior DieBehavior to add.
     */
    public void setDieBehavior(IDieBehavior newDieBehavior) {
        if(this.dieBehavior == null ||
                (this.dieBehavior.getClass() == SimpleDieBehavior.class && (newDieBehavior.getClass() != SimpleDieBehavior.class))) {
            this.dieBehavior = newDieBehavior;
        }
    }

    IDefendBehavior defendBehavior;
    public void setDefendBehavior(IDefendBehavior newDefendBehavior){
        if(this.defendBehavior == null ||
                (this.defendBehavior.getClass() == SimpleDefendBehavior.class && (newDefendBehavior.getClass() != SimpleDefendBehavior.class))) {
            this.defendBehavior = newDefendBehavior;
        }
    }

    //** Attacks **
    private ArrayList<IAttack> attacks = new ArrayList<>();
    public void addAttack(IAttack attack){
        if(!attacks.contains(attack)){
            attacks.add(attack);
        }

    }
    public ArrayList<IAttack> getAttacks(){
        return (ArrayList<IAttack>) attacks.clone();
    }

    // ***** Stats and states *****
    private float health;
    @Override
    public int getHealth() {
        return Math.round(health);
    }
    @Override
    public void setHealth(float health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return Math.round(stats.get(StatID.MAX_HEALTH));
    }

    @Override
    public boolean isAlive() {
        return dieBehavior.isAlive();
    }
    private boolean canAct = true;
    @Override
    public boolean canAct(){
        return canAct;
    }
    @Override
    public void canAct(boolean allow) {
        canAct = allow;
    }
    private Map<StatID, Float> stats = new HashMap<>();
    /**
     * Return a clone of the stats values.
     * @return clone of the stats values.
     */
    @Override
    public  Map<StatID, Float> getStats(){
        Map<StatID, Float> clonedStats = new HashMap<>();
        for (var i = 0; i < StatID.values().length; i++) {
            clonedStats.put(StatID.values()[i], this.stats.get(StatID.values()[i]));
        }
        return clonedStats;
    }

    private Map<StatID, Float> statAlterations = new HashMap<>();
    /**
     * Return a clone of the stat alterations dictionnary.
     * @return clone of the stat alterations dictionnary.
     */
    @Override
    public  Map<StatID, Float> getStatAlterations(){
        Map<StatID, Float> clonedStatAlt = new HashMap<StatID, Float>();
        for (var i = 0; i < StatID.values().length; i++) {
            clonedStatAlt.put(StatID.values()[i], this.statAlterations.get(StatID.values()[i]));
        }
        return clonedStatAlt;
    }

    private ActMode currentMode = ActMode.ATTACK;

    //Statuses (poison...)
    private ArrayList<IStatus> statuses = new ArrayList<>();
    public ArrayList<IStatus> getStatuses() {
        return statuses;
    }

    //Other infos
    private String name;
    public String getName() {
        return name;
    }

    /**
     * Set the act mode.
     * @param actMode ActMode enumerator.
     */
    @Override
    public void setActMode(ActMode actMode) {
        currentMode = actMode;
    }

    /**
     * Get the current act mode.
     * @return current act mode.
     */
    @Override
    public ActMode getActMode() {
        return currentMode;
    }

    /**
     * Modify the name.
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }


    // ****** Constructors ******

    /**
     * Constructor of the animal, without a name.
     * @param maxHealth Maximum health of the animal.
     * @param attackStat Base attack of the animal.
     * @param defenseStat Base defense of the animal.
     */
    public Animal(int maxHealth, float attackStat, float defenseStat) {
        this("Unamed Animal", maxHealth, attackStat, defenseStat);
    }

    /**
     * Full constructor of the animal.
     * @param name Name of the animal.
     * @param maxHealth Maximum health of the animal.
     * @param attackStat Base attack of the animal.
     * @param defenseStat Base defense of the animal.
     */
    public Animal(String name, float maxHealth, float attackStat, float defenseStat){
        this.health = maxHealth;
        stats.put(StatID.MAX_HEALTH, maxHealth);
        stats.put(StatID.ATTACK, attackStat);
        stats.put(StatID.DEFENSE, defenseStat);
        stats.put(StatID.ACCURACY, 100f);
        statAlterations.put(StatID.MAX_HEALTH, 1f);
        statAlterations.put(StatID.ATTACK, 1f);
        statAlterations.put(StatID.DEFENSE, 1f);
        statAlterations.put(StatID.ACCURACY, 1f);
        this.name = name;
    }

    //Action methods
    /**
     * Choose an attack.
     * @param index index between 1 and 4
     * @return The attack.
     */
    public IAttack chooseAttack(int index){
        return attacks.get(index-1);
    }

    /**
     * Execute the attack behavior.
     * @param target Target of the attack.
     * @param attack Attack to peform.
     */
    @Override
    public void attack(IAnimal target, IAttack attack) {
        attackBehavior.attack(target, attack, stats.get(StatID.ATTACK)*statAlterations.get(StatID.ATTACK));
    }

    /**
     * Performs a defense move : Change its mode to defense.
     */
    @Override
    public void defend() {
        currentMode = ActMode.DEFENSE;
    }


    // Reaction
    /**
     * Reacts upon an attack depending on the defend behavior.
     * @param damage Damage the foe is trying to inflict.
     */
    @Override
    public void attacked(IAttack attack, int damage) {
        defendBehavior.defend(attack, damage);
    }

    /**
     * Loses HP due to an attack or a status.
     * @param damage damage taken.
     */
    @Override
    public void hurt(int damage){
        if(damage > health) damage = Math.round(health);
        health -= damage;
        checkIfDead();
    }

    /**
     * Adds a status to the animal.
     * @param status Status to add.
     */
    @Override
    public void addStatus(IStatus status) {
        statuses.add(status);
    }

    /**
     * Removes a status to the animal.
     * @param status Status to remove
     */
    @Override
    public void removeStatus(IStatus status){
        statuses.remove(status);
    }

    /**
     * Alter a stat. Ex: Stat.ATTACK, 0.5 will lower the attack stat by a half.
     * To retablish the stat, enter 1.
     * @param statID Stat to alter
     * @param amount Floating number by which the stat must be multiplied, or 1 to retablish it.
     */
    @Override
    public void alterStat(StatID statID, float amount) {
        if(amount == 1){
            statAlterations.put(statID, amount);
            System.out.printf("%s's %s was restored.%n", name, statID.name().toLowerCase(Locale.ROOT));
        }
        else{
            statAlterations.put(statID, statAlterations.get(statID)*amount);
            System.out.printf("%s's %s was lowered%n", name, statID.name().toLowerCase(Locale.ROOT));
        }
    }

    private void checkIfDead(){
        if(health <=0){
            dieBehavior.die();
        }
    }

    /**
     * Do what needs to be done after the end of a turn :
     * Consumes the effects of the inflicted statuses.
     */
    public void endOfTurn(){
        for(var i=0; i < statuses.size(); i++){
            statuses.get(i).consumeEffect();
        }
    }

    public void printStats(){
        System.out.printf("%s's stats :%n" +
                        "Health : %d/%d%n" +
                        "Attack : %d%n" +
                        "Defense : %d%n%n",
                name,
                Math.round(health),getMaxHealth(),
                Math.round(stats.get(StatID.ATTACK)*statAlterations.get(StatID.ATTACK)*100),
                Math.round(stats.get(StatID.DEFENSE)*statAlterations.get(StatID.DEFENSE)*100)
        );
    }

}
