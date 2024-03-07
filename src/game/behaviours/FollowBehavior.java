package game.behaviours;

import engine.actions.Action;
import engine.actions.MoveActorAction;
import engine.actors.Actor;
import engine.actors.Behaviour;
import engine.positions.Exit;
import engine.positions.GameMap;
import engine.positions.Location;

/**
 * Follow Behaviour is implemented in Ancient woods enemies and returns a move action that moves the actor towards its target.
 *
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Behaviour
 */
public class FollowBehavior implements Behaviour {
    /**
     * Target of the follow behaviour.
     */
    private final Actor target;

    /**
     * Sets the target in the constructor.
     * @param actor the target to follow
     */
    public FollowBehavior(Actor actor) {
        this.target = actor;
    }

    /**
     * Finds the closest position to the target, then returns a move action for that square.
     * @param actor The Actor with the behaviour
     * @param map the GameMap containing the Actor
     * @return MoveActorAction for the closest location to the target or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location targetLocation = map.locationOf(target);
        Location enemyLocation = map.locationOf(actor);
        int storedScore = Integer.MAX_VALUE;
        Location storedLocation = null;
        for (Exit exit : enemyLocation.getExits()) {
            Location dest = exit.getDestination();
            int moveScore = moveScore(targetLocation, dest);
            if (moveScore < storedScore && dest.canActorEnter(actor)) {
                storedScore = moveScore;
                storedLocation = dest;
            }
        }
        if(storedLocation != null){
            return new MoveActorAction(storedLocation, "towards player");
        }else {
            return null;
        }
    }

    /**
     * Returns a score for how close the position is to the target
     * @param dest1 the location of the target
     * @param dest2 the location of this actor
     * @return int of the MoveScore
     */
    private int moveScore(Location dest1, Location dest2) {
        return Math.abs(dest1.x() - dest2.x()) + Math.abs(dest1.y() - dest2.y());
    }
}
