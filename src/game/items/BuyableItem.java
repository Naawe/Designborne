package game.items;

import edu.monash.fit2099.engine.actors.Actor;

/**
 * A class that represents an interface for buying items
 */
public interface BuyableItem {

    /**
     * A method that can be personalised for each item that can be bought
     * @param actor The actor which can buy the item
     * @param buyingAmount The amount the item will be bought for
     * @return A string that describes the result of executing the buy item method
     */
    String buyItem (Actor actor, int buyingAmount);
}
