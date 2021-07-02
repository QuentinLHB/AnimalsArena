package Action.Status.Concrete;

import Action.Status.Abstract.IStatus;
import Animal.Abstract.IAnimal;

import java.util.ArrayList;

public abstract class Status_Base {

    protected int nbOfTurns;
    protected int turnsLeft;
    protected IAnimal animal;

    protected Status_Base(IAnimal animal){
        this.animal = animal;
    }

    protected void disappear(IStatus status){
        ArrayList<IStatus> statuses = animal.getStatuses();
        statuses.remove(status);
    }

    /**
     * Checks if a status has already been inflicted to an animal.
     * @param animal Animal on which check.
     * @param statusID ID of the status to check for.
     * @return True if the status exists, else false.
     */
    public static boolean doesStatusAlreadyExist(IAnimal animal, StatusID statusID){
        ArrayList<IStatus> statuses = animal.getStatuses();
        for (IStatus existingStatus:statuses) {
            if(existingStatus.getStatusID().equals(statusID)) return true;
        }
        return false;
    }
}
