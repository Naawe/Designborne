package game.extended;

import java.util.ArrayList;
import java.util.List;

import engine.actors.Actor;
import engine.positions.GameMap;
import engine.positions.Ground;
import engine.positions.GroundFactory;
import engine.positions.Location;
import game.actors.EnemyActor;
import game.ground.SpawningGround;

/**
 * GameMap that provides functions necessary for execution
 * Created by:
 * @author Zhuojun Zhao
 * @version 1.0.0
 * @see GameMap
 */
public class ForestGameMap extends GameMap{
    
    public ForestGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    /**
    * Returns an ArrayList of Ground objects that are spawning the specified ground.
    * 
    * @param ground - the ground to look for
    * 
    * @return an ArrayList of Ground
    */
    public ArrayList<Location> getCertainSpawningGrounds(SpawningGround ground) {
        Class<? extends SpawningGround> groundClass = ground.getClass();
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int x : widths) {
			for (int y : heights) {
                Ground currentGround = at(x, y).getGround();
				if(groundClass.equals(currentGround.getClass())) {
                    locations.add(at(x, y));
                }
            }
        }
        return locations;
    }

     /**
     * Modifies the spawning rate of the ground.
     * 
     * @param multiplier - The multiplier to apply to the rate.
     * @param spawningGround - The ground to modify
     */
     public void modifySpawnRate(float multiplier, SpawningGround spawningGround) {
        ArrayList<Location> spawnerLocations = getCertainSpawningGrounds(spawningGround);
        for(Location location : spawnerLocations) { 
            SpawningGround spawnGround = (SpawningGround)location.getGround();
            spawnGround.updateSpawnRateMultiplier(multiplier);
     }
    }


    /**
    * Returns an arraylist of locations where enemy is located.
    * 
    * @param enemy - the enemy to look for
    * 
    * @return an arraylist of locations
    */
    public ArrayList<Location> getCertainEnemies(EnemyActor enemy) {
        Class<? extends EnemyActor> enemyClass = enemy.getClass();
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int x : widths) {
            for (int y : heights) {
                if(at(x,y).containsAnActor()){
                    Actor currentActor = at(x, y).getActor();
                    if(enemyClass.equals(currentActor.getClass())) {
                        locations.add(at(x, y));
                }
                
                }
            }
        }
        return locations;
}

    /**
    * Modifies damage multiplier of enemy.
    * 
    * @param multiplier - Damage multiplier to be applied
    * @param enemy - EnemyActor whose death is
    */
    public void modifyEnemyDamageMultiplier(float multiplier, EnemyActor enemy) {
            ArrayList<Location> enemyLocations = getCertainEnemies(enemy);
            for(Location location : enemyLocations) { 
                EnemyActor enemyActor = (EnemyActor)location.getActor();
                enemyActor.updateDamageMultiplier(multiplier);
        }
        }

    /**
    * Heals all enemies in this location that are able to heal the enemy.
    * 
    * @param healPoint - the point at which to heal the enemy
    * @param enemy - the enemy to heal
    */
    public void healCertainEnemies(int healPoint, EnemyActor enemy) {
        ArrayList<Location> enemyLocations = getCertainEnemies(enemy);
        for(Location location : enemyLocations) { 
            EnemyActor enemyActor = (EnemyActor)location.getActor();
            enemyActor.heal(healPoint);
    }

}   

}