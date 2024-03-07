package game.weather;

import java.util.Random;

import engine.positions.GameMap;
import game.actors.RedWolf;
import game.extended.ForestGameMap;
import game.ground.Bush;
import game.ground.Hut;
import game.spawner.ForestKeeperSpawner;
import game.spawner.RedWolfSpawner;

/**
* Execute effects related to this weather
 * Created by:
 * @author Zhuojun Zhao
*/
public class SunnyForestWeather implements Weather{
    float HUT_SPAWN_RATE_MULTIPLIER = 0.3f;
    float BUSH_SPAWN_RATE_MULTIPLIER = 0.3f;
    float REDWOLF_DAMAGE_MULTIPLIER = 3f;

    Hut hut = new Hut(new ForestKeeperSpawner(0.15f, new Random()));
    Bush bush = new Bush(new RedWolfSpawner(0.3f, new Random()));
    RedWolf redWolf = new RedWolf();
    /**
    * Execute effects related to this weather
    * 
    * @param gameMap - game map that is affected by this weather
    */
    public void weatherEffect(GameMap gameMap){
        ForestGameMap _gameMap = (ForestGameMap) gameMap;
        _gameMap.modifySpawnRate(HUT_SPAWN_RATE_MULTIPLIER, hut);
        _gameMap.modifySpawnRate(BUSH_SPAWN_RATE_MULTIPLIER, bush);
        _gameMap.modifyEnemyDamageMultiplier(REDWOLF_DAMAGE_MULTIPLIER, redWolf);
    }



}
