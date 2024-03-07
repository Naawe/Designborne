package game.spawner;

import engine.actors.Actor;
import engine.positions.Exit;
import engine.positions.Location;
import game.actors.HollowSoldier;

import java.util.Random;

/**
 * Spawner for the Hollow Soldier Enemies
 *
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Spawner
 */
public class HollowSoldierSpawner implements Spawner {
    /**
     * variable for the spawn rate of the spawner
     */
    private float spawnRate;
    /**
     * contains the random generator class
     */
    private final Random random;
    /**
     * Construct for the HollowSoldier Spawner
     * @param spawnRate for the chance of spawning
     * @param random random generator
     */
    public HollowSoldierSpawner(float spawnRate, Random random) {
        this.spawnRate = spawnRate;
        this.random = random;
    }

    /**
     * spawns the hollow soldier at location
     * @param location the location of spawning
     */
    @Override
    public void spawnNewActor(Location location) {
        if(random.nextFloat() < spawnRate){
            Actor newEnemy = new HollowSoldier();
            for(Exit exit: location.getExits()){
                if(exit.getDestination().canActorEnter(newEnemy)){
                    exit.getDestination().addActor(newEnemy);
                    return;
                }
            }
        }
    }

    /**
     * sets the new spawn rate for the spawner
     * @param newSpawnRate the adjusted spawn rate
     */
    @Override
    public void updateSpawnRate(float newSpawnRate) {
        this.spawnRate = newSpawnRate;
    }
}
