package game.spawner;

import engine.actors.Actor;
import engine.positions.Exit;
import engine.positions.Location;
import game.actors.WanderingUndead;

import java.util.Random;
/**
 * Spawner for the Hollow Soldier Enemies
 * Created by:
 * @author Ewan Lumsden Smith
 * Modified by:
 * @author Phoebe Jiang
 * @version 1.0.0
 * @see Spawner
 */
public class WanderingUndeadSpawner implements Spawner {
    /**
     * variable for the spawn rate of the spawner
     */
    private float spawnRate;
    /**
     * contains the random generator class
     */
    private final Random RANDOM;
    /**
     * Construct for the Wandering Undead Spawner
     * @param spawnRate for the chance of spawning
     * @param random random generator
     */
    public WanderingUndeadSpawner(float spawnRate, Random random) {
        this.spawnRate = spawnRate;
        this.RANDOM = random;
    }

    /**
     * spawns the wandering undead at location
     * @param location the location of spawning
     */
    @Override
    public void spawnNewActor(Location location) {
        if(RANDOM.nextFloat() < spawnRate){
            Actor newEnemy = new WanderingUndead();
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
