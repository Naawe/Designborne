package game.weather;

import java.util.ArrayList;

import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import game.actors.EnemyActor;
import game.extended.ForestGameMap;
import game.ground.Bush;
import game.ground.Hut;
import game.ground.SpawningGround;
import game.spawner.ForestKeeperSpawner;
import game.spawner.RedWolfSpawner;

public class SunnyForestWeather extends ForestWeather implements Weather{
    Hut hut = new Hut(new ForestKeeperSpawner(15,100));
    Bush bush = new Bush(new RedWolfSpawner(3,10));

    public void weatherEffect(GameMap gameMap){
        ForestGameMap _gameMap = (ForestGameMap) gameMap;
        modifySpawnRate(_gameMap, hut, 2f);
        modifySpawnRate(_gameMap, bush, 1f);
    }



}
