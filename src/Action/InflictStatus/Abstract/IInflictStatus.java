package Action.InflictStatus.Abstract;

import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;

public interface IInflictStatus {


    /**
     * Get the name of the status.
     * @return String of the name.
     */
    String getStatusName();

    /**
     * Inflict status to a target.
     * @param target Animal on which to inflict the status.
     */
    void inflictStatus(IAnimal target);

    /**
     *
     * @return True if the status is to be inflicted to the attack owner.
     */
    boolean isSelfInflicting();

    /**
     * Get the ID of the status, based on the @StatusID Enumerator.
     * @return StatusID enumerator.
     */
    StatusID getStatusID();
}
