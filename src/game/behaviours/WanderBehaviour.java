package game.behaviours;

import java.util.ArrayList;
import java.util.Random;

import engine.actions.Action;
import engine.actors.Actor;
import engine.positions.Exit;
import engine.positions.GameMap;
import engine.positions.Location;
import game.actors.EnemyActor;
import engine.actors.Behaviour;

/**
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Ewan Lumsden Smith
 * @author Phoebe Jiang
 * @version 2.0.0
 * @see Behaviour
 */
public class WanderBehaviour implements Behaviour {
    /**
     * Variable that holds the Random class object.
     */
    private final Random random = new Random();

    /**
     * Returns a MoveAction to wander to a random location, if possible.
     * If no movement is possible, returns null.
     *
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        EnemyActor enemy = (EnemyActor) actor;
        ArrayList<Action> actions = new ArrayList<>();
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }

        if (!actions.isEmpty()) {
            return actions.get(random.nextInt(actions.size()));
        }
        else {
            return null;
        }

    }
}
