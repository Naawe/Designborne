package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.positions.GameMap;
import game.items.Broadsword;

/**
 * Focus action is used by the BroadSword Item
 *
 * Created by:
 * @author Phoebe Jiang
 * Modified by:
 * @author Ewan Lumsden-Smith
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Action
 */

public class FocusAction extends Action {
    /**
     * the item that is using this action
     */
    private final Broadsword weaponItem;
    /**
     * The amount to increase the damage multiplier
     */
    private final float MULTIPLIER_INCREASE_PERCENTAGE = 0.10f;
    /**
     * The updated focus hit rate amount
     */
    private final int FOCUS_HIT_RATE = 90;
    /**
     * the modified amount for stamina decrease
     */
    private final int STAMINA_DECREASE = 40;
    /**
     * the focus duration of a weapon
     */
    private final int FOCUS_DURATION = 5;
    /**
     * The maximum the Broadsword's damage multiplier may be upgraded
     */
    private final int MAX_MULTIPLIER_INCREASE = 11;

    /**
     * sets the weapon
     * @param weapon the weapon using the focus action
     */
    public FocusAction(Broadsword weapon){
        this.weaponItem = weapon;
    }

    /**
     * increases the stats of the weapon then decreases the actors stamina the sets its focus time to 5
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return returns a string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(actor.getAttribute(BaseActorAttributes.STAMINA) >= STAMINA_DECREASE) {
            weaponItem.increaseDamageMultiplier(Math.max(MULTIPLIER_INCREASE_PERCENTAGE, MAX_MULTIPLIER_INCREASE));
            weaponItem.updateHitRate(FOCUS_HIT_RATE);
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, STAMINA_DECREASE);
            weaponItem.setFocusDuration(FOCUS_DURATION);
            return "FOCUSING!";
        }else{
            return "Not enough stamina";
        }
    }

    /**
     * returns a string of the description of the action
     * @param actor The actor performing the action.
     * @return returns a string describing the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " focuses with the " + weaponItem ;
    }
}
