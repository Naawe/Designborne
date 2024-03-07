package game.items;

import engine.actors.Actor;

/**
 * A class that represents an interface for upgrading items
 * Created by:
 * @author Phoebe Jiang
 * @version 1.0.0
 */

public interface Upgradable {
    /**
     * A method that can be personalised for each item that can be upgraded
     * @param actor The actor which can buy the item
     * @param upgradeAmount The amount the item will be upgraded for
     * @return A string that describes the result of executing the upgrade method
     */
    String upgrade(Actor actor, int upgradeAmount);
}

