package game.actions;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.GameMap;
import engine.weapons.Weapon;

import java.util.Random;

/**
 * Action for the player to use the "AttackAction"
 *
 * Created by:
 * @author Ewan Lumsden-Smith
 * Modified by:
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see Action
 */
public class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    private Actor target;

    /**
     * The direction of incoming attack.
     */
    private String direction;

    /**
     * Random number generator
     */
    private Random rand = new Random();

    /**
     * Weapon used for the attack
     */
    private Weapon weapon;
    /**
     * Chance to hit enemies
     */
    private final int TOTAL_POSSIBILITY_TO_HIT = 100;

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackAction(Actor target, String direction, Weapon weapon) {
        this.target = target;
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Constructor with intrinsic weapon as default
     *
     * @param target the actor to attack
     * @param direction the direction where the attack should be performed (only used for display purposes)
     */
    public AttackAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Checks if the actor has a weapon, is conscious and their ability to attack
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string describing the action that was chosen to be executed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (weapon == null) {
            weapon = actor.getIntrinsicWeapon();
        }

        if (!(rand.nextInt(TOTAL_POSSIBILITY_TO_HIT) <= weapon.chanceToHit())) {
            return actor + " misses " + target + ".";
        }

        int damage = weapon.damage();
        String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
        target.hurt(damage);
        if (!target.isConscious()) {
            result += "\n" + target.unconscious(actor, map);
        }

        return result;
    }

    /**
     * A description to be displayed to the user to attack an enemy
     * @param actor The actor performing the action.
     * @return A string description of the ability to attack
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " attacks " + target + " at " + direction + " with " + (weapon != null ? weapon : "Intrinsic Weapon");
    }
}
