package game.weather;

import java.util.ArrayList;

import engine.positions.GameMap;
import game.extended.ForestGameMap;
/**
 * Controller Keep track of play-turn and manage weather switching.
 *
 * Created by:
 * @author Zhuojun Zhao
 */
public class AncientWoodWeatherController{
    protected int playturnCount = 0;
    protected int weatherIndex = 0;
    protected ArrayList<Weather> weatherList;
    protected ArrayList<GameMap> gameMapList;
    protected final int TURN_DIVIDER = 3;
/**
 * Constructor. Set all weathers that will appear on certain game map(s)
 */
    public AncientWoodWeatherController(){
        ArrayList<Weather> weathersToAdd = new ArrayList<>();
        weathersToAdd.add(new SunnyForestWeather());
        weathersToAdd.add(new RainyForestWeather());
        this.weatherList = weathersToAdd;
    }
    /**
    * This method is called every TURN_DIVIDER turns to track weather
    */
    public void weatherTracker(){
        if(playturnCount !=0 && playturnCount % TURN_DIVIDER == 0){
            weatherIndex++;
        }
        if(weatherIndex > weatherList.size() - 1){
            weatherIndex = 0;
        }
        playturnCount++;
    }

    /**
    * Returns the current weather.
    * @return The current weather or null if there is
    */
    public Weather currentWeather(){
        return this.weatherList.get(weatherIndex);
    };
    
    /**
    * Processes the weather of the game
    * @param gameMapList - list of game map affected
    */
    public void processWeather(ArrayList<ForestGameMap> gameMapList){
        weatherTracker();
        for(GameMap gameMap : gameMapList){
            currentWeather().weatherEffect(gameMap);
    }
    
}
}