package game.items;

import engine.actors.Actor;

/**
 * A class that represents an interface for selling items
 *
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Vasi Karabourniotis
 * @author Phoebe Jiang
 * @version 1.0.0
 */
public interface Sellable {

    /**
     * A method that can be personalised for each item that can be sold
     * @param actor The actor which can sell the item
     * @param sellingAmount The amount the item will be sold for
     */
    String sell(Actor actor, int sellingAmount);
}

