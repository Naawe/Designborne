package game.items;

import engine.actors.Actor;

/**
 * A class that represents an interface for selling items
 * Created by:
 * @author Vasi Karabourniotis
 * Modified by:
 * @author Ewan Lumsden Smith
 * @author Phoebe Jiang
 * @version 1.0.0
 */
public interface Consumable {

    /**
     * A method that can be personalised for each item that can be consumed
     * @param actor The actor which can consume the item
     */
    void consume (Actor actor);
}
