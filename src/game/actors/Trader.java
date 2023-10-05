package game.actors;

import edu.monash.fit2099.engine.actors.Actor;
import game.general.Ability;

/**
 * An abstract class that trader actors inherit
 */
public abstract class Trader extends Actor {

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
