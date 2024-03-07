package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.items.Consumable;

/**
 * An action that can be used by any class that uses an item
 *
 * Created by:
 * @author Ewan Lumsden-Smith
 * Modified by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Action
 */
public class  ConsumableAction extends Action {
    /**
     * A string description to be used for the menu display
     */
    private final String description;
    /**
     * Consumable interface
     */
    private final Consumable consumable;


    /**
     * sets all the params for this class
     * @param consumable the item to be consumed
     * @param description the description used for the action
     *
     */
    public ConsumableAction(String description, Consumable consumable) {
        this.consumable = consumable;
        this.description = description;

    }

    /**
     * takes an actor and increases their stats in the way specified by the init returns a string description of the action
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        consumable.consume(actor);
        return actor + description;
    }

    /**
     * returns a string description of the action
     * @param actor The actor performing the action.
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + description;
    }
}
