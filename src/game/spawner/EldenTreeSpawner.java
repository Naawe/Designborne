package game.spawner;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.EldentreeGuardian;
import game.actors.EnemyActor;
import game.actors.LivingTreeBranch;
import game.actors.RedWolf;

import java.util.Random;

public class EldenTreeSpawner extends Spawner {
    public EldenTreeSpawner(int odds, int bound, Random random) {
        super(odds, bound, random);
    }

    /**
     * Spawns the Red Wolf at the param location
     *
     * @param location location to spawn
     */
    @Override
    public Actor getNewActor() {
        return new EldentreeGuardian();
    }
}

