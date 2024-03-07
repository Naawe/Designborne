package game.spawner;

import engine.positions.Location;


/**
 * An interface class that helps spawn actors
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @author Zhuojun Zhao
 * @version 1.0.0
 */
public interface Spawner {
    /**
     * class for spawning a new actor
     * @param location the location of spawning
     */
    void spawnNewActor(Location location);

    /**
     * adjusts the spawn rate of the spawner
     * @param newSpawnRate the adjusted spawn rate
     */
    void updateSpawnRate(float newSpawnRate);

}
