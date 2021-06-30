package Damage;

public interface IStatus {
    StatusID getStatusID();
    void consumeEffect();
    int getTurnsLeft();
    void disappear();
    int getTurns();
}
