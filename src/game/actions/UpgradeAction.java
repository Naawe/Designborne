package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import game.items.Upgradable;

/**
 * A class that represents an action that can be used to upgrade an item
 * Created by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Action
 */
public class UpgradeAction extends Action {
    /**
     * A string description to be used for the menu display
     */
    private final String description;
    /**
     * Upgradable interface
     */
    private final Upgradable upgradable;
    /**
     * The amount the item is upgraded for
     */
    private final int upgradeAmount;

    /**
     * Constructor used for making an instance of UpgradeAction which needs
     * a description, an upgradable item & its cost
     * @param description A String describing the action of selling
     * @param upgradable An item that can be upgraded by an Actor
     * @param upgradeAmount The amount of money an item costs to upgrade
     */
    public UpgradeAction(String description, Upgradable upgradable, int upgradeAmount) {
        this.description = description;
        this.upgradable = upgradable;
        this.upgradeAmount = upgradeAmount;
    }

    /**
     * This will allow an Actor to upgrade an
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string explaining the result of upgrading an item
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " " + upgradable.upgrade(actor, upgradeAmount);
    }

    /**
     * Displays a description of UpgradeAction to the user through the actor's menu
     * @param actor The actor performing the action.
     * @return A string summarising the action of UpgradeAction
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " " + description;
    }
}
