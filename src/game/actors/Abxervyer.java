package game.actors;

import java.util.ArrayList;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;
import game.behaviours.FollowBehavior;
import game.extended.ForestGameMap;
import game.general.Ability;
import game.general.Status;
import game.ground.Gate;
import game.items.Runes;
import game.weather.AncientWoodWeatherController;
/**
 * Boss of Ancient Wood.
 */
public class Abxervyer extends EnemyActor {
    /***
     * game map where the boss exists
     */
    GameMap battleGameMap;
    /***
     * game map player will go to once boss is defeated
     */
    GameMap leaveGameMap;
    ArrayList<ForestGameMap> weatherMap = new ArrayList<>();
    /***
     * Weather controller that allows the enemy to controll weather of the gamemap
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
    public Abxervyer(){
        super("Abxervyer", 'Y', HIT_POINTS, COST);
        addCapability(Ability.WALK_ON_VOID);
    }
    
    /**
     * sets the intrinsic weapon of the enemy
     * @return IntrinsicWeapon
     */
    /**
    * Returns a damage weapon that is capable of attacking.
    * 
    * 
    * @return an IntrinsicWeapon that is capable of attacking
    */
    @Override
    public IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(INTRINSIC_DAMAGE, "swings at", INTRINSIC_HIT_RATE);
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
    * Adds a GameMap to the list of leave GameMap
    * 
    * @param map - The GameMap to be
    */
    public void addLeaveGameMap(GameMap map){
        leaveGameMap = map;
    }
    /**
     * get actions other actor can perform on it
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return ActionList of allowableActions
     */
 
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)){
            addBehavior(FOLLOW_BEHAVIOUR_PRIORITY,new FollowBehavior(otherActor));
        }
        return super.allowableActions(otherActor, direction, map);
    }

    /**
    * Once defeated, boss leaves a Gate on where it last standed, drops runes, and print message.
    * 
    * @param actor - The perpetrator
    * @param map - The map of the actor
    * 
    * @return A string containing the result of actor being unconscious
    */
    @Override
    public String unconscious(Actor actor, GameMap map){
        Location lastStand = battleGameMap.locationOf(this);
        Gate gateToAncientWood = new Gate();
        gateToAncientWood.addAllowableAction(new MoveActorAction(leaveGameMap.at(1,1),"to the Ancient Woods!"));
        battleGameMap.at(lastStand.x(), lastStand.y()).setGround(gateToAncientWood);
        StringBuilder builder = new StringBuilder();
        builder.insert(OFFSET_VALUE,super.unconscious(actor, map));
        return builder.toString();
    }
}
