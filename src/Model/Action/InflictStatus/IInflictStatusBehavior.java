package Model.Action.InflictStatus;

import Model.Action.IActionBehavior;
import Model.Action.Status.Concrete.StatusID;
import Model.Animal.Creation.Abstract.IAnimal;

public interface IInflictStatusBehavior extends IActionBehavior {


    /**
     * Get the name of the status.
     * @return String of the name.
     */
    String getStatusName();

    /**
     * Inflict status to a target.
     * @param target Model.Animal on which to inflict the status.
     */
    void execute(IAnimal target);


    /**
     * Get the ID of the status, based on the @StatusID Enumerator.
     * @return StatusID enumerator.
     */
    StatusID getStatusID();
}
