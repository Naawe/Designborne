package game.actors;

import java.util.ArrayList;

import engine.actions.Action;
import engine.actions.ActionList;
import engine.actors.Actor;
import engine.displays.Display;
import engine.positions.GameMap;
import engine.positions.Location;
import engine.weapons.IntrinsicWeapon;
import game.behaviours.FollowBehavior;
import game.extended.ForestGameMap;
import game.general.Ability;
import game.general.Status;
import game.ground.Gate;
import game.weather.AncientWoodWeatherController;
/**
 * Boss of Ancient Wood.
 *
 * Created by:
 * @author Zhuojun Zhao
 * Modified by:
 * @author Ewan Lumsden-Smith
 * @author Vasi Karabourniotis
 * @version 1.0.0
 * @see EnemyActor
 */
public class Abxervyer extends BossEnemy{
    /**
     * game map where the boss exists
     */
    GameMap battleGameMap;
    /**
     * the gate that will be opened when the boss is defeated
     */
    private final Gate lastStandGate;
    /**
     * An array list of the weather maps
     */
    ArrayList<ForestGameMap> weatherMap = new ArrayList<>();
    /**
     * Weather controller that allows the enemy to control weather of the game map
     */
    AncientWoodWeatherController ancientWoodWeatherController = new AncientWoodWeatherController();
    /**
     * Constant for the Hit points
     */
    private static final int HIT_POINTS = 2000;
    /**
     * Constant for the number of runes dropped
     */
    private static final int COST = 5000;
    /**
     * Constant for the amount of damage dealt
     */
    private final int INTRINSIC_DAMAGE = 80;
    /**
     * Constant for the likelihood of attack landing
     */
    private final int INTRINSIC_HIT_RATE = 25;
    /**
     * Constant for the position in the hash Table for follow behaviour
     */
    private final int FOLLOW_BEHAVIOUR_PRIORITY = 998;

    /**
     * A constructor for creating an instance of Abxervyer
     * @param finalGate The gate that will be used by Abxervyer
     */
    public Abxervyer(Gate finalGate){
        super("Abxervyer", 'Y', HIT_POINTS, COST);
        addCapability(Ability.WALK_ON_VOID);
        this.lastStandGate = finalGate;
    }

    /**
    * Called when the player presses one of the turn buttons.
    * 
    * @param actions - The list of actions that were pressed
    * @param lastAction - The last action that was pressed
    * @param map - The GameMap to play the turn on
    * @param display - The Display to play the turn on
    * 
    * @return The next action to take
    */
    @Override 
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display){
        ancientWoodWeatherController.processWeather(weatherMap);
        return super.playTurn(actions, lastAction,map, display);
    }

    /**
    * Adds a map to the weather map
    * 
    * @param map - The map to add
    */
    public void addWeatherMap(ForestGameMap map){
        weatherMap.add(map);
    }
    /**
    * Adds battle game map.
    * 
    * @param map - The map to add
    */
    public void addBattleGameMap(GameMap map){
        battleGameMap = map;
    }

    /**
     * Returns a damage weapon that is capable of attacking.
     * @return an IntrinsicWeapon that is capable of attacking
     */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(INTRINSIC_DAMAGE, " ATTACKS", INTRINSIC_HIT_RATE);
    }

    /**
     * The list of allowable actions by Abxervyer
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A list of actions for Abxervyer
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            addBehavior(FOLLOW_BEHAVIOUR_PRIORITY,new FollowBehavior(otherActor));
        }
        return super.allowableActions(otherActor, direction, map);
    }

    /**
    * Once defeated, boss leaves a Gate on where it last stood, drops runes, and print message.
    * 
    * @param actor - The perpetrator
    * @param map - The map of the actor
    * 
    * @return A string containing the result of actor being unconscious
    */
    @Override
    public String unconscious(Actor actor, GameMap map){
        Location lastStand = battleGameMap.locationOf(this);
        battleGameMap.at(lastStand.x(), lastStand.y()).setGround(lastStandGate);
        actor.addCapability(Status.ABXERVYER_DEFEATED);
        return super.unconscious(actor, map);
    }
}
