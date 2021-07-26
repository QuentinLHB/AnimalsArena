package Action.InflictStatus;

import Action.IActionBehavior;
import Action.Status.Concrete.StatusID;
import Animal.Creation.Abstract.IAnimal;

import java.io.Serializable;

public interface IInflictStatusBehavior extends IActionBehavior {


    /**
     * Get the name of the status.
     * @return String of the name.
     */
    String getStatusName();

    /**
     * Inflict status to a target.
     * @param target Animal on which to inflict the status.
     */
    void execute(IAnimal target);


    /**
     * Get the ID of the status, based on the @StatusID Enumerator.
     * @return StatusID enumerator.
     */
    StatusID getStatusID();
}
