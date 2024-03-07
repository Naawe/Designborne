package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.items.Sellable;

/**
 * A class that represents an action that can be used to sell an item
 *
 * Created by:
 * @author Vasi Karabourniotis
 * Modified by:
 * @author Ewan Lumsden-Smith
 * @version 1.0.0
 * @see Action
 */
public class SellAction extends Action {
    /**
     * A string description to be used for the menu display
     */
    private final String description;
    /**
     * Sellable interface
     */
    private final Sellable sellable;
    /**
     * The amount the item is sold for
     */
    private final int sellingAmount;

    /**
     * Constructor used for making an instance of SellAction which needs
     * a description, a sellable item & its cost
     * @param description A String describing the action of selling
     * @param sellable An item that can be sold by an Actor
     * @param sellingAmount The amount of money an item costs
     */
    public SellAction(String description, Sellable sellable, int sellingAmount) {
        this.description = description;
        this.sellable = sellable;
        this.sellingAmount = sellingAmount;
    }

    /**
     * This will allow an Actor to sell an item to a trader if the trader can trade
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string explaining the result of selling an item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " " + sellable.sell(actor, sellingAmount);
    }

    /**
     * Displays a description of SellableAction to the user through the actor's menu
     * @param actor The actor performing the action.
     * @return A string summarising the action of SellableAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + description;
    }
}
