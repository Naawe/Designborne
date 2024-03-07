package game.items;

import engine.actors.Actor;

/**
 * A class that represents an interface for buying items
 *
 * Created by:
 * @author Vasi Karabourniotis
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 */
public interface Buyable {

    /**
     * A method that can be personalised for each item that can be bought
     * @param actor The actor which can buy the item
     * @param buyingAmount The amount the item will be bought for
     */
    String buy(Actor actor, int buyingAmount);
}
