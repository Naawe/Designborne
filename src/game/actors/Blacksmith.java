package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.actions.BlacksmithConversation;
import game.actions.Conversation;
import game.actions.ConversationAction;
import game.general.Ability;

/**
 * Class for the Blacksmith
 * Created by:
 * @author Phoebe
 * Modified by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see NPC
 */
public class Blacksmith extends NPC {
    private static final int HIT_POINTS = 10;
    Conversation blacksmithConversation = new BlacksmithConversation();

    /**
     * The constructor of the Actor class.
     */
    public Blacksmith() {
        super("Blacksmith", 'B', HIT_POINTS);
        addCapability(Ability.CAN_UPGRADE);
    }

    /**
     * At each turn, a valid action is performed.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn.
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the action the blacksmith will perform.
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }
    /**
     * Allows the Blacksmith to have a conversation
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList actions.
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actionList = new ActionList();
        actionList.add(new ConversationAction("listens to the Blacksmith's monologue", blacksmithConversation));
        return actionList;
    }

}
