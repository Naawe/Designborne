package game.actors;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * general abstract class that any nonthreatening NPCs can extend from like traveller and the BlackSmith (TA recommended.
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
}
