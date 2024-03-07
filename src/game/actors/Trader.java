package game.actors;

import engine.actors.Actor;
import game.general.Ability;

/**
 * An abstract class that trader actors inherit
 *
 * Created by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Actor
 */
public abstract class Trader extends NPC {

    /**
     * The constructor of a trader actor that needs a name,
     * a display character and hit points
     * @param name        the name of the Trader Actor
     * @param displayChar the character that will represent the Trader Actor in the display
     * @param hitPoints   the Trader Actor's starting hit points
     */
    public Trader(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.addCapability(Ability.CAN_TRADE);
    }
}
