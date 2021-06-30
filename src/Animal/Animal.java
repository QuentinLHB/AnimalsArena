package Animal;

import Action.Abstract.IBite;
import Action.Abstract.ICry;
import Damage.IStatus;

import java.util.ArrayList;

public class Animal implements IAnimal{

    public ArrayList<IStatus> getStatuses() {
        return statuses;
    }

    private ArrayList<IStatus> statuses = new ArrayList<>();
    private IBite biteBehavior;
    private ICry cryBehavior;
    public void addBiteBehavior(IBite biteBehavior){
        this.biteBehavior = biteBehavior;
    }
    public void addCryBehavior(ICry cryBehavior){
        this.cryBehavior = cryBehavior;
    }


    private String name;
    private int health;
    private int maxHealth;
    private boolean alive;
    private int attackStat;
    private int defenseStat;
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
        Attack,
        Defense
    }


    public Animal(String name, int maxHealth, int attackStat, int defenseStat) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attackStat = attackStat;
        this.defenseStat = defenseStat;
        this.alive = true;
    }


    @Override
    public void attack(IAnimal target) {
        currentMode = mode.Attack;
        biteBehavior.bite(target, attackStat);
    }

    @Override
    public void defend() {
        currentMode = mode.Defense;
    }

    @Override
    public void attacked(int damage) {
        if(currentMode.equals(mode.Defense)){
            damage *= ON_DEFENSE_REDUCTION;
        }
        health -= damage;

        if(health <0){
            die();
        }
    }

    public void hurt(int damage){
        health -= damage;
    }

    @Override
    public void die() {
        health = 0;
        for(int i=0; i < statuses.size(); i++){
            removeStatus(statuses.get(i));
        }
        alive = false;

    }

    @Override
    public void addStatus(IStatus status) {
        statuses.add(status);
    }

    @Override
    public void removeStatus(IStatus status){
        statuses.remove(status);
    }
}
