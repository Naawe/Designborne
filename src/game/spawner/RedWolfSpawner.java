package game.spawner;

import edu.monash.fit2099.demo.mars.behaviours.FollowBehaviour;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.EnemyActor;
import game.actors.RedWolf;
import game.behaviours.FollowBehavior;

import java.util.Random;

public class RedWolfSpawner implements Spawner{
    private Actor target;
    private int odds;
    private int bound;
    private Random random;

    public RedWolfSpawner(Actor target, int odds, int bound) {
        this.target = target;
        this.odds = odds;
        this.bound = bound;
        this.random = new Random();
    }

    @Override
    public void spawnEnemy(Location location) {
        if(random.nextInt(bound) < odds){
            EnemyActor newEnemy = new RedWolf();
            newEnemy.addBehavior(new FollowBehavior(target),998);
            location.map().addActor(newEnemy,location);
        }
    }
}