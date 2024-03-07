package game.ground;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actors.Actor;
import engine.displays.Display;
import engine.items.Item;
import engine.positions.Ground;
import engine.positions.Location;
import game.actions.OpenGateAction;
import game.general.Ability;
import game.notification.DeathSubcriber;
import game.notification.PlayerDeathMessageBus;

import java.util.ArrayList;

/**
 *  A class for the Gate which transports the actor between levels if they have the key to unlock
 *  Created by:
 *  @author Ewan Lumsden-Smith
 *  @version 1.0.0
 *  @see Ground
 */
public class Gate extends Ground implements DeathSubcriber {
    /**
     * Variable which holds the action that is used by this class
     */
    private final ArrayList<Action> actions = new ArrayList<>();
    /**
     * Variable that stores whether the gate has been opened or not
     */
    private boolean unlocked;

    /***
     * Constructor takes no parameters sets the displayChar as '=' and unlocked variable as false
     */
    public Gate() {
        super('=');
        this.unlocked = false;
        PlayerDeathMessageBus.addSubscriber(this);
    }

    /**
     * Whether an actor can enter
     * @param actor the Actor to check
     * @return A boolean value
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * allows you to add a movement action
     *
     * @param action action added.
     */
    public void addAllowableAction(Action action) {
        this.actions.add(action);
    }

    /**
     * Unlock method takes no parameters and returns void, it sets the gate to unlocked.
     */
    public void Unlock() {
        this.unlocked = true;
    }

    /**
     * checks to see if the actor has a key with the unlock ability if so it returns a UnlockGate action
     * if the gate is Unlocked then it returns the MovePlayerAction.
     * @return returns a list of allowable actions
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction) {
        ActionList actionList = super.allowableActions(actor, location, direction);
        if (unlocked) {
            for(Action action: actions){
                actionList.add(action);
            }
            return actionList;
        }
        for (Item item : actor.getItemInventory()) {
            if (item.hasCapability(Ability.UNLOCK)) {
                actionList.add(new OpenGateAction(this));
                return actionList;
            }
        }
        return actionList;
    }
     /***
     * prompt operations on gate once death of the actor it subscribes to happens.
     */   
    public void notifyDeath(){
        this.unlocked = false;
        Display display = new Display();
        display.println("Gate is locked again.");
    }
}

