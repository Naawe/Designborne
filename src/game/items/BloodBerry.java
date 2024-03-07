package game.items;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.items.Item;
import engine.positions.Location;
import game.actions.ConsumableAction;
import game.actions.SellAction;
import game.general.Ability;

/**
 * A class that represents the BloodBerry Item
 *
 * Created by:
 * @author Vasi Karabourniotis
 * Modified by:
 * @author Ewan Lumsden Smith
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Item
 * @see Consumable
 * @see Sellable
 */
public class BloodBerry extends Item implements Consumable, Sellable {
    /**
     * Constant for the max health increase given
     */
    private final int HEALTH_INCREASE_VALUE = 5;
    /**
     * Constant for the selling price
     */
    private final int SELLING_AMOUNT = 10;

    /**
     * A constructor that creates an instance for BloodBerry
     */
    public BloodBerry() {
        super("BloodBerry", '*', true);
    }

    /**
     * A list of actions that the BloodBerry can perform
     * @param owner the actor that owns the item
     * @return A collection of actions to be executed by the BloodBerry
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = super.allowableActions(owner);
        actionList.add(new ConsumableAction("eats the bloodberry", this));
        return actionList;
    }

    /**
     * By consuming the BloodBerry the actor's health will increase by a specific amount
     * @param actor The actor that consumes the item
     */
    @Override
    public void consume(Actor actor) {
        actor.modifyAttributeMaximum(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE,HEALTH_INCREASE_VALUE);
        actor.removeItemFromInventory(this);
    }

    /**
     * List of allowable actions that the BloodBerry allows its owner do to another actor.
     * @param target the other actor
     * @param location the location of the other actor
     * @return an unmodifiable list of actions for BloodBerry
     */
    @Override
    public ActionList allowableActions(Actor target, Location location) {
        ActionList actionList =  super.allowableActions(target, location);
        if (target.hasCapability(Ability.CAN_TRADE)) {
            actionList.add(new SellAction("sells the BloodBerry", this, SELLING_AMOUNT));
        }
        return actionList;
    }

    /**
     * The specific actions that occur when BloodBerry is sold
     * @param actor The actor which can sell the item
     * @param sellingAmount The amount the item will be sold for
     * @return A string that describes the result of executing the sell BloodBerry method
     */
    @Override
    public String sell(Actor actor, int sellingAmount) {
        actor.addBalance(sellingAmount);
        actor.removeItemFromInventory(this);
        return "sells the BloodBerry for " + sellingAmount + " runes";
    }
}
