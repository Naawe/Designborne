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
 * A class that represents the item Refreshing Flask
 *
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
public class RefreshingFlask extends Item implements Consumable, Sellable, Buyable, Upgradable {
    /**
     * Variable that holds the Random class object.
     */
    private Random random;
    /**
     * Constant representing the stamina increase value of the Refreshing Flask.
     * Is not final as can be modified if the Refreshing Flask is upgraded.
     */
    private int STAMINA_INCREASE_VALUE = 40;
    /**
     * Constant representing the selling amount of the Refreshing Flask.
     */
    private final int SELLING_AMOUNT = 25;
    /**
     * Constant representing the bound of the sellItem.
     */
    private final int BOUND_SELL_ITEM = 10;
    /**
     * Constant representing the bound of the buyItem.
     */
    private final int BOUND_BUY_ITEM = 10;
    /**
     * Constant representing the chance that the Refreshing Flask is stolen while trying to sell.
     */
    private final int CHANCE_SELL_ITEM = 5;
    /**
     * Constant representing the chance that the Refreshing Flask is discounted.
     */
    private final int CHANCE_BUY_ITEM = 1;
    /**
     * The amount of times the Refreshing Flask can be updated.
     */
    private int upgradableTimes = 1;
    /**
     * Constant representing the cost to upgrade the Refreshing Flask.
     */
    private final int UPGRADE_AMOUNT = 175;


    /**
     * A constructor that creates an instance for Refreshing Flask
     */
    public RefreshingFlask() {
        super("Refreshing Flask", 'u', true);
        this.random = new Random();
    }

    /**
     * A collection of actions that can be performed using the Refreshing Flask
     * @param owner the actor that owns the item
     * @return An unmodified collection of actions
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionlist = new ActionList();
        actionlist.add(super.allowableActions(owner));
        actionlist.add(new ConsumableAction("drinks the Refreshing Flask",this));
        return actionlist;
    }

    /**
     * By consuming the Refreshing Flask the actor's stamina will increase by a specific amount
     * @param actor The actor that consumes the item
     */
    @Override
    public void consume(Actor actor) {
        actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.INCREASE, STAMINA_INCREASE_VALUE);
        actor.removeItemFromInventory(this);
    }

    /**
     * List of allowable actions that the Refreshing Flask allows its owner do to another actor.
     * @param target the other actor
     * @param location the location of the other actor
     * @return an unmodifiable list of actions for Refreshing Flask
     */
    @Override
    public ActionList allowableActions(Actor target, Location location) {
        ActionList actionList = super.allowableActions(target, location);
        if (target.hasCapability(Ability.CAN_TRADE)) {
            actionList.add(new SellAction("sells the Refreshing Flask", this, SELLING_AMOUNT));
        }
        if (upgradableTimes > 0 && target.hasCapability(Ability.CAN_UPGRADE)) {
            actionList.add(new UpgradeAction("upgrades the Refreshing Flask", this, UPGRADE_AMOUNT));
        }
        return actionList;
    }

    /**
     * The specific actions that occur when Refreshing Flask is sold
     * @param actor The actor which can sell the item
     * @param sellingAmount The amount the item will be sold for
     * @return A string that describes the result of executing the sell Refreshing Flask method
     */
    @Override
    public String sell(Actor actor, int sellingAmount) {
        int chance = random.nextInt(BOUND_SELL_ITEM);
        if (chance < CHANCE_SELL_ITEM){
            actor.removeItemFromInventory(this);
        } else {
            actor.addBalance(sellingAmount);
            actor.removeItemFromInventory(this);
        }
        return "sells the Refreshing Flask for " + sellingAmount + " runes";
    }

    /**
     * The specific actions that occur when Refreshing Flask is bought
     * @param actor The actor which can buy the item
     * @param buyingAmount The amount the item will be bought for
     * @return A string that describes the result of executing the buy Refreshing Flask method
     */
    @Override
    public String buy(Actor actor, int buyingAmount) {
        int chance = random.nextInt(BOUND_BUY_ITEM);
        int discountedAmount = 80;
        if (chance < CHANCE_BUY_ITEM){
            buyingAmount = (buyingAmount * discountedAmount)/100;
        }
        if (actor.getBalance() >= buyingAmount){
            actor.deductBalance(buyingAmount);
            actor.addItemToInventory(this);
        } else {
            return "cannot afford " + this;
        }
        return "buys the Refreshing Flask for " + buyingAmount + " runes";
    }

    /**
     * Allows an actor to upgrade the Refreshing Flask.
     *
     * @param actor Actor with the Refreshing Flask.
     * @param upgradeAmount Cost to upgrade the Refreshing Flask.
     * @return Returns a string describing the transaction.
     */
    @Override
    public String upgrade(Actor actor, int upgradeAmount) {
        if (actor.getBalance() >= upgradeAmount){
            actor.deductBalance(upgradeAmount);
            this.STAMINA_INCREASE_VALUE *= 5;
            this.upgradableTimes -= 1;
        } else {
            return "cannot afford to upgrade " + this;
        }
        return "upgrades the Refreshing Flask for " + upgradeAmount + " runes";
    }
}

