package game.ground;

import engine.actors.Actor;
import engine.positions.Ground;
import engine.positions.Location;
import game.general.Ability;

/**
 * A class that represents the Void.
 * Created by:
 * @author Ewan Lumsden Smith
 * @version 1.0.0
 * @see Ground
 */
public class Void extends Ground {

    /**
     * Constructor. sets char to '+'
     */
    public Void() {
        super('+');
    }

    /**
     * returns true
     * @param actor the Actor to check
     * @return a boolean if the actor can enter
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return super.canActorEnter(actor);
    }

    /**
     * checks each turn if the actor standing on it can walk on voids if not it sets them to unconscious
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        if (location.containsAnActor() && (!location.getActor().hasCapability(Ability.WALK_ON_VOID))) {
            location.getActor().unconscious(location.map());
        }
    }

}