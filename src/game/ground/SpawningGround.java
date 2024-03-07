package game.ground;

import engine.positions.Ground;
import engine.positions.Location;
import game.spawner.Spawner;

/**
 * A class that represents the Spawning Ground.
 * Created by:
 * @author Vasi
 * @version 1.0.0
 * @see Ground
 */
public abstract class SpawningGround extends Ground {
    /**
     * Variable which holds the Spawner used the Spawning Ground
     */
    protected Spawner spawner;
    /**
     * Constructor which takes a displayChar and spawner as a parameter
     * @param displayChar the Display Character of the Spawning Ground
     * @param spawner the Spawner used by the spawning ground
     */
    public SpawningGround(char displayChar, Spawner spawner) {
        super(displayChar);
        this.spawner = spawner;
    }

    /**
     * Tick method of this class calls the spawn method with the location in the parameter
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        spawner.spawnNewActor(location);
    }

    /**
     * updates the SpawnRate for the spawner
     * @param newMultiplier a float for the new multiplier
     */
    public void updateSpawnRateMultiplier(float newMultiplier){
        this.spawner.updateSpawnRate(newMultiplier);
    }
}
