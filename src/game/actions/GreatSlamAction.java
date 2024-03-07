package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.actors.attributes.ActorAttributeOperations;
import engine.actors.attributes.BaseActorAttributes;
import engine.positions.Exit;
import engine.positions.GameMap;
import engine.positions.Location;
import game.items.GiantHammer;

/**
 * Action for the player to use the "Great Slam" skill with the Giant Hammer.
 *
 * Created by:
 * @author Phoebe Jiang
 * Modified by:
 * @author Ewan Lumsden-Smith
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Action
 */
public class GreatSlamAction extends Action {
    /**
     * The item that is using this action
     */
    private GiantHammer weaponItem;
    /**
     * The target of this action
     */
    private Actor target;
    /**
     * The direction of the incoming attack.
     */
    private String direction;
    /**
     * Constant representing the damage multiplier of the Giant Hammer.
     */
    private final int DAMAGE_MULTIPLIER = 160;
    /**
     * Constant representing the stamina decrease from using the Great Slam.
     */
    private final int STAMINA_DECREASE = 10;

    /**
     * Constructor for the Great Slam Action.
     * @param target the target for the GreatSlamAction
     * @param direction the direction to execute the action
     * @param weapon the weapon to execute the weapon
     */
    public GreatSlamAction(Actor target, String direction, GiantHammer weapon){
        this.target = target;
        this.direction = direction;
        this.weaponItem = weapon;
    }
    /**
     * Executes the Great Slam Action by first attacking the target actor with the full damage of the Giant Hammer
     * then the actors around the target with half the damage of the Giant Hammer
     * @param actor the actor who will be executing the action
     * @param map the place in the game the action will be executed
     * @return returns a string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if(actor.getAttribute(BaseActorAttributes.STAMINA) >= STAMINA_DECREASE){
            actor.modifyAttribute(BaseActorAttributes.STAMINA, ActorAttributeOperations.DECREASE, STAMINA_DECREASE);
            AttackAction targetAttack = new AttackAction(target, direction, weaponItem);
            targetAttack.execute(actor, map);

            this.weaponItem.updateDamageMultiplier(DAMAGE_MULTIPLIER * 0.5f);

            for (Exit exit : map.locationOf(actor).getExits()) {
                Location surroundingLocation = exit.getDestination();
                if (surroundingLocation.containsAnActor()) {
                    Actor surroundingActor = surroundingLocation.getActor();
                    AttackAction surroundingAttack = new AttackAction(surroundingActor, direction, weaponItem);
                    surroundingAttack.execute(actor, map);
                }
            }

            this.weaponItem.updateDamageMultiplier(DAMAGE_MULTIPLIER);
            return "SLAMMING";
        }else{
            return "Not enough stamina";
        }
    }

    /**
     * Returns a string of the description of the action
     * @param actor The actor performing the action.
     * @return returns a string describing the action.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " uses great slam with " + weaponItem ;
    }
}
