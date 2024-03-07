package game.actors;

import engine.actors.Actor;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.items.HealingVial;
import game.items.Rune;

/**
 * class for the Forest Keeper which extends EnemyActor
 * Created by:
 * @author Ewan Lumsden-Smith & Phoebe Jiang
 * @version 1.0.0
 * @see EnemyActor
 */
public class ForestKeeper extends ForestEnemy {
    /**
     * Constant for the Hit points
     */
    private static final int HIT_POINTS = 125;
    /**
     * Constant for the number of runes dropped
     */
    private static final int RUNE_AMOUNT = 50;
    /**
     * Constant for the amount of damage dealt
     */
    private final int WEAPON_DAMAGE = 25;
    /**
     * Constant for the likelihood of attack landing
     */
    private final int WEAPON_HIT_RATE = 75;
    /**
     * The chance of the enemy actor dropping a healing vial
     */
    private final float HEALING_VIAL_DROP_CHANCE = 0.2f;

    /**
     * Constructor: sets the attributes as well as the behaviours and capabilities on an enemy actor
     */
    public ForestKeeper() {
        super("Forest Keeper", '8', HIT_POINTS, RUNE_AMOUNT);
    }

    /**
     * sets the intrinsic weapon of the enemy
     * @return IntrinsicWeapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(WEAPON_DAMAGE, "swings at", WEAPON_HIT_RATE);
    }

    /**
     * Drops necessary items and removes actor from map after they die
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return A String describing the events of their death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        drop(location, new Rune(runeAmount), RUNE_DROP_CHANCE);
        drop(location, new HealingVial(), HEALING_VIAL_DROP_CHANCE);
        return super.unconscious(actor, map);
    }
}
