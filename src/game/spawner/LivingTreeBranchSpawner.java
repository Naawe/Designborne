package game.spawner;

import engine.actors.Actor;
import engine.positions.Exit;
import engine.positions.Location;
import game.actors.LivingTreeBranch;

import java.util.Random;

/**
 * Class for Spawning the Living Tree Branches
 *
 * Created by:
 * @author Ewan Lumsden-Smith
 * @version 1.0.0
 * @see Spawner
 */

public class LivingTreeBranchSpawner implements Spawner{
    /**
     * variable for the spawn rate of the spawner
     */
    private float spawnRate;
    /**
     * contains the random generator class
     */
    private final Random random;
    /**
     * Construct for the Tree Branch Spawner
     * @param spawnRate odds of spawning
     * @param random random generator
     */
    public LivingTreeBranchSpawner(float spawnRate, Random random) {
        this.spawnRate = spawnRate;
        this.random = random;
    }

    /**
     * spawns the living tree branch at location
     * @param location the location of spawning
     */
    @Override
    public void spawnNewActor(Location location) {
        if(random.nextFloat() < spawnRate){
            Actor newEnemy = new LivingTreeBranch();
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
