package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import game.general.Ability;
import game.general.Status;
import game.items.Broadsword;
import game.items.HealingVial;
import game.items.RefreshingFlask;
import game.items.Trade;

public class Traveller extends Actor implements Trade {

    /**
     * The constructor of the Actor class.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Traveller(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        addCapability(Ability.CAN_TRADE);
        addItemToInventory(new HealingVial());
        addItemToInventory(new RefreshingFlask());
        addItemToInventory(new Broadsword());
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    @Override
    public String buyItems(Item item, Player player) {
        return null;
    }

    @Override
    public String sellItems(Item item, Player player) {
        return null;
    }
}
