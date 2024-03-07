package game.actors;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actions.DoNothingAction;
import engine.actors.Actor;
import engine.displays.Display;
import engine.positions.GameMap;

/**
 * general abstract class that any nonthreatening NPCs can extend from like traveller and the BlackSmith
 *
 * Created by:
 * @author Ewan Lumsden-Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Actor
 */
public abstract class NPC extends Actor {
    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public NPC(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    /**
     * At each turn, a valid action is performed.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn.
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return An action that will be executed for the NPC
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

}
