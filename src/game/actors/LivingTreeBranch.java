package game.actors;

import engine.actors.Actor;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.general.Ability;
import game.items.BloodBerry;
import game.items.Rune;

/**
 * Class for the LivingTreeBranch enemy
 * Created by:
 * @author Ewan Lumsden-Smith
 * @version 1.0.0
 * @see EnemyActor
 */
public class LivingTreeBranch extends EnemyActor{
    /**
     * Constant storing the ruin amount
     */
    private static final int RUNE_AMOUNT = 500;
    /**
     * constant storing the hit points
     */
    private static final int HIT_POINTS = 75;
    /**
     * constant storing the chance of a BloodBerry dropping
     */
    private final float BLOOD_BERRY_DROP_CHANCE = 0.5f;
    /**
     * Constant storing the weapon damage
     */
    private final int WEAPON_DAMAGE = 250;
    /**
     * constant storing the hit rate
     */
    private final int HIT_RATE = 90;
    /**
     * constructor for the LivingTreeBranch EnemyActor class
     */
    public LivingTreeBranch() {
        super("Living Tree Branch", '?', HIT_POINTS, RUNE_AMOUNT);
        addCapability(Ability.WALK_ON_VOID);
        behaviours.remove(WANDER_BEHAVIOUR_PRIORITY);
    }

    /**
     * does chance checks to see if the LivingTreeBranch will drop BloodBerries or Runes then returns a message
     * describing the death sequence.
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return String describing the items dropped by the method and how the Actor was killed
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Location location = map.locationOf(this);
        drop(location, new Rune(runeAmount), RUNE_DROP_CHANCE);
        drop(location, new BloodBerry(), BLOOD_BERRY_DROP_CHANCE);
        return super.unconscious(actor,map);
    }

    /**
     * creates the weapon used by this class
     * @return An IntrinsicWeapon Item object.
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(WEAPON_DAMAGE, "attacks player", HIT_RATE);
    }
}
