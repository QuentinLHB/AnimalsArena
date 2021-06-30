package Damage;

public interface IStatus {
    void consumeEffect();
    int getTurnsLeft();
    void disappear();
    int getTurns();
}
