package game.items;

import edu.monash.fit2099.engine.actors.Actor;

public interface BuyableItem {

    String buyItem (Actor actor, int buyingAmount);
}