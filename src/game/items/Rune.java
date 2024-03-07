package game.items;

import engine.actions.ActionList;
import engine.actors.Actor;
import engine.displays.Display;
import engine.items.Item;
import engine.positions.Location;
import game.actions.ConsumableAction;
import game.general.Status;
import game.notification.DeathSubcriber;
import game.notification.PlayerDeathMessageBus;

/**
 * Runes are dropped by enemies and used to trade with
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Item
 * @see Consumable
 */
public class Rune extends Item implements Consumable, DeathSubcriber{
    /**
     * The Amount of runes for the object
     */
    private final Integer amount;
    /***
     * Constructor.
     * @param amount The amount of runes the object is worth.
     */
    public Rune(int amount) {//TODO: refactor Rune argument to show who dropped Rune?
        super("Runes", '$', true);
        this.amount = amount;
        PlayerDeathMessageBus.addSubscriber(this);
    }

    /**
     * returns the list of allowable actions when the Runes are being held by the player
     * @param owner the actor that owns the item
     * @return returns a ConsumableAction to add the Runes to wallet
     */
    @Override
    public ActionList allowableActions(Actor owner) {
        ActionList actionList = super.allowableActions(owner);
        actionList.add(new ConsumableAction("Add " + amount + " runes to wallet", this));
        return actionList;
    }

    /**
     * Consume interface used to add the runes to wallet
     * @param actor The actor which can consume the item
     */
    @Override
    public void consume(Actor actor) {
        actor.addBalance(amount);
        actor.removeItemFromInventory(this);
    }
    /**
     * get amount of rune item
     * @return string of rune amount
     */
    public String getAmount(){
        return amount.toString();
    }
    /***
     * prompt operations on rune once death of the actor it subscribes to happens.
     */
    @Override
    public void notifyDeath(){
        this.addCapability(Status.REMOVED);
        
    }
	
	/**
	 * Called once per turn, so that Locations can experience the passage time. If that's
	 * important to them.
	 */
    @Override
    public void tick(Location currentLocation){
        if(this.hasCapability(Status.REMOVED)){
            currentLocation.removeItem(this);
            Display display = new Display();
            display.println("Player dies again. " + this.getAmount() + " amount of " + this + " on ground is removed.");
        }
    }
}
