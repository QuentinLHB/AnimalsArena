package Animal.Concrete;

import Action.Attack.Abstract.IAttack;
import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;
import Animal.Behaviors.DieBehavior.Abstract.IDieBehavior;
import Animal.Behaviors.DieBehavior.Concrete.SimpleDieBehavior;
import Animal.Behaviors.PeformAttackBehavior.Abstract.ActMode;
import Animal.Behaviors.PeformAttackBehavior.Abstract.IPerformAttackBehavior;
import Animal.Behaviors.PeformAttackBehavior.Concrete.SimpleAttackBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Animal implements IAnimal {

    // Behaviors
    IPerformAttackBehavior attackBehavior;
    public void setAttackBehavior(IPerformAttackBehavior attackBehavior){
        if(this.attackBehavior == null ||
        (this.attackBehavior.getClass() == SimpleAttackBehavior.class && (attackBehavior.getClass() != SimpleAttackBehavior.class))){
            this.attackBehavior = attackBehavior;
            System.out.printf("Une nouvelle attack behavior a été ajoutée à %s%n", this.getName()); //todo à retirer
        }

    }
    IDieBehavior dieBehavior;
    public void setDieBehavior(IDieBehavior dieBehavior) {
        if(this.dieBehavior == null ||
                (this.dieBehavior.getClass() == SimpleDieBehavior.class && (dieBehavior.getClass() != SimpleDieBehavior.class))) {
            this.dieBehavior = dieBehavior;
            System.out.printf("Une nouvelle die behavior a été ajoutée à %s%n", this.getName()); //todo à retirer
        }
    }

    //Attacks
    private ArrayList<IAttack> attacks = new ArrayList<>();
    public void addAttack(IAttack attack){
        if(!attacks.contains(attack)){
            attacks.add(attack);
        }

    }
    public ArrayList<IAttack> getAttacks(){
        return (ArrayList<IAttack>) attacks.clone();
    }

    //Stats and states.
    private int health;
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    private int maxHealth;
    public int getMaxHealth() {
        return maxHealth;
    }
    //private boolean alive;
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
    private Map<StatID, Integer> stats = new HashMap<StatID, Integer>();
    /**
     * Return a clone of the stats values.
     * @return clone of the stats values.
     */
    public  Map<StatID, Integer> getStats(){
        Map<StatID, Integer> clonedStats = new HashMap<StatID, Integer>();
        for (int i = 0; i < StatID.values().length; i++) {
            clonedStats.put(StatID.values()[i], this.stats.get(StatID.values()[i]));
        }
        return clonedStats;
    }
    private Map<StatID, Float> statAlterations = new HashMap<>();
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

    //Constants
    private final double ON_DEFENSE_REDUCTION = 0.5; //todo à tej


    //Constructor

    /**
     * Constructor of the animal, without a name.
     * @param maxHealth Maximum health of the animal.
     * @param attackStat Base attack of the animal.
     * @param defenseStat Base defense of the animal.
     */
    public Animal(int maxHealth, int attackStat, int defenseStat) { //todo ajouter l'élément en propriété
        this.name = "Unamed Animal";
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        stats.put(StatID.ATTACK, attackStat);
        stats.put(StatID.DEFENSE, defenseStat);
        statAlterations.put(StatID.ATTACK, 1f);
        statAlterations.put(StatID.DEFENSE, 1f);
        //this.alive = true; -> Géré dans le dieBehavior
    }

    /**
     * Full constructor of the animal.
     * @param name Name of the animal.
     * @param maxHealth Maximum health of the animal.
     * @param attackStat Base attack of the animal.
     * @param defenseStat Base defense of the animal.
     */
    public Animal(String name, int maxHealth, int attackStat, int defenseStat){
        this(maxHealth, attackStat, defenseStat);
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
     * Try to perform an attack. Misses if one of the animal is dead.
     * @param target Target of the attack.
     * @param attack Attack to peform.
     */
    @Override
    public void attack(IAnimal target, IAttack attack) {
        attackBehavior.attack(target, attack, stats.get(StatID.ATTACK));
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
     * Reacts upon an attack : Loses HP.
     * @param damage Damage inflicted by the foe's attack.
     */
    @Override
    public void attacked(int damage) {
        damage-= (stats.get(StatID.DEFENSE)*statAlterations.get(StatID.DEFENSE));
        if(currentMode.equals(ActMode.DEFENSE)){
            damage *= ON_DEFENSE_REDUCTION;
        }
        hurt(damage);
        System.out.printf("%s lost %d damage.%n%n", name, damage);
    }

    /**
     * Loses HP due to an attack or a status.
     * @param damage damage taken.
     */
    public void hurt(int damage){
        health -= damage;

        checkIfDead();
    }

//    /**
//     * The animal is dead : Statuses are removed and the animal can't act.
//     */
//    private void die() {
//        health = 0;
//        for(var i=0; i < statuses.size(); i++){
//            removeStatus(statuses.get(i));
//        }
//        alive = false;
//        canAct = false;
//        System.out.printf("%s is dead.%n", this.name);
//    }

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
     * Alter a stat. Ex: Stat.ATTACK, 0.5 will lower the attack stat by a half, 2 will retablish it.
     * @param statID Stat to alter
     * @param amount Floating number by which the stat must be multiplied.
     */
    @Override
    public void alterStat(StatID statID, float amount) {
        statAlterations.put(statID, amount);
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
        System.out.printf("%s's stats :%nHealth : %d/%d%nAttack : %d%nDefense : %d%n%n",
                name,
                health,maxHealth,
                stats.get(StatID.ATTACK),
                stats.get(StatID.DEFENSE));
    }

}
