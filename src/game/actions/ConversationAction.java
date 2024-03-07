package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;

/**
 * A class that represents an action of conversing between the player and another actor
 *
 * Created by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Action
 */
public class ConversationAction extends Action {
    /**
     * A string description to be used for the menu display
     */
    private final String description;
    /**
     * Conversation interface
     */
    private final Conversation conversation;

    /**
     * Constructor used for making an instance of ConversationAction
     * which needs a description and a conversation string
     * @param description A String describing the action of conversing
     * @param conversation A conversation string
     */
    public ConversationAction (String description, Conversation conversation) {
        this.description = description;
        this.conversation = conversation;
    }

    /**
     * This will allow the player to speak to an actor
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A randomly chosen string from the actor's monologue options
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " " + conversation.conversation(actor);
    }

    /**
     * Displays a description of ConversationAction to the user through the player's menu
     * @param actor The actor performing the action.
     * @return A string summarising the action of ConversationAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + description;
    }
}
