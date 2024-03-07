package game.items;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.items.Item;
import engine.positions.Location;
import game.actions.ConsumableAction;
import game.actions.SellAction;
import game.actions.UpgradeAction;
import game.general.Ability;

import java.util.Random;

/**
 * A class that represents the Healing Vial
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Vasi Karabourniotis
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Item
 * @see Consumable
 * @see Buyable
 * @see Sellable
 */
public class HealingVial extends Item implements Consumable, Sellable, Buyable, Upgradable {
    /**
     * variable that holds the Random class object
     */
    private Random random;
    /**
     * Constant representing the selling amount of the Healing Vial.
     */
    private final int SELLING_AMOUNT = 35;
    /**
     * Constant representing the bound of the sellItem.
     */
    private final int BOUND_SELL_ITEM = 10;
    /**
     * Constant representing the bound of the buyItem
     */
    private final int BOUND_BUY_ITEM = 4;
    /**
     * Constant representing the chance the selling price of the Healing Vial is doubled.
     */
    private final int CHANCE_SELL_ITEM = 1;
    /**
     * Constant representing the chance the buying price of the Healing Vial is increased.
     */
    private final int CHANCE_BUY_ITEM = 1;
    /**
     * The amount of times the Healing Vial can be updated.
     */
    private int upgradableTimes = 1;
    /**
     * Constant representing the cost to upgrade the Healing Vial.
     */
    private final int UPGRADE_AMOUNT = 250;
    /**
     * Constant representing the stamina increase value of the Healing Vial.
     * Is not final as can be modified if the Healing Vial is upgraded.
     */
    private int HEALTH_INCREASE_VALUE = 15;

    /**
     * A constructor that creates an instance for Healing Vial
     */
    public HealingVial() {
        super("Healing Vial", 'a', true);
        this.random = new Random();
    }

    /**
     * Returns a replenish action object
     * @param owner the actor that owns the item
     * @return
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionlist = new ActionList();
        actionlist.add(super.allowableActions(owner));
        actionlist.add(new ConsumableAction("drinks the Healing Vial" ,this));
        return actionlist;
    }

    /**
     * By consuming the Healing Vial the actor's health will increase by a specific amount
     * @param actor The actor that consumes the item
     */
    @Override
    public void consume(Actor actor) {
        actor.modifyAttribute(BaseActorAttributes.HEALTH, ActorAttributeOperations.INCREASE, HEALTH_INCREASE_VALUE);
        actor.removeItemFromInventory(this);
    }

    /**
     * List of allowable actions that the Healing Vial allows its owner do to another actor.
     * @param target the other actor
     * @param location the location of the other actor
     * @return an unmodifiable list of actions for Healing Vial
     */
    @Override
    public ActionList allowableActions(Actor target, Location location) {
        ActionList actionList = super.allowableActions(target, location);
        if (target.hasCapability(Ability.CAN_TRADE)) {
            actionList.add(new SellAction("sells the Healing Vial ", this, SELLING_AMOUNT));
        }
        if (upgradableTimes > 0 && target.hasCapability(Ability.CAN_UPGRADE)) {
            actionList.add(new UpgradeAction("upgrades the Healing Vial", this, UPGRADE_AMOUNT));
        }
        return actionList;
    }

    /**
     * The specific actions that occur when Healing Vial is sold
     * @param actor The actor which can sell the item
     * @param sellingAmount The amount the item will be sold for
     * @return A string that describes the result of executing the sell Healing Vial method
     */
    @Override
    public String sell(Actor actor, int sellingAmount) {
        Random random = new Random();
        int chance = random.nextInt(BOUND_SELL_ITEM);
        if (chance < CHANCE_SELL_ITEM) {
            sellingAmount = sellingAmount * 2;
        }
        actor.addBalance(sellingAmount);
        actor.removeItemFromInventory(this);
        return "sells the Healing Vial for " + sellingAmount + " runes";
    }

    /**
     * The specific actions that occur when Healing Vial is bought
     * @param actor The actor which can buy the item
     * @param buyingAmount The amount the item will be bought for
     * @return A string that describes the result of executing the buy Healing Vial method
     */
    @Override
    public String buy(Actor actor, int buyingAmount) {
        int chance = random.nextInt(BOUND_BUY_ITEM);
        int increasedAmount = 150;
        if (chance < CHANCE_BUY_ITEM) {
            buyingAmount = (buyingAmount * increasedAmount)/100;
        }
        if(actor.getBalance() >= buyingAmount){
            actor.deductBalance(buyingAmount);
            actor.addItemToInventory(this);
            return "buys the Healing Vial for " + buyingAmount + " runes";
        } else {
            return  "cannot afford the " + this;
        }
    }

    /**
     * Allows an actor to upgrade the Healing Vial.
     *
     * @param actor Actor with the Healing Vial.
     * @param upgradeAmount Cost to upgrade the Healing Vial.
     * @return Returns a string describing the transaction.
     */
    @Override
    public String upgrade(Actor actor, int upgradeAmount) {
        if (actor.getBalance() >= upgradeAmount){
            actor.deductBalance(upgradeAmount);
            this.HEALTH_INCREASE_VALUE *= 8;
            this.upgradableTimes -= 1;
        } else {
            return "cannot afford to upgrade " + this;
        }
        return "upgrades the Healing Vial for " + upgradeAmount + " runes";
    }
}
