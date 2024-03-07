package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;

/**
 * Action for when the player dies
 *
 * Created by:
 * @author Ewan Lumsden-Smith
 * @version 1.0.0
 * @see Action
 */
public class DieAction extends Action {
    /**
     * This will be used to execute the unconscious method
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string informing the action was successful
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.unconscious(map);
    }

    /**
     * This is supposed to show the action in the user's menu
     * @param actor The actor performing the action.
     * @return Nothing will be returned
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
