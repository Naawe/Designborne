package game.ground;

import engine.actors.Actor;
import engine.positions.Ground;

/**
 * A class that represents the Wall ground type.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Ewan Lumsden Smith
 * *
 */
public class Wall extends Ground {

    /**
     * Constructor for the Wall Class takes no parameters and sets the displayChar as '#
     */
    public Wall() {
        super('#');
    }

    /**
     * blocks everyone
     * @param actor the Actor to check
     * @return returns a boolean for if the actor can enter.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }
}
