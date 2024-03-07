package game.ground;

import engine.actors.Actor;
import engine.positions.Ground;
import game.general.Ability;

/**
 * A class that represents the floor inside a building.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Phoebe Jiang
 * @author Ewan Lumsden Smith
 * @version 1.0.0
 * @see Ground
 */
public class Floor extends Ground {

    /**
     * Constructor for a Floor object.
     * Initializes the floor with the '_' character to represent its appearance on the game map.
     */
    public Floor() {
        super('_');
    }

    /**
     * Determines if an actor can enter the floor.
     *
     * @param actor The actor attempting to enter the floor.
     * @return Only true if the actor has the capability to walk on floor.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return (actor.hasCapability(Ability.WALK_ON_FLOOR));
    }
}
