package game.ground;
import engine.positions.Ground;

/**
 * A class that represents bare dirt.
 * Created by:
 * @author Riordan D. Alfredo
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Ground
 */
public class Dirt extends Ground {

    /**
     * Constructor for a Dirt object.
     * Initializes the dirt with the '.' character to represent its appearance on the game map.
     */
    public Dirt() {
        super('.');
    }
}