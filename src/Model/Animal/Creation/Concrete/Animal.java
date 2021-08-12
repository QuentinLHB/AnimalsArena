package Model.Animal.Creation.Concrete;

import Model.Action.Attack.Abstract.IAttack;
import Model.Action.Status.Abstract.IStatus;
import Model.Animal.Creation.Abstract.IAnimal;
import Model.Animal.Behaviors.DefendBehavior.Abstract.IDefendBehavior;
import Model.Animal.Behaviors.DefendBehavior.Concrete.SimpleDefendBehavior;
import Model.Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;
import Model.Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Model.Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;
import Model.Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;
import Model.Util.Serialization;
import View.BufferedText;
import View.Util;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Animal implements IAnimal {


    private final int id;
    public int getId() {
        return id;
    }

    @Serial
    private static final long serialVersionUID = 3492485037626697204L;

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
    public IPerformAttackBehavior getAttackBehavior(){return attackBehavior;}

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
    public IDieBehavior getDieBehavior(){return dieBehavior;}

    IDefendBehavior defendBehavior;
    public void setDefendBehavior(IDefendBehavior newDefendBehavior){
        if(this.defendBehavior == null ||
                (this.defendBehavior.getClass() == SimpleDefendBehavior.class && (newDefendBehavior.getClass() != SimpleDefendBehavior.class))) {
            this.defendBehavior = newDefendBehavior;
        }
    }
    public IDefendBehavior getDefendBehavior(){return defendBehavior;}

    //** Attacks **
    private ArrayList<IAttack> attacks = new ArrayList<>();

    @Override
    public void addAttack(IAttack attack){
        if(!attacks.contains(attack)){
            attacks.add(attack);
        }

    }

    @Override
    public void removeAttack(IAttack attack) {
        attacks.remove(attack);
    }

    @Override
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
        int maxHealth = getMaxHealth();
        if(health > maxHealth) health = maxHealth;
        this.health = health;
    }

    /**
     * Heals a % of the animal's max HP.
     * @param amount Percentage of HP to restore (1.2 = 20%)
     */
    @Override
    public float heal(float amount) {
        if(amount <=1) return 0;
        int maxHealth = getMaxHealth();
        float hpRestored = maxHealth * (amount-1);
//
        if(health+hpRestored >= maxHealth) {
            hpRestored = maxHealth - health ;
            health = maxHealth;

        }
        else{
            health += hpRestored;
        }

        return hpRestored;
    }

    /**
     * Heals a fix amount of HP.
     * @param amount Amount of HP to add to the current health.
     */
    @Override
    public int heal(int amount) {
        if(amount <=0) return 0;
        int maxHealth = getMaxHealth();
        health += amount;
        if(health > maxHealth) {
            amount = Math.round(maxHealth - health);
            health = maxHealth;
            return amount;
        }
        return amount;
    }

    public int getMaxHealth() {
        return Math.round(stats.get(StatID.MAX_HEALTH)*statAlterations.get(StatID.MAX_HEALTH));
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
    public void canDefend(boolean allow) {
        defendBehavior.canDefend(allow);
    }

    @Override
    public boolean canDefend() {
        return defendBehavior.canDefend();
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

    @Override
    public Float getStat(StatID statID) {
        return stats.get(statID) * statAlterations.get(statID);
    }

    private Map<StatID, Float> statAlterations = new HashMap<>();

    @Override
    public Float getStatAlteration(StatID statID) {
        return statAlterations.get(statID);
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
    public Animal(int maxHealth, float attackStat, float defenseStat, float speed) {
        this("Unnamed Animal", maxHealth, attackStat, defenseStat, speed);
    }

    /**
     * Full constructor of the animal.
     * @param name Name of the animal.
     * @param maxHealth Maximum health of the animal.
     * @param attackStat Base attack of the animal.
     * @param defenseStat Base defense of the animal.
     */
    public Animal(String name, float maxHealth, float attackStat, float defenseStat, float speed){
        this.health = maxHealth;

        stats.put(StatID.MAX_HEALTH, maxHealth);
        stats.put(StatID.ATTACK, attackStat);
        stats.put(StatID.DEFENSE, defenseStat);
        stats.put(StatID.ACCURACY, 100f);
        stats.put(StatID.SPEED, speed);

        statAlterations.put(StatID.MAX_HEALTH, 1f);
        statAlterations.put(StatID.ATTACK, 1f);
        statAlterations.put(StatID.DEFENSE, 1f);
        statAlterations.put(StatID.ACCURACY, 1f);
        statAlterations.put(StatID.SPEED, 1f);

        this.id = Serialization.generateNewID();

        this.name = name;
    }

    //Model.Action methods
    /**
     * Choose an attack.
     * @param index index between 1 and 4
     * @return The attack.
     */
    public IAttack chooseAttack(int index){
        return attacks.get(index);
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
        if(canDefend()) currentMode = ActMode.DEFENSE;
        else BufferedText.addBufferedText(String.format("%s couldn't defend.%n", name));
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
        if(damage > health) health = 0;
        else health -= damage;
        BufferedText.addBufferedText(String.format("%s lost %d HP.%n", name, damage));
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
        }
        else{
            statAlterations.put(statID, statAlterations.get(statID)*amount);
            if(statAlterations.get(statID) > 2f) statAlterations.put(statID, 2f);
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

    public String getStatDisplay(){
        return String.format("Health : %d/%d%n" +
                        "Attack : %d%n" +
                        "Defense : %d%n" +
                        "Speed : %d%n",
                Math.round(health), Math.round(stats.get(StatID.MAX_HEALTH) * statAlterations.get(StatID.MAX_HEALTH)),
                Math.round(stats.get(StatID.ATTACK)*statAlterations.get(StatID.ATTACK)*100),
                Math.round(stats.get(StatID.DEFENSE)*statAlterations.get(StatID.DEFENSE)*100),
                Math.round(stats.get(StatID.SPEED)*statAlterations.get(StatID.SPEED)*100)
        );
    }

    public String getHtmlStatDisplay(){
        String display = getStatDisplay();
        display = display.replace("Health", Util.toBold("Health"));
        display = display.replace("Attack", Util.toBold("Attack"));
        display = display.replace("Speed", Util.toBold("Speed"));
        display = display.replace("Defense", Util.toBold("Defense"));
        return Util.toHtml(display);

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
