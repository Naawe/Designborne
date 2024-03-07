package game.actors;

import engine.actors.Actor;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.items

.HealingVial;
import game.items.Key;
import game.items.Rune;

/**
 * extends enemy actor the wandering undead is the class of the first enemy in the game
 * Created by:
 * @author Ewan Lumsden-Smith & Phoebe Jiang
 * @version 1.0.0
 * @see EnemyActor
 */
public class WanderingUndead extends EnemyActor {
    /**
     * The amount of hit points WanderingUndead has
     */
    private static final int HIT_POINTS = 100;
    /**
     * The amount of runes the WanderingUndead has
     */
    private static final int RUNE_AMOUNT = 50;
    /**
     * The amount of damage an intrinsic weapon can do
     */
    private final int WEAPON_DAMAGE = 30;
    /**
     * The chances of the attack being successful
     */
    private final int WEAPON_HIT_RATE = 50;
    /**
     * The chance of a healing vial being dropped
     */
    private final float HEALING_VIAL_CHANCE = 0.2f;
    /**
     * The chance of an old key being dropped
     */
    private final float OLD_KEY_CHANCE = 1.0f;

    /**
     * Constructor for creating instances of the WanderingUndead
     */
    public WanderingUndead() {
        super("Wandering Undead", 't', HIT_POINTS, RUNE_AMOUNT);
    }

    /**
     * sets its intrinsic weapon
     * @return returns the enemies intrinsic weapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(WEAPON_DAMAGE, "Swings at", WEAPON_HIT_RATE);
    }

    /**
     * drops necessary items upon death and then returns death sequence
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return A String describing death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Location location = map.locationOf(this);
        drop(location, new Rune(runeAmount), RUNE_DROP_CHANCE);
        drop(location, new HealingVial(), HEALING_VIAL_CHANCE);
        drop(location, new Key(), OLD_KEY_CHANCE);
        return super.unconscious(actor, map);
    }
}

