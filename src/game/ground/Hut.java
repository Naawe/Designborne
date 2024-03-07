package game.ground;

import engine.positions.Ground;
import game.spawner.Spawner;

/**
 * A class that represents bare dirt.
 * Created by:
 * @author Ewan Lumsden Smith
 * @version 1.0.0
 * @see Ground
 */
public class Hut extends SpawningGround {

    /**
     * constructor takes a spawner as a parameter
     * @param spawner The Spawning Object used by the spawning ground
     */
    public Hut(Spawner spawner) {
        super('h', spawner);
    }
}
