package game.spawner;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.WanderingUndead;

import java.util.Random;

public class WanderingUndeadSpawner implements Spawner {
    private final int bound;
    private final int odds;
    private final Random random;

    public WanderingUndeadSpawner(int bound, int odds) {
        this.bound = bound;
        this.odds = odds;
        this.random = new Random();
    }

    @Override
    public void spawnEnemy(Location location) {
        if(random.nextInt(bound) < odds){
            location.addActor(new WanderingUndead());
        }
    }
}
