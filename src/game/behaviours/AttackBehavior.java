package game.behaviours;

import engine.actions.Action;
import engine.actors.Actor;
import engine.actors.Behaviour;
import engine.positions.Exit;
import engine.positions.GameMap;
import engine.positions.Location;
import game.actions.AttackAction;
import game.general.Status;

/**
 * Attack behaviour that implements behaviour and is used by the enemy to attack any other enemies.
 *
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Behaviour
 */
public class AttackBehavior implements Behaviour {
    /**
     * checks to see if there is an enemy within its surroundings, if there is, it returns an attack action towards that enemy.
     * @param actor the Actor with the behaviour
     * @param map the GameMap containing the Actor
     * @return returns the AttackAction or null
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (map.isAnActorAt(destination) && map.getActorAt(destination).hasCapability(Status.HOSTILE_TO_ENEMY)) {
                return (new AttackAction(map.getActorAt(destination), destination.toString()));
            }

        }
        return null;
    }
}
