package Animal;

import Action.Abstract.IAction;
import Damage.IDoDamage;
import Damage.IStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Animal implements IAnimal{

    //Attacks (bite...)
    private ArrayList<IDoDamage> attacks = new ArrayList<>();
    public void addAttack(IDoDamage attack){attacks.add(attack);}

    //Stats
    private Map<Stat, Integer> Stats = new HashMap<Stat, Integer>();

    //Statuses (poison...)
    private ArrayList<IStatus> statuses = new ArrayList<>();
    public ArrayList<IStatus> getStatuses() {
        return statuses;
    }


    public String getName() {
        return name;
    }

    private String name;
    private int health;
    private int maxHealth;
    private boolean alive;
    private mode currentMode;
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isAlive() {
        return alive;
    }

    private final double ON_DEFENSE_REDUCTION = 0.5;

    public enum mode{
        ATTACK_MODE,
        DEFENSE_MODE
    }


    public Animal(String name, int maxHealth, int attackStat, int defenseStat) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        Stats.put(Stat.ATTACK, attackStat);
        Stats.put(Stat.DEFENSE, defenseStat);
        this.alive = true;
    }


    @Override
    public void attack(IAnimal target, IDoDamage attack) {
        currentMode = mode.ATTACK_MODE;
        attack.doDamage(target, Stats.get(Stat.ATTACK));
    }

    /** Choose an attack entering the index between 1 and 4.*/
    public IDoDamage chooseAttack(int index){
        return attacks.get(index-1);
    }

    @Override
    public void defend() {
        currentMode = mode.DEFENSE_MODE;
    }

    @Override
    public void attacked(int damage) {
        if(currentMode.equals(mode.DEFENSE_MODE)){
            damage *= ON_DEFENSE_REDUCTION; //TODO intégrer la stat défense
        }
        health -= damage;

        checkIfDead();

    }

    private void checkIfDead(){
        if(health <=0){
            die();
        }
    }

    public void hurt(int damage){
        health -= damage;
        checkIfDead();
    }

    @Override
    public void die() {
        health = 0;
        for(var i=0; i < statuses.size(); i++){
            removeStatus(statuses.get(i));
        }
        alive = false;
        System.out.println("Je meurs....");

    }

    @Override
    public void addStatus(IStatus status) {
        statuses.add(status);
    }

    @Override
    public void removeStatus(IStatus status){
        statuses.remove(status);
    }

    public void endOfTurn(){
        for(var i=0; i < statuses.size(); i++){
            statuses.get(i).consumeEffect();
        }
    }

}
