package game.actors;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.FollowBehavior;
import game.general.Status;
import game.items.HealingVial;
import game.items.Runes;

import java.util.Random;

/**
 * class for the Forest Keeper which extends EnemyActor
 */
public class ForestKeeper extends EnemyActor {
    /**
     * Constant for the Hit points
     */
    private static final int HIT_POINTS = 125;
    /**
     * Constant for the number of runes dropped
     */
    private static final int COST = 50;
    /**
     * Constant for the amount of damage dealt
     */
    private final int INTRINSIC_DAMAGE = 25;
    /**
     * Constant for the likelihood of attack landing
     */
    private final int INTRINSIC_HIT_RATE = 75;
    /**
     * Constant for the position in the hash Table for follow behaviour
     */
    private final int FOLLOW_BEHAVIOUR_PRIORITY = 998;
    /**
     * Constant for the odds for dropping a healing vial
     */
    private final int BOUND = 10;
    private final int CHANCE = 2;

    /**
     * sets the attributes as well as the behaviours and capabilities on an enemy actor
     */
    public ForestKeeper() {
        super("Forest Keeper", '8', HIT_POINTS, COST);
    }

    /**
     * sets the intrinsic weapon of the enemy
     * @return IntrinsicWeapon
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(INTRINSIC_DAMAGE, "swings at", INTRINSIC_HIT_RATE);
    }

    /**
     * Drops necessary items and removes actor from map after they die
     * @param actor the perpetrator
     * @param map where the actor fell unconscious
     * @return A String describing the events of their death
     */
    @Override
    public String unconscious(Actor actor, GameMap map) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        int number = random.nextInt(BOUND);
        if(number < CHANCE){
            map.locationOf(this).addItem(new HealingVial());
            builder.append("\n").append(name).append(" dropped a healing Vial").append("\n");
        }
        map.locationOf(this).addItem(new Runes(this.getRuneAmount()));
        return builder.toString();
    }

    /**
     * Sets follow behaviour if player is within range of the enemy and then returns actionList
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList of allowableActions
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            addBehavior(FOLLOW_BEHAVIOUR_PRIORITY,new FollowBehavior(otherActor));
        }
        return super.allowableActions(otherActor, direction, map);
    }
}
