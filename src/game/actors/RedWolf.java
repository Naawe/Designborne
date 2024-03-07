package game.actors;

import engine.actors.Actor;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.items.HealingVial;
import game.items.Rune;

/**
 * RedWolf enemy actor class
 * Created by:
 * @author Ewan Lumsden-Smith & Phoebe Jiang
 * @version 1.0.0
 * @see EnemyActor
 */
public class RedWolf extends ForestEnemy {
    /**
     * Constant for hit points of the actor
     */
    private static final int HIT_POINTS = 25;
    /**
     * Constant for the amount of runes RedWolf drops
     */
    private static final int RUNE_AMOUNT = 25;
    /**
     * Constant for the damage the enemy actor deals.
     */
    private final int INTRINSIC_DAMAGE = 15;
    /**
     * constant for the hit rate of the RedWolf
     */
    private final int INTRINSIC_HIT_RATE = 80;
    /**
     * constant for the odds of the item drop
     */
    private final float HEALING_VIAL_DROP_CHANCE = 1.0f;

    /**
     * Constructor for the Red Wolf class
     */
    public RedWolf() {
        super("Red Wolf", 'r',HIT_POINTS, RUNE_AMOUNT);
    }

    /**
     * sets the intrinsic weapon of the RedWolf
     * @return IntrinsicWeapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(INTRINSIC_DAMAGE,"bites" , INTRINSIC_HIT_RATE);
    }

    /**
     * drops all the necessary items after actor dies and returns a string describing the events
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return String of what dropped
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Location location = map.locationOf(this);
        drop(location, new Rune(runeAmount), RUNE_DROP_CHANCE);
        drop(location, new HealingVial(), HEALING_VIAL_DROP_CHANCE);
        return super.unconscious(actor, map);
    }
}