package game.actions;

import engine.actors.Actor;

/**
 * A class that represents an interface for have a conversation with an actor
 *
 * Created by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 */
public interface Conversation {
    /**
     * A function to create a unique conversation between the player and another actor
     * @param actor The actor who can have a conversation
     * @return A string conversation
     */
    public String conversation (Actor actor);
}
