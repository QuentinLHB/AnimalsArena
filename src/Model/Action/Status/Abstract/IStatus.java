package Model.Action.Status.Abstract;

import Model.Action.Status.Concrete.StatusID;

import java.io.Serializable;

public interface IStatus extends Serializable {

    /**
     * Get the ID of the status.
     * @return StatusID of the status.
     */
    StatusID getStatusID();

    /**
     * Get the status name.
     * @return String of the status.
     */
    String getStatusName();

    /**
     * Consume the effect of the status.
     */
    void consumeEffect();

    /**
     * Get the amount of turns left before the status is fully consumed.
     * @return Integer representing the amount of turns left.
     */
    int getTurnsLeft();

    /**
     * Removes the effect from the animal.
     */
    void disappear();

    /**
     * Get the full duration of the status.
     * @return Integer representing the full duration of the attack.
     */
    int getDuration();

    int getDefaultDuration();
}
